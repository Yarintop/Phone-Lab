package app.twins.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import app.boundaries.DigitalItemBoundary;
import app.boundaries.OperationBoundary;
import app.boundaries.UserBoundary;
import app.converters.ItemConverter;
import app.converters.OperationConverter;
import app.twins.data.ItemEntity;

@Service
public class ItemsServiceMockup implements ItemsService {
	
	private Map<String, Map<String, ItemEntity>> items;
    private String spaceId;
	private ItemConverter entityConverter;
	
	
    /**
     * Sets the spaceId value from the application.properties file
     *
     * @param spaceId the loaded spaceId value, default would be "2021b.twins"
     */
    @Value("${spring.application.name:2021b.twins}")
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
     * @param converter - an instance of {@link ItemConverter}
     */
	@Autowired
	public void setEntityConverter(ItemConverter entityConverter) {
		this.entityConverter = entityConverter;
	}
	
	@Override
	public DigitalItemBoundary createItem(String userSpace, String userEmail, DigitalItemBoundary item) {
		String newId = UUID.randomUUID().toString();
		String primaryId = this.entityConverter.toPrimaryId(userSpace, userEmail);
		String secondaryId = "";
		ItemEntity entity = this.entityConverter.toEntity(item);
		
		entity.setItemId(newId, userSpace);
		entity.setCreatedTimestamp(new Date());
		
		if(entity.getCreatedBy().getUserId().get("email") == null)
			entity.getCreatedBy().getUserId().put("email", userEmail);
		if(entity.getCreatedBy().getUserId().get("space") == null)
			entity.getCreatedBy().getUserId().put("space", userSpace);
		
		secondaryId = this.entityConverter.toSecondaryId(entity);
		if(this.items.get(primaryId) == null) // No Map for this userSpace & userEmail
			this.items.put(primaryId, new HashMap<>()); // No need for synchronized as it happens on the wrapper Map (Maybe?)
		
		this.items.get(primaryId).put(secondaryId, entity);
		
		return this.entityConverter.toBoundary(entity);
	}
	
	
	@Override
	public DigitalItemBoundary updateItem(String userSpace, String userEmail, String itemSpace, String itemId,
			DigitalItemBoundary update) {
		String primaryId = this.entityConverter.toPrimaryId(userSpace, userEmail);
		String secondaryId = this.entityConverter.toSecondaryId(itemSpace, itemId);
		
		// get existing message from mockup database
		if (this.items.get(primaryId) != null && this.items.get(primaryId).get(secondaryId) != null) {
			ItemEntity existing = this.items.get(primaryId).get(secondaryId);
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
			
			if (update.getItemAttributes() != null) {
				existing.getItemAttributes().putAll(update.getItemAttributes());
				dirty = true;
			}
			
			// ignore timestamp as creation timestamp is never changed

			// ignore createdBy as created user is never changed

			if (update.getLocation() != null) {
				if (update.getLocation().get("lat") != null) {
					existing.getLocation().put("lat", update.getLocation().get("lat"));
					dirty = true;
				}
				
				if (update.getLocation().get("lng") != null) {
					existing.getLocation().put("lng", update.getLocation().get("lng"));
					dirty = true;
				}
			}
			
			// update mockup database
			if (dirty) {
				this.items.get(primaryId).put(secondaryId, existing);
			}
			
			DigitalItemBoundary rv = this.entityConverter.toBoundary(existing);
			return rv;
			
		}else {
			// TODO have server return status 404 here
			throw new RuntimeException("could not find message by id: " + primaryId + "&" + secondaryId);// NullPointerException
		}
	}
	@Override
	public List<DigitalItemBoundary> getAllItems(String userSpace, String userEmail) {
		String primaryId = this.entityConverter.toPrimaryId(userSpace, userEmail);

		return this.items.get(primaryId)
				.values()
				.stream()
				.map(this.entityConverter::toBoundary)
				.collect(Collectors.toList());
	}
	@Override
	public DigitalItemBoundary getSpecificItem(String userSpace, String userEmail, String itemSpace, String itemId) {
		// MOCKUP
		String primaryId = this.entityConverter.toPrimaryId(userSpace, userEmail);
		String secondaryId = this.entityConverter.toSecondaryId(itemSpace, itemId);
		if (this.items.get(primaryId) != null && this.items.get(primaryId).get(secondaryId) != null) {
			ItemEntity entity = this.items
					.get(primaryId).get(secondaryId);
			DigitalItemBoundary boundary = entityConverter.toBoundary (entity);
			return boundary;
		}else {
			// TODO have server return status 404 here
			throw new RuntimeException("could not find message by id: " + primaryId + "&" + secondaryId);// NullPointerException
		}
	}
	@Override
	public void deleteAllItems(String adminSpace, String adminEmail) {
		this.items
		.clear();	
	}
}
