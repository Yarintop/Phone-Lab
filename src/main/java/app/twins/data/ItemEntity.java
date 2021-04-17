package app.twins.data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import app.boundaries.ItemIdBoundary;
import app.boundaries.UserBoundary;

public class ItemEntity implements Entity {
	private ItemIdBoundary itemId = new ItemIdBoundary(); // This line might change
	private String type = "no type";
	private String name = "no name yet";
	private boolean active = false;
	private Date createdTimestamp = new Date();
	private UserBoundary createdBy = new UserBoundary(); // This line might change
	private Map<String, Double> location = new HashMap<>();
	private Map<String, Object> itemAttributes = new HashMap<>();
	
	
	
	public ItemEntity() {
		super();
	}
	
	public ItemIdBoundary getItemId() {
		return itemId;
	}
	public void setItemId(ItemIdBoundary itemId) {
		this.itemId = itemId;
	}
	public void setItemId(String itemId, String itemSpace) {
		this.itemId = new ItemIdBoundary(itemSpace, itemId);
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}
	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}
	public UserBoundary getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(UserBoundary createdBy) {
		this.createdBy = createdBy;
	}
	public Map<String, Double> getLocation() {
		return location;
	}
	public void setLocation(Map<String, Double> location) {
		this.location = location;
	}
	public Map<String, Object> getItemAttributes() {
		return itemAttributes;
	}
	public void setItemAttributes(Map<String, Object> itemAttributes) {
		this.itemAttributes = itemAttributes;
	}
}

