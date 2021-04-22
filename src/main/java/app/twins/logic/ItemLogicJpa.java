package app.twins.logic;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import app.boundaries.ItemIdBoundary;
import app.boundaries.UserIdBoundary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.dao.ItemDao;
import app.boundaries.DigitalItemBoundary;
import app.boundaries.UserBoundary;
import app.converters.ItemConverter;
import app.exceptions.NotFoundException;
import app.twins.data.ItemEntity;

import javax.swing.text.html.Option;

@Service
public class ItemLogicJpa implements UpdatedItemsService {
    private ItemDao itemDao;
    //private Map<String,ItemEntity> items;
    private String spaceId;
    private ItemConverter entityConverter;


    /**
     * Sets the spaceId value from the application.properties file
     *
     * @param spaceId the loaded spaceId value, default would be "2021b.twins"
     */
    @Value("${spring.application.name:2021b.notdef}")
    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    public ItemLogicJpa() {
    }

    @Autowired
    public void setItemDao(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    /**
     * This function will set the converter that will be used to
     * convert boundary objects to entity objects and vice versa
     *
     * @param entityConverter - an instance of {@link ItemConverter}
     */
    @Autowired
    public void setEntityConverter(ItemConverter entityConverter) {
        this.entityConverter = entityConverter;
    }

    @Override
    @Transactional//(readOnly = false)
    public DigitalItemBoundary createItem(String userSpace, String userEmail, DigitalItemBoundary item) {
        userSpace = this.spaceId;
        String newId = UUID.randomUUID().toString();

        // Set space of the created item to be that of our project.
        item.setItemId(newId, userSpace);
        item.setCreatedTimestamp(new Date());

        //Update the info of the creating user to be those in the REST API /twins/items/{userSpace}/{userEmail})

        item.setCreatedBy(new UserBoundary(userEmail, userSpace));

        if (item.getLocation() == null)
            item.setLocation(-1, -1);
        if (item.getItemAttributes() == null)
            item.setItemAttributes(new HashMap<>());

        ItemEntity entity = this.entityConverter.toEntity(item);
        this.itemDao.save(entity);

        DigitalItemBoundary temp = this.entityConverter.toBoundary(entity);

        return this.entityConverter.toBoundary(entity);
    }


    @Override
    @Transactional//(readOnly = false)
    public DigitalItemBoundary updateItem(String userSpace, String userEmail, String itemSpace, String itemId,
                                          DigitalItemBoundary update) {
        String itemKey = this.entityConverter.toSecondaryId(itemSpace, itemId);
        Optional<ItemEntity> optionalItem = this.itemDao.findById(itemKey);
        // get existing message from mockup database
        if (optionalItem.isPresent()) {
            DigitalItemBoundary existing = this.entityConverter.toBoundary(optionalItem.get());
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
                this.itemDao.save(this.entityConverter.toEntity(existing));
            }

            return existing;

        } else {
            throw new NotFoundException("could not find message by id: " + itemKey);// NullPointerException
        }
    }

    /**
     * Get all items created by UserId= {email: userEmail, space: userSpace}
     */
    @Override
    @Transactional(readOnly = true)
    public List<DigitalItemBoundary> getAllItems(String userSpace, String userEmail) {
        Iterable<ItemEntity> allEntities = this.itemDao.findAll();
        return StreamSupport
                .stream(allEntities.spliterator(), false) // get stream from iterable
                .map(this.entityConverter::toBoundary)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public DigitalItemBoundary getSpecificItem(String userSpace, String userEmail, String itemSpace, String itemId) {
        // MOCKUP
        String itemKey = this.entityConverter.toSecondaryId(itemSpace, itemId);
        Optional<ItemEntity> optionalItem = this.itemDao.findById(itemKey);

        if (optionalItem.isPresent()) {
            ItemEntity entity = optionalItem.get();
            return entityConverter.toBoundary(entity);
        } else {
            // TODO have server return status 404 here
            throw new NotFoundException("could not find message by id: " + itemKey);// NullPointerException
        }
    }

    @Override
    @Transactional//(readOnly = false)
    public void deleteAllItems(String adminSpace, String adminEmail) {
        this.itemDao.deleteAll();
    }

    @Override
    @Transactional
    public void bindChild(String userSpace, String userEmail, String itemSpace, String itemId, ItemIdBoundary childId) {
        String parentId = this.entityConverter.toSecondaryId(itemSpace, itemId);

        ItemEntity parentItem = this.itemDao.findById(parentId)
                .orElseThrow(() -> new NotFoundException("Item with ID:" + parentId + "not found"));

        ItemEntity childItem = this.itemDao.findById(childId.toString())
                .orElseThrow(() -> new NotFoundException("Item with ID:" + childId + "not found"));


        parentItem.addChild(childItem, true);

        this.itemDao.save(parentItem);
        this.itemDao.save(childItem);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DigitalItemBoundary> getAllChildren(String userSpace, String userEmail, String itemSpace, String itemId) {
        String parentId = this.entityConverter.toSecondaryId(itemSpace, itemId);
        ItemEntity parentItem = this.itemDao.findById(parentId)
                .orElseThrow(() -> new NotFoundException("Item with ID:" + parentId + "not found"));

        return parentItem.getChildren().stream().map(entityConverter::toBoundary).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DigitalItemBoundary> getParents(String userSpace, String userEmail, String itemSpace, String itemId) {
        String itemKey = this.entityConverter.toSecondaryId(itemSpace, itemId);

        ItemEntity item = this.itemDao.findById(itemKey)
                .orElseThrow(() -> new NotFoundException("Item with ID:" + itemKey + "not found"));

        return item.getParents().stream().map(this.entityConverter::toBoundary).collect(Collectors.toList());
    }
}
