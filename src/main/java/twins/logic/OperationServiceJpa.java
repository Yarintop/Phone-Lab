package twins.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import twins.boundaries.*;
import twins.converters.ItemConverter;
import twins.converters.OperationConverter;
import twins.dao.ItemDao;
import twins.dao.OperationDao;
import twins.dao.UserDao;
import twins.data.*;
import twins.exceptions.BadRequestException;
import twins.exceptions.NoPermissionException;
import twins.exceptions.NotFoundException;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import java.util.stream.StreamSupport;

@Service
public class OperationServiceJpa implements OperationsService {

    private OperationDao operationsDao;
    private JmsTemplate jmsTemplate;

    // DAOs for checking user & items are correct
    private UserDao usersDao;
    private ItemDao itemDao;

    // User utils
    private UserUtilsService userUtilsService;
    private UpdatedItemsService itemsService;
    private UsersService usersService;

    private OperationConverter operationConverter;
    private ItemConverter itemConverter;
    private String spaceId;

    @Autowired
    public void setDAOs(OperationDao operationsDao, UserDao usersDao, ItemDao itemsDao) {
        this.operationsDao = operationsDao;
        this.usersDao = usersDao;
        this.itemDao = itemsDao;
    }

    @Autowired
    public void setServices(UserUtilsService userUtilsService, UpdatedItemsService itemsService, UsersService usersService) {
        this.userUtilsService = userUtilsService;
        this.itemsService = itemsService;
        this.usersService = usersService;
    }

