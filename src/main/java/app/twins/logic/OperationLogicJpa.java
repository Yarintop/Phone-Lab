package app.twins.logic;

import app.boundaries.OperationBoundary;
import app.boundaries.UserIdBoundary;
import app.converters.OperationConverter;
import app.dao.ItemDao;
import app.dao.OperationDao;
import app.dao.UserDao;
import app.exceptions.NoPermissionException;
import app.exceptions.NotFoundException;
import app.twins.data.OperationEntity;
import app.twins.data.UserEntity;
import app.twins.data.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class OperationLogicJpa implements OperationsService {

    private OperationDao operationsDao;

    //DAOs for checking user & items are correct
    private UserDao usersDao;
    private ItemDao itemsDao;

    private OperationConverter converter;
    private String spaceId;

    @Autowired
    public void setDAOs(OperationDao operationsDao, UserDao usersDao, ItemDao itemsDao) {
        this.operationsDao = operationsDao;
        this.usersDao = usersDao;
        this.itemsDao = itemsDao;
    }

    @Autowired
    public void setConverter(OperationConverter converter) {
        this.converter = converter;
    }

    @Value("${spring.application.name:2021b.notdef}")
    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    @Override
    @Transactional
    public Object invokeOperation(OperationBoundary operation) {
        OperationEntity entity = converter.toEntity(operation);
        if (checkUserAndItemMissing(entity))
            throw new NotFoundException("Either the item or the user inside the operation not found!");
        entity.setOperationId(UUID.randomUUID().toString(), spaceId);
        entity.setCreatedTimestamp(new Date());
        operationsDao.save(entity);
        return operation;
    }

    @Override
    @Transactional
    public OperationBoundary invokeAsynchronous(OperationBoundary operation) {
        OperationEntity entity = converter.toEntity(operation);
        if (checkUserAndItemMissing(entity))
            throw new NotFoundException("Either the item or the user inside the operation not found!");
        entity.setOperationId(UUID.randomUUID().toString(), spaceId);
        entity.setCreatedTimestamp(new Date());
        operationsDao.save(entity);
        return converter.toBoundary(entity);
    }

    @Override
    @Transactional
    public List<OperationBoundary> getAllOperations(String adminSpace, String adminEmail) {
        Optional<UserEntity> userToCheck = usersDao.findById(new UserIdBoundary(adminSpace, adminEmail).toString());
        if (userToCheck.isPresent() && userToCheck.get().getRole() == UserRole.ADMIN)
            return StreamSupport.stream(operationsDao.findAll().spliterator(), false)
                    .map(converter::toBoundary)
                    .collect(Collectors.toList());
        throw new NoPermissionException("User: " + adminEmail + " does not permitted to view all operations");
    }

    @Override
    @Transactional
    public void deleteAllOperations(String adminSpace, String adminEmail) {
        Optional<UserEntity> userToCheck = usersDao.findById(new UserIdBoundary(adminSpace, adminEmail).toString());
        if (userToCheck.isPresent() && userToCheck.get().getRole() == UserRole.ADMIN)
            operationsDao.deleteAll();
        else
            throw new NoPermissionException("User: " + adminEmail + " does not permitted to delete operations");

    }

    @Transactional(readOnly = true)
    public boolean checkUserAndItemMissing(OperationEntity oe) {
        if (oe.getItem() == null || oe.getInvokedBy() == null) return true;
        String userKey = oe.getInvokedBy().getUserId();
        String itemKey = oe.getItem().getItemId();
        return !usersDao.findById(userKey).isPresent() && itemsDao.findById(itemKey).isPresent();
    }
}
