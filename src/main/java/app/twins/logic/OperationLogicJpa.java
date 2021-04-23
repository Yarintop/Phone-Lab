package app.twins.logic;

import app.boundaries.OperationBoundary;
import app.converters.OperationConverter;
import app.dao.ItemDao;
import app.dao.OperationDao;
import app.dao.UserDao;
import app.exceptions.NotFoundException;
import app.twins.data.OperationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
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
        //TODO: Check if admin user is correct
        return StreamSupport.stream(operationsDao.findAll().spliterator(), false)
                .map(converter::toBoundary)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteAllOperations(String adminSpace, String adminEmail) {
        //TODO: Check if admin user is correct
        operationsDao.deleteAll();

    }

    @Transactional(readOnly = true)
    public boolean checkUserAndItemMissing(OperationEntity oe) {
        if (oe.getItem() == null || oe.getInvokedBy() == null) return true;
        String userKey = oe.getInvokedBy().getUserId();
        String itemKey = oe.getItem().getItemId();
        return !usersDao.findById(userKey).isPresent() && itemsDao.findById(itemKey).isPresent();
    }
}
