package app.twins.logic;

import app.boundaries.OperationBoundary;
import app.twins.data.OperationEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class OperationsServiceMockup implements OperationsService {

    private Map<String, OperationEntity> operations = Collections.synchronizedMap(new HashMap<>());
    //TODO: Add entity Converter


    @Override
    public Object invokeOperation(OperationBoundary operation) {
        return null;
    }

    @Override
    public OperationBoundary invokeAsynchronous(OperationBoundary operation) {
        return null;
    }

    @Override
    public OperationBoundary[] getAllOperations(String adminSpace, String adminEmail) {
        return new OperationBoundary[0];
    }

    @Override
    public void deleteAllOperations(String adminSpace, String adminEmail) {

    }
}