    @Autowired
    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }


    @Autowired
    public void setItemConverter(ItemConverter itemConverter)
    {
        this.itemConverter = itemConverter;
    }

    @Autowired
    public void setOperationConverter(OperationConverter converter) {
        this.operationConverter = converter;
    }

    @Value("${spring.application.name:2021b.notdef}")
    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    @Override
    @Transactional
    public Object invokeOperation(OperationBoundary operation) {
        String userSpace = operation.getInvokedBy().getUserId().getSpace();
        String userEmail = operation.getInvokedBy().getUserId().getEmail();

        // Check Permission
        ErrorType permissionStatus = userUtilsService.checkRoleUser(userSpace, userEmail, UserRole.PLAYER);
        if (permissionStatus == ErrorType.USER_DOES_NOT_EXIST)
            throw new NotFoundException("User " + userEmail + " with space ID: " + userSpace + " not found!");

        if (permissionStatus == ErrorType.BAD_USER_ROLE)
            throw new NoPermissionException("User " + userEmail + " with space ID: " + userSpace +
                    " doesn't have permission for this action!");

        // Check operation format
        if (operation.getType() == null || operation.getType().length() == 0)
            throw new BadRequestException("Invalid operation type! (" + operation.getType() + ")");

        OperationEntity entity = operationConverter.toEntity(operation);
        if (checkItemMissing(entity))
            throw new NotFoundException("The item inside the operation was not found!");
        if (!checkItemActive(entity))
            throw new NotFoundException("The item inside the operation is not active!");

        entity.setOperationId(UUID.randomUUID().toString(), spaceId);
        entity.setCreatedTimestamp(new Date());
        operationsDao.save(entity);

        Map<String, Object> operationAttrs = operation.getOperationAttributes();
        ItemIdBoundary itemIdBoundary = operation.getItem().getItemId();
        String itemSpace = itemIdBoundary.getSpace();
        String itemId = itemIdBoundary.getId();
        DigitalItemBoundary itemBoundary = itemsService.getSpecificItem(userSpace, userEmail, itemSpace, itemId);
        Map<String, Object> itemAttrs = itemBoundary.getItemAttributes();
        Map<String, Object> msg = new HashMap<>();

        //Change Player to be a Manager to be able to activate update role
        UserIdBoundary userId = operation.getInvokedBy().getUserId();
        UserBoundary userBoundary = usersService.login(userId.getSpace(), userId.getEmail());
        Object rv = null;
        userBoundary.setRole("Manager");
        usersService.updateUser(userId.getSpace(), userId.getEmail(), userBoundary);

        switch(entity.getOperationType())
        {
            case "assignTechnician":
                String tech = (String) operationAttrs.get("technician");
                itemAttrs.put("assignedTechnician", tech);
                itemsService.updateItem(userSpace, userEmail, itemSpace, itemId, itemBoundary);
                rv = itemBoundary;
                break;
            case "markAsDone":
                Optional<ItemEntity> itemEntity = itemDao.findById(entity.getItem());
                if (itemEntity.isPresent()) {
                    try {
                        this.sendSMSNotification(itemEntity.get());
                    } catch (InterruptedException e) {
                        System.err.println(e);
                    }
                }
            case "useSparePart":
                itemAttrs.put("Deactivation Date", new Date());
                itemBoundary.setActive(false);
                itemBoundary = itemsService.updateItem(userSpace, userEmail, itemSpace, itemId, itemBoundary);
                rv = itemBoundary;
                break;

            case "getSparePartsCount":
                rv = itemDao.countByNameIgnoreCaseAndActiveTrue(itemBoundary.getName());
                break;

            case "addSpareParts": //TODO - DELETE?
                int amount = (int) itemAttrs.getOrDefault("amount", 0);
                int amountToAdd = (int) operationAttrs.getOrDefault("amountToAdd", 1);
                itemAttrs.put("amount", amount + amountToAdd);
                itemBoundary = itemsService.updateItem(userSpace, userEmail, itemSpace, itemId, itemBoundary);
                rv = itemBoundary;
                break;

            case "bindPartsToJob": // TODO - SHOULD MAKE IT MARKED AS USED AS WELL
                Map<String, String> child = (Map<String, String>) operationAttrs.get("childItem");
                ItemIdBoundary childId = new ItemIdBoundary(child.getOrDefault("space", ""), child.getOrDefault("id", ""));
                itemsService.bindChild(userSpace, userEmail, itemSpace, itemId, childId);
                rv = itemBoundary;
                break;

            case "getJobSparePartsCost":
                String parentId = itemConverter.toSecondaryId(itemSpace, itemId);
                List<DigitalItemBoundary> children = itemDao
                        .findAllByParents_id(parentId)
                        .stream()
                        .map(itemConverter :: toBoundary)
                        .collect(Collectors.toList());
                
                double price = 0;
                for (DigitalItemBoundary item : children){
                    Integer tmp = (int) item.getItemAttributes().getOrDefault("price", 0);
                    price += tmp.doubleValue();
                }

                msg.put("totalPrice", price);
                msg.put("numberOfParts", children.size());
                rv = msg;
                break;

            // case "deleteSpecificItem":
            //     itemDao.deleteById(arg0);
            //     break;
            
            // THE DIMA HAS SPOKEN
            // case "deleteSpecificUser":
            //     break;

            // case "getAllItemsByType":
            //     return itemDao
            //             .findAllByType(itemBoundary.getType())
            //             .stream()
            //             .map(itemConverter::toBoundary)
            //             .collect(Collectors.toList());

            default:
                msg.put("ERROR", "!!~UNDEFINED OPERATION~!!");
                rv = msg;
                break;
        }
        userBoundary.setRole("Player");
        usersService.updateUser(userId.getSpace(), userId.getEmail(), userBoundary);
        return rv;
    }

    @Override
    @Transactional
    public OperationBoundary invokeAsynchronous(OperationBoundary operation) {
        String userSpace = operation.getInvokedBy().getUserId().getSpace();
        String userEmail = operation.getInvokedBy().getUserId().getEmail();

        // Check Permission
        ErrorType permissionStatus = userUtilsService.checkRoleUser(userSpace, userEmail, UserRole.PLAYER);
        if (permissionStatus == ErrorType.USER_DOES_NOT_EXIST)
            throw new NotFoundException("User " + userEmail + " with space ID: " + userSpace + " not found!");

        if (permissionStatus == ErrorType.BAD_USER_ROLE)
            throw new NoPermissionException("User " + userEmail + " with space ID: " + userSpace +
                    " doesn't have permission for this action!");

        // Check operation format
        if (operation.getType() == null || operation.getType().length() == 0)
            throw new BadRequestException("Invalid operation type! (" + operation.getType() + ")");

        OperationEntity entity = operationConverter.toEntity(operation);
        if (checkItemMissing(entity))
            throw new NotFoundException("The item inside the operation was not found!");

        entity.setOperationId(UUID.randomUUID().toString(), spaceId);
        operation = operationConverter.toBoundary(entity);

        // send OperationBoundary to MOM for a-synchronous processing
        ObjectMapper jackson = new ObjectMapper();
        try {
            String json = jackson.writeValueAsString(operation);
            this.jmsTemplate // API for MOM (ActiveMQ)
                    .send("opInbox", // target of message
                            session->session.createTextMessage(json) // message creator that generates Text Message
                    );

            // return response to client without waiting for processing to end by MOM
            return operation;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public List<OperationBoundary> getAllOperations(String adminSpace, String adminEmail) {
        Optional<UserEntity> userToCheck = usersDao.findById(new UserIdBoundary(adminSpace, adminEmail).toString());
        if (!userToCheck.isPresent() || userToCheck.get().getRole() != UserRole.ADMIN)
            throw new NoPermissionException("User: " + adminEmail + " is not permitted to view all operations");
        
        return StreamSupport.stream(operationsDao.findAll().spliterator(), false)
            .map(operationConverter::toBoundary)
            .collect(Collectors.toList());
            
    }

    @Override
    @Transactional
    public List<OperationBoundary> getAllOperations(String adminSpace, String adminEmail, int size, int page) {
        Optional<UserEntity> userToCheck = usersDao.findById(new UserIdBoundary(adminSpace, adminEmail).toString());
        if (!userToCheck.isPresent() || userToCheck.get().getRole() != UserRole.ADMIN)
            throw new NoPermissionException("User: " + adminEmail + " is not permitted to view all operations");

        return operationsDao
            .findAll(PageRequest.of(page, size, Sort.Direction.ASC, "createdTimestamp"))
            .getContent()
            .stream()
            .map(this.operationConverter::toBoundary)
            .collect(Collectors.toList());
            
    }

    @Override
    @Transactional
    public void deleteAllOperations(String adminSpace, String adminEmail) {
        Optional<UserEntity> userToCheck = usersDao.findById(new UserIdBoundary(adminSpace, adminEmail).toString());
        if (userToCheck.isPresent() && userToCheck.get().getRole() == UserRole.ADMIN)
            operationsDao.deleteAll();
        else
            throw new NoPermissionException("User: " + adminEmail + " is not permitted to delete operations");
    }

    @Override
    public void sendSMSNotification(ItemEntity Job) throws InterruptedException {
        System.out.println("Sending an SMS message through a *REAL* API (EYAL KATZ IS KING)"); // Instead of thread sleep we would send a request to an external SMS API.
    }

    @Transactional(readOnly = true)
    public boolean checkItemMissing(OperationEntity oe) {
        if (oe.getItem() == null) return true;
        String itemKey = oe.getItem();
        return !itemDao.findById(itemKey).isPresent();
    }

    @Transactional(readOnly = true)
    public boolean checkItemActive(OperationEntity oe) {
        String itemKey = oe.getItem();
        Optional<ItemEntity> optionalItemEntity = itemDao.findById(itemKey);
        if (optionalItemEntity.isPresent()) {
            ItemEntity itemEntity = optionalItemEntity.get();
            if (itemEntity.isActive()){
                return true;
            }
        }
        return false;
    }
}

/*

                                    `,;*########+*:.                                                                                                   
                               `,*##+i::,,,,,:::;*##+,                                                                                                
                            `:+#+**:,,,,,,,,,:**:,,,;+#;                                                                                              
                          .*#*:,+nni,,,,,,,,,;z#:,,,,,:*#.                                                                                            
                        :#+;,,,,*#*:,,,,,,,,,,::,,,,,,,,:#;                                                                                           
                      ;#*:,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,:+*                                                                                          
                    :#*:,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,+*                                                                                         
                  ,zn;,,,,,,,,,,,,,,,,,,:ii:,,,,,,,,,,,,,,,,+*                                                                                        
                `*xnz;,,,,,,,,,,,,,,,,,:#nn+,,,,,,,,;i,,,,,,,+;                                                                                       
               ,#i##;,,,,,:i*i:,,,,,,,,:+#+:,,,,,,,:zz;,,,,,,:z,                                                                                      
              i#:,,,,,,,,:#nnz:,,,,,,,,,,:,,,,,,,,,:+*:,,,,,,,:z`                                                                                     
            `#i,,,,,,,,,,;znzi,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,i*                                                                                     
           .#;,,,,,,,,,,,,;;:,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,#.                                                                                    
          ,#:,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,;+                                                                                    
         :#:,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,#`                                                                                   
        :#:,,,,::,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,ii                                                                                   
       ,+:,,,,;#+,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,:#                                                                                   
      .n;,,,,:#n#,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,z`                                                                                  
     `nn;,,,,;z#:,,,,,,,:;*+#*:,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,#,                                                                                  
     +n#:,,,,,;:,,,,,,;+z#*;::,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,*;                                                                                  
    :+i:,,,,,,,,,,,,;##;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,;i                                                                             `;;: 
   `#:,,,,,,,,,,,,:+#;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,;*                                                                          `:++;,i,
   ii,,,,,,,,,,,,:z*,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,:+                                                                    `.,;*+#*:,,,:*
  `#,,,,,,,,,,,,;z;,,,,,,,,,,,:;ii,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,:+                                                             .:;*++##+*i::,,,,,,,+
  ii,,,,,,,,,,,:z:,,,,,,,,:i#zz#*;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,;*                                                        .;*##+*i:::,,,,,,,,,,,,,,+
  #,,,,,,,,,,,:z;,,,,,,,;#z+;:,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,;*                                                     `i#+i:,,,,,,,,,,,,,,,,,,,,,:*
 .+,,,,**,,,,,+i,,,,,,:##;,,,,,,,,,,,,,,,,:::,,,,,,,,,,,,,,,,,,,,,ii                                                   `*#;:,,,,,,,,,,,,,,,,,,,,,,,:;;
 *;,,,inz:,,,:#,,,,,,*z;,,,,,,,,,,,,,,:i#zz#i,,,,,,,,,,,,,,,,,,,,,+,                                                  .#;,,,,,,,,,,,,,,,,,,,,,,,,:;;#`
 #:,,,#n#,,,,;;,,,,:#+,,,,,,,,,,,,:i#z#*;:,,,,,,,,,,,,,,,,,,,,,,,,z`                                                 ,#:,,,,,,,,,,,,,,,,,,,,,,,,:;;** 
`#,,,:zz;,,,,,,,,,:zi,,,,,,,,,,,;+z+;:,,,,,,,,,,,,,,,,,,,,,,,,,,,:#                                                 ,#:,,,,,,,,,,,,,,,,,,,,,,,,:;;*z` 
:*,,,,i;,,,,,,,,,:z;,,,,,,,,,:iz#;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,*;                                                `#:,,,,,,,,,,,,,,,,,,,,,,,:;;;zM,  
i;,,,,,,,,,,,,,,:z;,,,,,,,,,*z+:,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,z`                                                +;,,,,,,,,,,,,,,,,,,,,,,,:;;;#Mi   
*:,,,,,,,,,,,,,,#i,,,,,,,,iz*:,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,;*                                                ;*,,,,,,,,,,,,,,,,,,,,,,:;;;;*Wi    
#*,,,,,,,,,,,,,i+,,,,,,,:##:,,,,,,,;+####+;,,,,,,,,,,,,,,,,,,,,,#.                                                #:,,,,,,,,,,,,,,,,,,,,:;ii;;;zi     
zz,,,,,,,,,,,,:#:,,,,,,iz;,,,,,,,:#+:,,,,;+#:,,,,,,,,,,,,,,,,,,i*                                                ,*,,,,,,,,,,,,,,,,,,::;+nxx*;#;      
zz,,,,,,,,,,,,*i,,,,,,*#:,,,,,,,,#;........:#*,,,,,,,,,,,,,,,,:z`                                               .z:,,,,,,,,,,,,,,::;;;inn##x*z:       
#*,,,,,,,,,,,,i,,,,,,++,,,,,,,,,i+..........,++:,,,,,,,,,,,,,,+;                                               :#+,,,,,,,,,,,,,::;;;;;zz+#xn#.        
+;,,,,,,,,,,,,,,,,,,+*,,,,,,,,,,#,............i#,,,,,,,,,,,,,;#                                              `**;:,,,,,,,,,,,,:;;;;;;;xnnxn;          
ii,,:i:,,,,,,,,,,,,+*,,,,,,,,,,:z..............i+,,,,,,,,,,,:#.                                             ,#;,,,,,,,,,,,,,,:;;;;;;;;i*z+`           
:*,,ini,,,,,,,,,,,i+,,,,,,,,,,,;#...............**,,,,,,,,,,*i                                            `*+:,,,,,,,,,,,,,,:;;;;*#z#*#+.             
.#,,+n*,,,,,,,,,,:z:,,,,,,,,,,,;#................#;,,,,,,,,;#                                            ,#i,,,,,,,,,,,,,,,:;;;inn#zx#,               
`#,,+ni,,,,,,,,,,#;,,,,,,,,,,,,:#................,z:,,,,,,iz.                                          `*+:,,,,,,,,,,,,,,,,:;;;nzzn+.                 
 #:,;i:,,,,,,,,,;#,,,,,,,::::,,,#.................i*,,,,,,**                                          ,#i,,,,,,,,,,,,,:::,,:;;+Mzi`                   
 ii,,,,,,,,,,,,,#:,,,,,;##++##i:#,......,:.........#:,,,,,:#                                        `*+:,,,,,,,,,,,,,;####++++i.                      
 .#,,,,,,,,,,,,:#,,,,,*#,....,*z#i.....*nx;........;+,,,,,,*;   `:i++++i.                          :#;,,,,,,,,,,,,,;#*.                               
  #:,,,,,,,,,,,i*,,,,;#,.......,+n....,nxxz,.......,#:,,,,,,+.i++i;:,,,;++`                      `++:,,,,,,,,,,,,i#i`                                 
  ;*,,,,,,,,,,,+;,,,,#:..........#;....zxxxi........ii,,,,,:##;,,,,,,,,,,;#`                    ;#;,,,,,,,,,,,,i#i`                                   
  `#,,,,,,,,,,,::,,,:z...........:#....ixxxn,.......,#,,,,iz;,,,,,,,,,,,,,:#`                 .+*,,,,,,,,,,,,i#i`                                     
   ii,,,,,,,,,,,,,,,:+............+:...,nxxx#,.......#:,,:i,,,,,,,,,,::::,,i;                ;+:,,,,,,,,,,:i#;`                                       
   `#:,,,,,,,,,,,,,,:+............,#,...*xxxxi.......+;,,,,,,,,,,:i#####z#ii;              .+i,,,,,,,,,,,i#i`                                         
    :+,,,,,,,,,,,,,,:+.............*i...,zxxxxi......ii,,,,,,,,;##*:,,,,,:*n#,`           i+:,,,,,,,,,,izi`                                           
     +i,,,,,,,,,,,,,:#.............,#,...:nxxxx;.....ii,,,,,,:##;,,,,,,,,,,:;+##*;.`    .#i,,,,,,,,,,i#*`                                             
     `#;,,,,,,,,,,,,,z......:;,.....;+....;xxxxx:....+;,,,,,iz;,,,,,,,,,,,,,,,,::i+##*;i#:,,,,,,,,,;#i`                                               
      `#;,,,,,,,,,,,,#:....*xx*......+i....;nxxx:...,z,,,,:#+:,,,,,,,,,,,,,,,,,,,,,,,:i+z;,,,,,,,;#*`                                                 
       `+*:,,,,,,,,,,i*...,xxxx:.....,#;....:zx*....i*,,,:zi,,,,,,,,,,,,,,,,,,,,,,,,,,,,:z,,,,,;#*`                                                   
         ;#i,,,,,,,,,:z,...#xxxz,....ii#*....,:....:z:,,;z;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,#,,,;#*`                                                     
          `i#+i;:::;iizi...:nxxx+,...#:,*#:.......:z:,,*#:,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,:#,;#*`                                                       
             .;*++*i;:,#,...*xxxx*..,#,,,:##i,.,:+#:,,++:,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,:#*#*`                                                         
                       ;*...,zxxxx*.:+,,,,,:*#nz+;,,:#*,,,,,,,,,:::;::::::::,,,,,,,,::;zM*`                                                           
                        +:...:nxxxx#ii,,,,,,,,i+,,,:z;,,,,,,,,:;;;;;;;;i**i;;;;;;;;;;in+.                                                             
                        `#,...;nxxxxx;,,,,,,,,,z;,;z;,,,,,,,,;;;;;;;;;zxnxxni;;;i#nz##,                                                               
                         .#,...:nxxxM:,,,,,,,,,:z*z:,,,,,,;+;;;iii;;;*n##++z+;;ixznz,                                                                 
                          ,#,...,#xnx:,,,,,,,,,,*z:,,,,,,:z,z*znnnni;*x###nxi;;zxz;                                                                   
                           .#:....;,#:,,,,,,,,,*+,,,,,,,:#, `*x##+#xi;*zzz+ii+nx,                                                                     
                            `+*,....z:,,,,,,,,++,,,,,,,,+:    ,xMnnM+*+++#znnn#n:                                                                     
                             ,zz#++#x:,,,,,,:#*,,,,,,,,*nn+` ;xz##zznnnzz#######i                                                                     
                            :#:,:;;:z:,,,,,:#i,,,,,,,,in##zn#n##################*                                                                     
                           .#:,,,,,,z:,,,,:zi,,,,,,,,;xx####Mz#################zi                                                                     
                          `#:,,,,,,,+;,,,:z;,,,,,,,,:z:*x####M#################n:                                                                     
                          ;i,,,,,,,,**,,:z;,,,,,,,,:z;,,;n###zx##############n#.                                                                      
                         `#,,,,,,,,;##,:z;,,,,,,,,,#i,,,,:n###x############zn:                                                                        
                         :*,,,,,,:+#;z:z;,,,,,,,,,+*,,,,,,;n##zn#########nn;`                                                                         
                         +:,,,,,:#i,,#z:,,,,,,,,,i+,,,,,,,,z###x#######nz;`                                                                           
                         #,,,,,:z;,,:z;,,,,,,,,,;#:,,,,,,,,;Mn#x###n#nz:                                                                              
                        `#,,,,:z:,,:z;,,,,,,,,,:z:,,,,,,,,,,nzxM###zM+                                                                                
                        `#,,,,#;,,:z;,,,,,,,,,,#;,,,,,,,,,,,z##n#####ni                                                                               
                        `#,,,i*,,:#;,,,,,,,,,,**,,,,,,,,,,,,+#########ni                                                                              
                         #,,,:,,,+i,,,,,,,,,,;#,,,,,,,,,,,,,+##########n:                                                                             
                         *:,,,,,**,,,,,,,,,,:#:,,,,,,,,,,,,,############n.                                                                            
                         ,+,,,,;#,,,,,,,,,,,+i,,,,,,,,,,,,,,n#############                                                                            
                          i#i;iz:,,,,,,,,,,;#,,,,,,,,,,,,,,;n############n:                                                                           
                           .;*ni,,,,,,,,,,:#:,,,,,,,,,,,,,,z##############n`                                                                          
                             .+,,,,,,,,,,,**,,,,,,,,,,,,,,*n##############z*                                                                          
                             +:,,,,,,,,,,:nz:,,,,,,,,,,,,+n################n.                                                                         
                            .+,,,,,,,,,,,##zz;,,,,,,,,,;zn##################+                                                                         
                            *:,,,,,,,,,,;x###nz+;::::i#xz###################n,                                                                        
                           `+,,,,,,,,,,:zz#####znnnxn;.,z####################+                                                                        
                           :i,,,,,,,,,:#z########zni`   #####################x,                                                                       
                           *:,,,,,,,,,+n########n+`     :n####################+                                                                       
                           +:,,,,,,,:+n#######nz,       `n####################n`                                                                      
                           zz:,,,,,inz######nz:          #####################z;                                                                      
                           znn+ii+nn#####zn#,            i######################                                                                      
                           ,zzznnz####znz*.              :z####################n`                                                                     
                            `*znnnnnz+;.                 ,n####################n:                                                                     
                               ....                      .n#####################*                                                                     
                                                         .n#####################z                                                                     
                                                         .n#####################n`                                                                    
                                                         :z#####################n,                                                                    
                                                         *z#####################z;                                                                    
                                                         ########################+                                                                    
                                                        `n#######################z                                                                    
                                                        ,n#######################n                                                                    
                                                        *########################n`                                                                   
                                                        n########################x,                                                                   
                                                       ,n########################n,                                                                   
                                                       +#########################n:                                                                   
                                                      `n#########################z;                                                                   
                                                      ;z########znxxxxxxnzz######z;                                                                   
                                                      z######nnz+i;:::::;*#nxz###z:                                                                   
                                                     ,n###zxz;:,,,,,,,,,,,,,:*nn#n,                                                                   
                                                     ,n#zx#:,,,,,,,,,,,,,,,,,,:*nn`                                                                   
                                                      znz;,,,,,,,,,,,,,,,,,,,,,,;+                                                                    
                                                      .#,,,,,,,,,,,,,,,,,,,,,,,,+:                                                                    
                                                      .*,,,,,,,,,,,,,,,,,,,,,,,,+.                                                                    
                                                      i;,,,,,,,,,,,,,,,,,,,,,,,,#                                                                     
                                                     `#,,,,,,,,,,,,,,,,,,,,,,,,:+                                                                     
                                                     ;i,,,,,,,,,,,,,,,,,,,,,,,,i:                                                                     
                                                     #:,,,,,,,,,,,,,,,,,,,,,,,:z`                                                                     
                                                    ,+,,,,,,,,,,,,,,,,,,,,,,,,z;                                                                      
                                                    +:,,,,,,,,,,,,,,,,,,,,,,:#n`                                                                      
                                                   .#,,,,:i,,,,,,,,,,,,,,,,:#i#                                                                       
                                                   *;,,,,*ni,,,,,,,,i:,,,;iz;,#                                                                       
                                                  `#,,,,:z:+#i:,,,,,#:,,,#*:,,+                                                                       
                                                  i;,,,,**,,:+##z##*z,,,,#:,,,+                                                                       
                                                 `+,,,,:z,,,,,,,#. `#,,,,#:,,,+                                                                       
                                                 :;,,,,*i,,,,,,ii  ,*,,,,+:,,,:                                                                       
                                                 .`````:```````.   `.``` `                                                                            

*/
