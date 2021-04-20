package app.converters;

import app.boundaries.OperationBoundary;
import app.boundaries.OperationIdBoundary;
import app.twins.data.OperationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class OperationConverter implements EntityConverter<OperationEntity, OperationBoundary> {

    private UserConverter userConverter;
    private ItemConverter itemConverter;

    @Autowired
    public void setConverters(ItemConverter itemConverter, UserConverter userConverter) {
        this.itemConverter = itemConverter;
        this.userConverter = userConverter;
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
        entity.setOperationType(boundary.getType());
        entity.setCreatedTimestamp(boundary.getCreatedTimestamp());
//        entity.setInvokedBy(boundary.getInvokedBy());
//        entity.setItem(boundary.getItem());
        entity.setInvokedBy(userConverter.toEntity(boundary.getInvokedBy()));
        entity.setItem(itemConverter.toEntity(boundary.getItem()));
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
        boundary.setType(entity.getOperationType());
        boundary.setCreatedTimestamp(entity.getCreatedTimestamp());
        if (entity.getInvokedBy() != null)
            boundary.setInvokedBy(userConverter.toBoundary(entity.getInvokedBy()));
        if (entity.getItem() != null)
            boundary.setItem(itemConverter.toBoundary(entity.getItem()));

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
    public String convertMapKey(OperationIdBoundary operationIdMap) {
        String id = operationIdMap.getId();
        String spaceId = operationIdMap.getSpace();
        return id + "&" + spaceId;
    }

    /**
     * Convert string format key to key-value map for boundary object
     * Assumes that the ID string in the format id&spaceId and '&' is not in the 'id' or 'spaceId'
     *
     * @param operationIdString - ID string in format: id&spaceId
     * @return {@link HashMap} that represent the id for a boundary object
     */
    public OperationIdBoundary convertStringKey(String operationIdString) {
        String[] idParts = operationIdString.split("&");
        return new OperationIdBoundary(idParts[0], idParts[1]);
    }
}
