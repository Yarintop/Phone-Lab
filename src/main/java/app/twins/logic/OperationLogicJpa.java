package app.twins.logic;

import app.boundaries.OperationBoundary;
import app.converters.OperationConverter;
import app.dao.OperationDao;
import app.twins.data.OperationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class OperationLogicJpa implements OperationsService {

    private OperationDao operationsDao;
    private OperationConverter converter;
    private String spaceId;

    @Autowired
    public void setOperationsDao(OperationDao operationsDao) {
        this.operationsDao = operationsDao;
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
    public Object invokeOperation(OperationBoundary operation) {
        OperationEntity entity = converter.toEntity(operation);
        operationsDao.save(entity);
        return operation;
    }

    @Override
    public OperationBoundary invokeAsynchronous(OperationBoundary operation) {
        OperationEntity entity = converter.toEntity(operation);
        entity.setOperationId(UUID.randomUUID().toString(), spaceId);
        operationsDao.save(entity);
        return converter.toBoundary(entity);
    }

    @Override
    public List<OperationBoundary> getAllOperations(String adminSpace, String adminEmail) {
        //TODO: Check if admin user is correct
        return StreamSupport.stream(operationsDao.findAll().spliterator(), false)
                .map(converter::toBoundary)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAllOperations(String adminSpace, String adminEmail) {
        //TODO: Check if admin user is correct
        operationsDao.deleteAll();

    }
}
