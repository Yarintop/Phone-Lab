package app.converters;

import app.boundaries.OperationBoundary;
import app.twins.data.OperationEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class OperationConverter implements EntityConverter<OperationEntity, OperationBoundary> {

    private String spaceIdKey;
    private String idKey;

    /**
     * Sets the spaceIdKey value from the application.properties file, future proofing for future use.
     *
     * @param spaceIdKey the loaded spaceId value, default would be "spaceId"
     */
    @Value("${key.space:spaceId}")
    public void setSpaceIdKey(String spaceIdKey) {
        this.spaceIdKey = spaceIdKey;
    }

    /**
     * Sets the idKey value from the application.properties file, future proofing for future use.
     *
     * @param idKey the loaded spaceId value, default would be "id"
     */
    @Value("${key.id:id}")
    public void setIdKey(String idKey) {
        this.idKey = idKey;
    }

    /**
     * This function will convert a boundary object to entity for all operations
     *
     * @param boundary - {@link OperationBoundary} that represents an operation
     * @return {@link OperationEntity} that represents an operation
     */
    @Override
    public OperationEntity toEntity(OperationBoundary boundary) {
        OperationEntity entity = new OperationEntity();
        if (boundary.getOperationId() != null)
            // If there is an ID, convert it into a single string
            entity.setOperationId(convertMapKey(boundary.getOperationId()));
        else
            entity.setOperationId(""); // Empty ID string if the ID is missing
        entity.setOperationType(boundary.getOperationType());
        entity.setCreatedTimestamp(boundary.getCreatedTimestamp());
        entity.setInvokedBy(boundary.getInvokedBy());
        entity.setItem(boundary.getItem());
        entity.setOperationAttributes(boundary.getOperationAttributes());
        return entity;
    }

    /**
     * This function will convert an entity object to boundary object for all operations
     *
     * @param entity - {@link OperationEntity} that represents an operation
     * @return {@link OperationBoundary} that represents an operation
     */
    @Override
    public OperationBoundary toBoundary(OperationEntity entity) {
        OperationBoundary boundary = new OperationBoundary();

        if (entity.getOperationId() != null && entity.getOperationId().length() > 0)
            // If there is a valid ID, convert it into map value for the boundary
            boundary.setOperationId(convertStringKey(entity.getOperationId()));
        boundary.setOperationType(entity.getOperationType());
        boundary.setCreatedTimestamp(entity.getCreatedTimestamp());
        boundary.setInvokedBy(entity.getInvokedBy());
        boundary.setItem(entity.getItem());
        boundary.setOperationAttributes(entity.getOperationAttributes());
        return boundary;
    }

    /**
     * Convert Map key to String format, used for entity classes.
     * Assumes that the expected key values will be present in the Map
     *
     * @param operationIdMap - ID Map from a boundary object
     * @return String that represent the id in the format: id&spaceId
     */
    public String convertMapKey(Map<String, String> operationIdMap) {
        String id = operationIdMap.get(idKey);
        String spaceId = operationIdMap.get(spaceIdKey);
        return id + "&" + spaceId;
    }

    /**
     * Convert string format key to key-value map for boundary object
     * Assumes that the ID string in the format id&spaceId and '&' is not in the 'id' or 'spaceId'
     *
     * @param operationIdString - ID string in format: id&spaceId
     * @return {@link HashMap} that represent the id for a boundary object
     */
    public Map<String, String> convertStringKey(String operationIdString) {
        Map<String, String> operationIdMap = new HashMap<>();
        String[] idParts = operationIdString.split("&");
        operationIdMap.put(idKey, idParts[0]);
        operationIdMap.put(spaceIdKey, idParts[1]);
        return operationIdMap;
    }
}
