package app.boundaries;

import com.fasterxml.jackson.annotation.JsonView;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import app.jsonViews.Views;

public class DigitalItemBoundary implements Boundary{

	@JsonView(Views.Public.class)
	private Map<String, String> itemId = new HashMap<>(); // This line might change

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

	public DigitalItemBoundary(Map<String, String> itemId, String type, String name, Boolean active, Date createdTimestamp,
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

	public UserBoundary getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(UserBoundary createdBy) {
		this.createdBy = createdBy;
	}

	public Map<String, String> getItemId() {
		return itemId;
	}

	public void setItemId(Map<String, String> itemId) {
		this.itemId = itemId;
	}
	public void setItemId(String itemId, String itemSpace) {
		this.itemId = new HashMap<>();
		this.itemId.put("id", itemId);
		this.itemId.put("space", itemSpace);
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
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		DigitalItemBoundary that = (DigitalItemBoundary) o;
		return Objects.equals(itemId, that.itemId) && Objects.equals(type, that.type) &&
				Objects.equals(name, that.name) && Objects.equals(active, that.active) &&
				Objects.equals(createdTimestamp, that.createdTimestamp) &&
				Objects.equals(createdBy, that.createdBy) && Objects.equals(location, that.location) &&
				Objects.equals(itemAttributes, that.itemAttributes);
	}

	@Override
	public int hashCode() {
		return Objects.hash(itemId, type, name, active, createdTimestamp, createdBy, location, itemAttributes);
	}

	@Override
	public String toString() {
		return "DigitalItemBoundary:\n"
				+ "itemId=" + itemId + "\n"
				+ "type=" + type + "\n"
				+ "name=" + name + "\n"
				+ "active=" + active + "\n"
				+ "createdTimestamp=" + createdTimestamp + "\n"
				+ "createdBy=" + createdBy.getUserId() + "\n"
				+ "location=" + location + "\n"
				+ "itemAttributes=" + itemAttributes;
	}
	

}
