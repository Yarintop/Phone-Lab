package app.boundaries;

import com.fasterxml.jackson.annotation.JsonView;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

}
