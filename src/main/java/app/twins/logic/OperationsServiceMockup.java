package app.twins.logic;

import app.boundaries.OperationBoundary;
import app.converters.OperationConverter;
import app.dummyData.DummyData;
import app.twins.data.OperationEntity;
import app.twins.data.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class OperationsServiceMockup implements OperationsService {

    private Map<String, OperationEntity> operations = Collections.synchronizedMap(new HashMap<>());
    private DummyData dataGenerator;
    private String spaceId;
    private OperationConverter converter;


    @Autowired
    public void setConverter(OperationConverter converter) {
        this.converter = converter;
    }

    @Autowired
    public void setDummyDataGenerator(DummyData dataGenerator) {
        this.dataGenerator = dataGenerator;
    }

    /**
     * For this mockup class, this function will return {@link OperationBoundary}.
     * Just to test if all the components will work together.
     * <p>
     * This function represents invoking a synchronous operation.
     *
     * @param operation - the operation to invoke
     * @return return an object that represents the result of an operation
     */
    @Override
    public Object invokeOperation(OperationBoundary operation) {
        OperationEntity entity = converter.toEntity(operation);
        //TODO: use dummy data generator component

        return operation;
    }

    @Override
    public OperationBoundary invokeAsynchronous(OperationBoundary operation) {
        //TODO: Implement here operationHandler to handle the operations (async)

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
