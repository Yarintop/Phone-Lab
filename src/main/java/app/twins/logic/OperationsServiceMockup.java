package app.twins.logic;

import app.boundaries.OperationBoundary;
import app.converters.OperationConverter;
import app.dummyData.DummyData;
import app.twins.data.OperationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

//@Service
public class OperationsServiceMockup implements OperationsService {

    private Map<String, OperationEntity> operations = Collections.synchronizedMap(new HashMap<>());
    private DummyData dataGenerator;
    private String spaceId;
    private OperationConverter converter;

    /**
     * Sets the spaceId value from the application.properties file
     *
     * @param spaceId the loaded spaceId value, default would be "2021b.twins"
     */
    @Value("${spring.application.name:2021b.notdef}")
    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    /**
     * This function will set the converter that will be used to
     * convert boundary objects to entity objects and vice versa
     *
     * @param converter - an instance of {@link OperationConverter}
     */
    @Autowired
    public void setConverter(OperationConverter converter) {
        this.converter = converter;
    }

    /**
     * This function will set the dummy data generator
     *
     * @param dataGenerator - an instance of {@link DummyData}
     */
    @Autowired
    public void setDummyDataGenerator(DummyData dataGenerator) {
        this.dataGenerator = dataGenerator;
    }

    /**
     * For this mockup class, this function will return {@link OperationBoundary}.
     * Just to test if all the components will work together.
     * <p>
     * This function represents invoking a sync operation.
     *
     * @param operation - the operation to invoke
     * @return return an object that represents the result of an operation (for this mockup it will be the same operation)
     */
    @Override
    public Object invokeOperation(OperationBoundary operation) {
        OperationEntity entity = converter.toEntity(operation);

        //Generate mock ID, to simulate that an ID was created for this operation
        String entityId = dataGenerator.getRandomId() + "&" + spaceId;
        entity.setOperationId(entityId);

        //Save the entity in a collection
        operations.put(entityId, entity);
        return operation;
    }

    /**
     * This function represents invoking an async operation.
     * This will create an ID for the operation and send it back to the user with the new ID
     *
     * @param operation - the operation to invoke
     * @return - {@link OperationBoundary} that will include the ID of the operation. later on the user could check it to see the status.
     */
    @Override
    public OperationBoundary invokeAsynchronous(OperationBoundary operation) {
        OperationEntity entity = converter.toEntity(operation);

        //Generate mock ID, to simulate that an ID was created for this operation
        String entityId = dataGenerator.getRandomId() + "&" + spaceId;
        entity.setOperationId(entityId);

        //Save the entity in a collection
        operations.put(entityId, entity);

        //"Send" the operation to a handler and then convert it to an entity.
        //SEND_FUNCTION()
        return converter.toBoundary(entity);

    }

    @Override
    public List<OperationBoundary> getAllOperations(String adminSpace, String adminEmail) {
        //TODO: Do something with adminSpace and adminEmail here
        return operations.values().stream().map(converter::toBoundary).collect(Collectors.toList());
    }

    @Override
    public void deleteAllOperations(String adminSpace, String adminEmail) {
        //TODO: remove only operations from adminSpace and adminEmail (?)
        operations.clear();
    }
}
