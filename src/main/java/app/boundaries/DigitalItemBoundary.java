package app.boundaries;

import com.fasterxml.jackson.annotation.JsonView;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import app.jsonViews.Views;

public class DigitalItemBoundary implements Boundary{

	@JsonView(Views.Public.class)
	private ItemIdBoundary itemId = new ItemIdBoundary();// This line might change

	@JsonView(Views.Item.class)
	private String type = "no type";

	@JsonView(Views.Item.class)
	private String name = "no name yet";

	@JsonView(Views.Item.class)
	private Boolean active = false;

	@JsonView(Views.Item.class)
	private Date createdTimestamp = new Date();

	@JsonView(Views.Item.class)
	private UserBoundary createdBy = new UserBoundary(); // This line might change

	@JsonView(Views.Item.class)
	private Map<String, Double> location = new HashMap<>();

	@JsonView(Views.Item.class)
	private Map<String, Object> itemAttributes = new HashMap<>();

	public DigitalItemBoundary() { /* Default Constructor */ }

	public DigitalItemBoundary(ItemIdBoundary itemId, String type, String name, Boolean active, Date createdTimestamp,
			UserBoundary createdBy, Map<String, Double> location, Map<String, Object> itemAttributes)
	{
		this.itemId = itemId;
		this.type = type;
		this.name = name;
		this.active = active;
		this.createdTimestamp = createdTimestamp;
		this.createdBy = createdBy;
		this.location = location;
		this.itemAttributes = itemAttributes;
	}
	
	public DigitalItemBoundary(String itemId, String itemSpace, String type, String name, Boolean active, Date createdTimestamp,
			UserBoundary createdBy, Map<String, Double> location, Map<String, Object> itemAttributes)
	{
		this(new ItemIdBoundary(itemSpace, itemId), type, name, active, createdTimestamp, createdBy, location, itemAttributes);
	}

	public UserBoundary getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(UserBoundary createdBy) {
		this.createdBy = createdBy;
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

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DigitalItemBoundary other = (DigitalItemBoundary) obj;
		if (active == null) {
			if (other.active != null)
				return false;
		} else if (!active.equals(other.active))
			return false;
		if (createdBy == null) {
			if (other.createdBy != null)
				return false;
		} else if (!createdBy.equals(other.createdBy))
			return false;
		if (createdTimestamp == null) {
			if (other.createdTimestamp != null)
				return false;
		} else if (!createdTimestamp.equals(other.createdTimestamp))
			return false;
		if (itemAttributes == null) {
			if (other.itemAttributes != null)
				return false;
		} else if (!itemAttributes.equals(other.itemAttributes))
			return false;
		if (itemId == null) {
			if (other.itemId != null)
				return false;
		} else if (!itemId.equals(other.itemId))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((active == null) ? 0 : active.hashCode());
		result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result + ((createdTimestamp == null) ? 0 : createdTimestamp.hashCode());
		result = prime * result + ((itemAttributes == null) ? 0 : itemAttributes.hashCode());
		result = prime * result + ((itemId == null) ? 0 : itemId.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "DigitalItemBoundary:\n"
				+ "itemId={" + itemId + "}\n"
				+ "type=" + type + "\n"
				+ "name=" + name + "\n"
				+ "active=" + active + "\n"
				+ "createdTimestamp=" + createdTimestamp + "\n"
				+ "createdBy=" + createdBy.getUserId() + "\n"
				+ "location=" + location + "\n"
				+ "itemAttributes=" + itemAttributes;
	}
	

}
