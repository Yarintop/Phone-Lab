package app.converters;

import app.boundaries.DigitalItemBoundary;
import app.twins.data.ItemEntity;


import org.springframework.stereotype.Component;

@Component
public class ItemConverter implements EntityConverter<ItemEntity, DigitalItemBoundary> {
    @Override
    public ItemEntity toEntity(DigitalItemBoundary boundaryObject) {
		ItemEntity rv = new ItemEntity();
		
		rv.setItemId(boundaryObject.getItemId());
		rv.setType(boundaryObject.getType());
		rv.setName(boundaryObject.getName());
		if (boundaryObject.getActive() != null)
			rv.setActive(boundaryObject.getActive());
		rv.setCreatedTimestamp(boundaryObject.getCreatedTimestamp());
		rv.setCreatedBy(boundaryObject.getCreatedBy());
		rv.setLocation(boundaryObject.getLocation());
		rv.setItemAttributes(boundaryObject.getItemAttributes());
		
		return rv;
    }

    @Override
    public DigitalItemBoundary toBoundary(ItemEntity entityObject) {
		DigitalItemBoundary rv = new DigitalItemBoundary();
    	
		rv.setItemId(entityObject.getItemId());
		rv.setType(entityObject.getType());
		rv.setName(entityObject.getName());
		rv.setActive(entityObject.isActive());
		rv.setCreatedTimestamp(entityObject.getCreatedTimestamp());
		rv.setCreatedBy(entityObject.getCreatedBy()); // This line might change
		rv.setLocation(entityObject.getLocation());
		rv.setItemAttributes(entityObject.getItemAttributes());
		
		return rv;
    }
    
    public String toSecondaryId(ItemEntity item)
    {
    	String itemSpace = "";
    	String itemId = "";
    	if(item.getItemId() != null)
    	{
    		itemId = item.getItemId().get("id");
    		itemSpace = item.getItemId().get("space");
    	}
    		
    	return toSecondaryId(itemSpace, itemId);
    }
    
    public String toPrimaryId(String userSpace, String userEmail)
    {
    	if(userSpace == null)
    		userSpace = "";
    	if(userEmail == null)
    		userEmail = "";
    	return "userSpace=" + userSpace + "&userEmail=" + userEmail;
    }
     
    public String toSecondaryId(String itemSpace, String itemId)
    {
    	if(itemSpace == null)
    		itemSpace = "";
    	if(itemId == null)
    		itemId = "";
    	
    	return "itemSpace=" + itemSpace + "&itemId=" + itemId;
    }
}
