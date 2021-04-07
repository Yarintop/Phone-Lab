package app.converters;

import app.boundaries.OperationBoundary;
import app.twins.data.OperationEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class OperationConverter implements EntityConverter<OperationEntity, OperationBoundary> {


    @Override
    public OperationEntity toEntity(OperationBoundary boundary) {
        OperationEntity entity = new OperationEntity();
        if (boundary.getOperationId() != null)
            entity.setOperationId(boundary.getOperationId());
        entity.setOperationType(boundary.getOperationType());
        entity.setCreatedTimestamp(boundary.getCreatedTimestamp());
        entity.setInvokedBy(boundary.getInvokedBy());
        entity.setItem(boundary.getItem());
        entity.setOperationAttributes(boundary.getOperationAttributes());
        return entity;
    }

    @Override
    public OperationBoundary toBoundary(OperationEntity entity) {
        OperationBoundary boundary = new OperationBoundary();

        boundary.setOperationId(entity.getOperationId());
//        boundary.setOperationId(entity.getOperationId());
        boundary.setOperationType(entity.getOperationType());
        boundary.setCreatedTimestamp(entity.getCreatedTimestamp());
        boundary.setInvokedBy(entity.getInvokedBy());
        boundary.setItem(entity.getItem());
        boundary.setOperationAttributes(entity.getOperationAttributes());
        return boundary;
    }

    private String convertMapKey(Map<String, String> operationIdMap) {
        String operationIdString = "";
        //TODO: implement this after updating the application.properties to avoid hard coding values
        return operationIdString;
    }

    private Map<String, String> convertStringKey(String operationIdString){
        Map<String, String> operationIdMap = new HashMap<>();
        //TODO: implement this after updating the application.properties to avoid hard coding values
        return operationIdMap;
    }
}
