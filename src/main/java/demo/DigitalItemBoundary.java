package demo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DigitalItemBoundary {
	private Map<String, String> itemId; // This line might change
	private String type;
	private String name;
	private Boolean active;
	private Date createdTimestamp;
	private Map<String, Map<String, String>> createdBy; // This line might change
	private Map<String, Double> location;
	private Map<String, Object> itemAttributes;

	public DigitalItemBoundary() { /* Default Constructor */
		this.itemId = new HashMap<>();
		this.type = "Good Type";
		this.name = "This Product Has No Name";
		this.active = false;
		this.createdTimestamp = new Date();
		this.createdBy = new HashMap<>();
		this.location = new HashMap<>();
		this.itemAttributes = new HashMap<>();
	}

	public DigitalItemBoundary(Map<String, String> itemId, String type, String name, Boolean active,
			Date createdTimestamp, Map<String, Map<String, String>> createdBy, Map<String, Double> location,
			Map<String, Object> itemAttributes) {
		this.itemId = itemId;
		this.type = type;
		this.name = name;
		this.active = active;
		this.createdTimestamp = createdTimestamp;
		this.createdBy = createdBy;
		this.location = location;
		this.itemAttributes = itemAttributes;
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

	public Map<String, Map<String, String>> getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Map<String, Map<String, String>> createdBy) {
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
