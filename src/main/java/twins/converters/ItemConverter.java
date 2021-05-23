package twins.converters;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import twins.boundaries.DigitalItemBoundary;
import twins.boundaries.ItemIdBoundary;
import twins.dao.UserDao;
import twins.data.ItemEntity;
import twins.data.UserEntity;
import twins.exceptions.NotFoundException;

import java.util.Map;
import java.util.Optional;

@Component
public class ItemConverter implements EntityConverter<ItemEntity, DigitalItemBoundary> {
    private final ObjectMapper jackson;

    private UserConverter userConverter;
    private UserDao userDao;

    @Autowired
    public void setConverters(UserConverter userConverter) {
        this.userConverter = userConverter;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public ItemConverter() {
        this.jackson = new ObjectMapper();
    }


    @Override
    public ItemEntity toEntity(DigitalItemBoundary boundaryObject) {
        ItemEntity rv = new ItemEntity();

        rv.setId(this.convertMapKey(boundaryObject.getItemId()));
        rv.setType(boundaryObject.getType());
        rv.setName(boundaryObject.getName());
        if (boundaryObject.isActive() != null)
            rv.setActive(boundaryObject.isActive());
        rv.setCreatedTimestamp(boundaryObject.getCreatedTimestamp());
//        rv.setCreatedBy(userConverter.toEntity(boundaryObject.getCreatedBy()));
        rv.setCreatedBy(boundaryObject.getCreatedBy().getUserId().toString());
        rv.setLongitude(boundaryObject.getLocation().getLng());
        rv.setLatitude(boundaryObject.getLocation().getLat());
        rv.setItemAttributes(this.fromMapToJson(boundaryObject.getItemAttributes()));

        return rv;
    }

    @Override
    public DigitalItemBoundary toBoundary(ItemEntity entityObject) {
        DigitalItemBoundary rv = new DigitalItemBoundary();
        System.err.println(entityObject);
        rv.setItemId(this.convertStringKey(entityObject.getId()));
        rv.setType(entityObject.getType());
        rv.setName(entityObject.getName());
        rv.setActive(entityObject.isActive());
        rv.setCreatedTimestamp(entityObject.getCreatedTimestamp());
//        rv.setCreatedBy(userConverter.toBoundary(entityObject.getCreatedBy())); // This line might change

        Optional<UserEntity> userEntity = userDao.findById(entityObject.getCreatedBy());
        // This line might change
        if (userEntity.isPresent())
            rv.setCreatedBy(userConverter.toBoundary(userEntity.get()));
        else
            throw new NotFoundException("Related user not found (" + entityObject.getCreatedBy() + ") in the given item");
        rv.setLocation(entityObject.getLatitude(), entityObject.getLongitude());
        rv.setItemAttributes((Map<String, Object>) this.fromJsonToMap(entityObject.getItemAttributes(), Map.class));

        return rv;
    }

    public String toPrimaryId(String userSpace, String userEmail) {
        if (userSpace == null)
            userSpace = "";
        if (userEmail == null)
            userEmail = "";
        return "userSpace=" + userSpace + "&userEmail=" + userEmail;
    }

    public String toSecondaryId(String itemSpace, String itemId) {
        if (itemSpace == null)
            itemSpace = "";
        if (itemId == null)
            itemId = "";

        return itemId + "&" + itemSpace;
    }

    /**
     * Convert ItemIdBoundary to String format, used for entity classes.
     *
     * @param itemId - ItemIdBoundary from a boundary object
     * @return String that represent the id in the format: id&spaceId
     */
    public String convertMapKey(ItemIdBoundary itemId) {

        String id = null;
        String spaceId = null;

        if (itemId != null) {
            id = itemId.getId();
            spaceId = itemId.getSpace();
        }
        return id + "&" + spaceId;
    }

    /**
     * Convert string format key to key-value map for boundary object
     * Assumes that the ID string in the format id&spaceId and '&' is not in the 'id' or 'spaceId'
     *
     * @param itemIdString - ID string in format: id&spaceId
     * @return {@link ItemIdBoundary} that represent the id for a boundary object
     */
    public ItemIdBoundary convertStringKey(String itemIdString) {
        String id = null;
        String spaceId = null;
        if (itemIdString != null) {
            String[] idParts = itemIdString.split("&");
            id = idParts[0];
            if (idParts.length > 1)
                spaceId = idParts[1];
        }
        return new ItemIdBoundary(spaceId, id);
    }

    public String fromMapToJson(Object value) { // marshalling: Java->JSON

        try {
            return this.jackson.writeValueAsString(value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    public Object fromJsonToMap(String json, Class cls) { // unmarshalling: JSON->Java
        try {
            return this.jackson.readValue(json, cls);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
