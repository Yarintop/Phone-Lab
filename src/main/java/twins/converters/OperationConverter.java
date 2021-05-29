package twins.converters;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import twins.boundaries.OperationBoundary;
import twins.boundaries.OperationIdBoundary;
import twins.dao.ItemDao;
import twins.dao.UserDao;
import twins.data.ItemEntity;
import twins.data.OperationEntity;
import twins.data.UserEntity;
import twins.exceptions.NotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class OperationConverter implements EntityConverter<OperationEntity, OperationBoundary> {

    private final ObjectMapper jackson;
    private UserConverter userConverter;
    private ItemConverter itemConverter;
    private UserDao userDao;
    private ItemDao itemDao;


    public OperationConverter(ObjectMapper jackson) {
        this.jackson = jackson;
    }

    @Autowired
    public void setConverters(ItemConverter itemConverter, UserConverter userConverter) {
        this.itemConverter = itemConverter;
        this.userConverter = userConverter;
    }

    @Autowired
    public void setConverters(UserDao userDao, ItemDao itemDao) {
        this.itemDao = itemDao;
        this.userDao = userDao;
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
            entity.setId(convertMapKey(boundary.getOperationId()));
        else
            entity.setId(""); // Empty ID string if the ID is missing
        entity.setOperationType(boundary.getType());
        entity.setCreatedTimestamp(boundary.getCreatedTimestamp());
        if(boundary.getInvokedBy() != null)
            entity.setInvokedBy(boundary.getInvokedBy().getUserId().toString());
        if(boundary.getItem() != null)
            entity.setItem(boundary.getItem().getItemId().toString());
//        entity.setOperationAttributes(boundary.getOperationAttributes());
        entity.setOperationAttributes(fromMapToJson(boundary.getOperationAttributes()));
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

        if (entity.getId() != null && entity.getId().length() > 0)
            // If there is a valid ID, convert it into map value for the boundary
            boundary.setOperationId(convertStringKey(entity.getId()));
        boundary.setType(entity.getOperationType());
        boundary.setCreatedTimestamp(entity.getCreatedTimestamp());
//      boundary.setInvokedBy(userConverter.toBoundary(entity.getInvokedBy()));
        if (entity.getInvokedBy() != null) {  //TODO: add logic when item & user not present
            Optional<UserEntity> userEntity = userDao.findById(entity.getInvokedBy());
            if (userEntity.isPresent())
                boundary.setInvokedBy(userConverter.toBoundary(userEntity.get()));
            else
                throw new NotFoundException("Related user not found (" + entity.getInvokedBy() + ") in the given operation");
        }
        if (entity.getItem() != null) {
            Optional<ItemEntity> itemEntity = itemDao.findById(entity.getItem());
            if (itemEntity.isPresent())
                boundary.setItem(itemConverter.toBoundary(itemEntity.get()));
            else
                throw new NotFoundException("Related user not found (" + entity.getItem() + ") in the given operation");
//            boundary.setItem(itemConverter.toBoundary(entity.getItem()));
        }

//        boundary.setOperationAttributes(entity.getOperationAttributes());
        boundary.setOperationAttributes(fromJsonToMap(entity.getOperationAttributes()));
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
        return new OperationIdBoundary(idParts[1], idParts[0]);
    }

    public String fromMapToJson(Map<String, Object> value) { // marshalling: Java->JSON
        try {
            return this.jackson.writeValueAsString(value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, Object> fromJsonToMap(String json) { // unmarshalling: JSON->Java
        try {
            return this.jackson.readValue(json, Map.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
