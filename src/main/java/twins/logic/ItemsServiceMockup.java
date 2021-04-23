package twins.logic;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import twins.converters.UserConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import twins.boundaries.DigitalItemBoundary;
import twins.boundaries.UserBoundary;
import twins.converters.ItemConverter;
import twins.exceptions.NotFoundException;
import twins.data.ItemEntity;

//@Service
public class ItemsServiceMockup implements ItemsService {

    private Map<String, ItemEntity> items;
    private String spaceId;
    private ItemConverter entityConverter;
    private UserConverter userConverter;


    /**
     * Sets the spaceId value from the application.properties file
     *
     * @param spaceId the loaded spaceId value, default would be "2021b.twins"
     */
    @Value("${spring.application.name:2021b.notdef}")
    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    public ItemsServiceMockup() {
        // create a thread safe collection
        this.items = Collections.synchronizedMap(new HashMap<>());
    }

    /**
     * This function will set the converter that will be used to
     * convert boundary objects to entity objects and vice versa
     *
     * @param entityConverter - an instance of {@link ItemConverter}
     */
    @Autowired
    public void setConverters(ItemConverter entityConverter, UserConverter userConverter) {
        this.entityConverter = entityConverter;
        this.userConverter = userConverter;
    }

    @Override
    public DigitalItemBoundary createItem(String userSpace, String userEmail, DigitalItemBoundary item) {
        userSpace = this.spaceId;
        String newId = UUID.randomUUID().toString();
//        UserEntity createdBy = this.userConverter.toEntity(item.getCreatedBy());
        String itemKey = this.entityConverter.toSecondaryId(userSpace, newId);

        // Set space of the created item to be that of our project.
        item.setItemId(newId, userSpace);
        item.setCreatedTimestamp(new Date());
//
        //Update the info of the creating user to be those in the REST API /twins/items/{userSpace}/{userEmail})
        item.setCreatedBy(new UserBoundary(userEmail, userSpace));

        ItemEntity entity = this.entityConverter.toEntity(item);
        this.items.put(itemKey, entity);

        return this.entityConverter.toBoundary(entity);
    }


    @Override
    public DigitalItemBoundary updateItem(String userSpace, String userEmail, String itemSpace, String itemId,
                                          DigitalItemBoundary update) {
        String itemKey = this.entityConverter.toSecondaryId(itemSpace, itemId);

        // get existing message from mockup database
        if (this.items.get(itemKey) != null) {
            DigitalItemBoundary existing = this.entityConverter.toBoundary(this.items.get(itemKey));
            boolean dirty = false;

            // update collection and return update
            if (update.getActive() != null) {
                existing.setActive(update.getActive());
                dirty = true;
            }

            if (update.getType() != null) {
                existing.setType(update.getType());
                dirty = true;
            }

            if (update.getName() != null) {
                existing.setName(update.getName());
                dirty = true;
            }

            // ignore id from update - as ids are never changed

            if (update.getItemAttributes() != null && !update.getItemAttributes().equals(existing.getItemAttributes())) {
                existing.setItemAttributes(update.getItemAttributes());
                dirty = true;
            }

            // ignore timestamp as creation timestamp is never changed

            // ignore createdBy as created user is never changed

            if (update.getLocation() != null) {
                existing.setLocation(update.getLocation());
                dirty = true;
            }

            // update mockup database
            if (dirty) {
                this.items.put(itemKey, this.entityConverter.toEntity(existing));
            }

            return existing;

        } else {
            // TODO have server return status 404 here
            throw new NotFoundException("could not find message by id: " + itemKey);// NullPointerException
        }
    }

    /**
     * Get all items created by UserId= {email: userEmail, space: userSpace}
     */
    @Override
    public List<DigitalItemBoundary> getAllItems(String userSpace, String userEmail) {
        return this.items
                .values()
                .stream()
                .map(this.entityConverter::toBoundary)
                .collect(Collectors.toList());
    }

    @Override
    public DigitalItemBoundary getSpecificItem(String userSpace, String userEmail, String itemSpace, String itemId) {
        // MOCKUP
        String itemKey = this.entityConverter.toSecondaryId(itemSpace, itemId);
        if (this.items.get(itemKey) != null) {
            ItemEntity entity = this.items.get(itemKey);
            DigitalItemBoundary boundary = entityConverter.toBoundary(entity);
            return boundary;
        } else {
            // TODO have server return status 404 here
            throw new RuntimeException("could not find message by id: " + "primaryId" + "&" + "secondaryId");// NullPointerException
        }
    }

    @Override
    public void deleteAllItems(String adminSpace, String adminEmail) {
        this.items
                .clear();
    }
}
