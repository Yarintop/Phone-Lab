package twins.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import twins.boundaries.DigitalItemBoundary;
import twins.boundaries.ItemIdBoundary;
import twins.boundaries.UserBoundary;
import twins.converters.ItemConverter;
import twins.dao.ItemDao;
import twins.data.ErrorType;
import twins.data.ItemEntity;
import twins.data.UserRole;
import twins.exceptions.BadRequestException;
import twins.exceptions.NoPermissionException;
import twins.exceptions.NotFoundException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ItemServiceJpa implements UpdatedItemsService {
    private ItemDao itemDao;

    private UserUtilsService userUtilsService;

    private String spaceId;
    private ItemConverter entityConverter;


    public ItemServiceJpa() {
    }

    /**
     * Sets the spaceId value from the application.properties file
     *
     * @param spaceId the loaded spaceId value, default would be "2021b.twins"
     */
    @Value("${spring.application.name:2021b.notdef}")
    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    @Autowired
    public void setDAOs(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    @Autowired
    public void setServices(UserUtilsService userUtilsService) {
        this.userUtilsService = userUtilsService;
    }

    /**
     * This function will set the converter that will be used to
     * convert boundary objects to entity objects and vice versa
     *
     * @param entityConverter - an instance of {@link ItemConverter}
     */
    @Autowired
    public void setEntityConverter(ItemConverter entityConverter) {
        this.entityConverter = entityConverter;
    }

    @Override
    @Transactional//(readOnly = false)
    public DigitalItemBoundary createItem(String userSpace, String userEmail, DigitalItemBoundary item) {

        if (item.getName() == null || item.getName().length() == 0)
            throw new BadRequestException("Invalid item name! (" + item.getName() + ")");

        if (item.getType() == null || item.getType().length() == 0)
            throw new BadRequestException("Invalid item type! (" + item.getType() + ")");

        userSpace = this.spaceId;
        String newId = UUID.randomUUID().toString();

        // Set space of the created item to be that of our project.
        item.setItemId(newId, userSpace);
        item.setCreatedTimestamp(new Date());

        //Update the info of the creating user to be those in the REST API /twins/items/{userSpace}/{userEmail})

        item.setCreatedBy(new UserBoundary(userEmail, userSpace));

        if (item.getLocation() == null)
            throw (new BadRequestException("Can't create an item without a location")); // Item must have a location
        if (item.getItemAttributes() == null)
            item.setItemAttributes(new HashMap<>());
        if (item.getType() == null)
            throw (new BadRequestException("Can't create an item without a type")); // Item must have a type
        if (item.getName() == null)
            throw (new BadRequestException("Can't create an item without a name")); // Item must have a name
        if (item.isActive() == null)
            item.setActive(false); // Set default active value to false
        if (item.getItemAttributes() == null)
            item.setItemAttributes(new HashMap<>()); // Set default item attributes to an empty map


        ItemEntity entity = this.entityConverter.toEntity(item);
        this.itemDao.save(entity);

        return this.entityConverter.toBoundary(entity);
    }


    @Override
    @Transactional//(readOnly = false)
    public DigitalItemBoundary updateItem(String userSpace, String userEmail, String itemSpace, String itemId,
                                          DigitalItemBoundary update) {

        if (update.getName() != null && update.getName().length() == 0)
            throw new BadRequestException("Invalid item name! (" + update.getName() + ")");

        if (update.getType() != null && update.getType().length() == 0)
            throw new BadRequestException("Invalid item type! (" + update.getType() + ")");

        String itemKey = this.entityConverter.toSecondaryId(itemSpace, itemId);
        Optional<ItemEntity> optionalItem = this.itemDao.findById(itemKey);
        // get existing message from mockup database
        if (optionalItem.isPresent()) {
            DigitalItemBoundary existing = this.entityConverter.toBoundary(optionalItem.get());
            boolean dirty = false;

            // update collection and return update
            if (update.isActive() != null) {
                existing.setActive(update.isActive());
                dirty = true;
            }

            if (update.getType() != null) {
                existing.setType(update.getType());
                dirty = true;
            }

            if (update.getName() != null) {
                existing.setName(update.getName());
                dirty = true;
            }

            // ignore id from update - as ids are never changed

            if (update.getItemAttributes() != null && !update.getItemAttributes().equals(existing.getItemAttributes())) {
                existing.setItemAttributes(update.getItemAttributes());
                dirty = true;
            }

            // ignore timestamp as creation timestamp is never changed

            // ignore createdBy as created user is never changed

            if (update.getLocation() != null) {
                existing.setLocation(update.getLocation());
                dirty = true;
            }

            // update mockup database
            if (dirty) {
                this.itemDao.save(this.entityConverter.toEntity(existing));
            }

            return existing;

        } else {
            throw new NotFoundException("could not find message by id: " + itemKey);// NullPointerException
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<DigitalItemBoundary> getAllItems(String userSpace, String userEmail) {

        ErrorType managerRoleCheck = userUtilsService.checkRoleUser(userSpace, userEmail, UserRole.MANAGER);
        if (managerRoleCheck == ErrorType.USER_DOES_NOT_EXIST)
            throw new NotFoundException("User " + userEmail + " with space ID: " + userSpace + " not found!");

        ErrorType playerRoleCheck = userUtilsService.checkRoleUser(userSpace, userEmail, UserRole.PLAYER);
        if (playerRoleCheck != ErrorType.GOOD && managerRoleCheck != ErrorType.GOOD)
            throw new NoPermissionException("User " + userEmail + " with space ID: " + userSpace +
                    " doesn't have permission for this action!");

        Iterable<ItemEntity> allEntities = this.itemDao.findAll();
        return StreamSupport
                .stream(allEntities.spliterator(), false) // get stream from iterable
                .map(this.entityConverter::toBoundary)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DigitalItemBoundary> getAllItems(String userSpace, String userEmail, int size, int page) {

        ErrorType managerRoleCheck = userUtilsService.checkRoleUser(userSpace, userEmail, UserRole.MANAGER);
        if (managerRoleCheck == ErrorType.USER_DOES_NOT_EXIST)
            throw new NotFoundException("User " + userEmail + " with space ID: " + userSpace + " not found!");

        ErrorType playerRoleCheck = userUtilsService.checkRoleUser(userSpace, userEmail, UserRole.PLAYER);
        if (playerRoleCheck != ErrorType.GOOD && managerRoleCheck != ErrorType.GOOD)
            throw new NoPermissionException("User " + userEmail + " with space ID: " + userSpace +
                    " doesn't have permission for this action!");

        if(managerRoleCheck == ErrorType.GOOD) // User role is manager
            return this.itemDao
                    .findAll(PageRequest.of(page, size))
                    .getContent()
                    .stream()
                    .map(this.entityConverter::toBoundary)
                    .collect(Collectors.toList());
        else // User role is Player
            return this.itemDao
                    .findAllByActiveTrue(PageRequest.of(page, size)) // Find all items where active=true
                    .getContent()
                    .stream()
                    .map(this.entityConverter::toBoundary)
                    .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public DigitalItemBoundary getSpecificItem(String userSpace, String userEmail, String itemSpace, String itemId) {
        // MOCKUP
        String itemKey = this.entityConverter.toSecondaryId(itemSpace, itemId);
        Optional<ItemEntity> optionalItem = this.itemDao.findById(itemKey);

        if (optionalItem.isPresent()) {
            ItemEntity entity = optionalItem.get();
            return entityConverter.toBoundary(entity);
        } 
        else {
            // TODO have server return status 404 here
            throw new NotFoundException("could not find message by id: " + itemKey);// NullPointerException
        }
    }

    @Override
    @Transactional//(readOnly = false)
    public void deleteAllItems(String adminSpace, String adminEmail) {
        this.itemDao.deleteAll();
    }

    @Override
    @Transactional
    public void bindChild(String userSpace, String userEmail, String itemSpace, String itemId, ItemIdBoundary childId) {
        String parentId = this.entityConverter.toSecondaryId(itemSpace, itemId);

        ItemEntity parentItem = this.itemDao.findById(parentId)
                .orElseThrow(() -> new NotFoundException("Item with ID:" + parentId + "not found"));

        ItemEntity childItem = this.itemDao.findById(childId.toString())
                .orElseThrow(() -> new NotFoundException("Item with ID:" + childId + "not found"));


        parentItem.addChild(childItem, true);

        this.itemDao.save(parentItem);
        this.itemDao.save(childItem);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DigitalItemBoundary> getAllChildren(String userSpace, String userEmail, String itemSpace, String itemId) {
        ErrorType managerRoleCheck = userUtilsService.checkRoleUser(userSpace, userEmail, UserRole.MANAGER);
        if (managerRoleCheck == ErrorType.USER_DOES_NOT_EXIST)
            throw new NotFoundException("User " + userEmail + " with space ID: " + userSpace + " not found!");

        ErrorType playerRoleCheck = userUtilsService.checkRoleUser(userSpace, userEmail, UserRole.PLAYER);
        if (playerRoleCheck != ErrorType.GOOD && managerRoleCheck != ErrorType.GOOD)
            throw new NoPermissionException("User " + userEmail + " with space ID: " + userSpace +
                    " doesn't have permission for this action!");


        String parentId = this.entityConverter.toSecondaryId(itemSpace, itemId);
        ItemEntity parentItem = this.itemDao.findById(parentId)
                .orElseThrow(() -> new NotFoundException("Item with ID:" + parentId + "not found"));

                if (managerRoleCheck == ErrorType.GOOD) // User role is Manager
                return parentItem
                        .getChildren()
                        .stream()
                        .map(this.entityConverter::toBoundary)
                        .collect(Collectors.toList());
            else // User role is Player
                return parentItem
                        .getChildren()
                        .stream()
                        .filter( e -> e.isActive() )
                        .map(this.entityConverter::toBoundary)
                        .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DigitalItemBoundary> getParents(String userSpace, String userEmail, String itemSpace, String itemId) {
        ErrorType managerRoleCheck = userUtilsService.checkRoleUser(userSpace, userEmail, UserRole.MANAGER);
        if (managerRoleCheck == ErrorType.USER_DOES_NOT_EXIST)
            throw new NotFoundException("User " + userEmail + " with space ID: " + userSpace + " not found!");

        ErrorType playerRoleCheck = userUtilsService.checkRoleUser(userSpace, userEmail, UserRole.PLAYER);
        if (playerRoleCheck != ErrorType.GOOD && managerRoleCheck != ErrorType.GOOD)
            throw new NoPermissionException("User " + userEmail + " with space ID: " + userSpace +
                    " doesn't have permission for this action!");
                    
        String itemKey = this.entityConverter.toSecondaryId(itemSpace, itemId);
        ItemEntity item = this.itemDao.findById(itemKey)
                .orElseThrow(() -> new NotFoundException("Item with ID:" + itemKey + "not found"));

        if (managerRoleCheck == ErrorType.GOOD) // User role is Manager
            return item
                    .getParents()
                    .stream()
                    .map(this.entityConverter::toBoundary)
                    .collect(Collectors.toList());
        else // User role is Player
            return item
                    .getParents()
                    .stream()
                    .filter( e -> e.isActive() )
                    .map(this.entityConverter::toBoundary)
                    .collect(Collectors.toList());
    }
}
