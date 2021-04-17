package app.twins.logic;

import app.boundaries.OperationBoundary;
import app.converters.OperationConverter;
import app.dao.OperationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return null;
    }

    @Override
    public OperationBoundary invokeAsynchronous(OperationBoundary operation) {
        return null;
    }

    @Override
    public List<OperationBoundary> getAllOperations(String adminSpace, String adminEmail) {
        return null;
    }

    @Override
    public void deleteAllOperations(String adminSpace, String adminEmail) {

    }
}
