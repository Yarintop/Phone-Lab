package app.twins.data;

import java.util.Date;


import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import app.boundaries.UserBoundary;

@javax.persistence.Entity
@Table(name="ITEMS")
public class ItemEntity implements Entity {
	private String itemId = ""; // This line might change
	private String type = "no type";
	private String name = "no name yet";
	private boolean active = false;
	private Date createdTimestamp = new Date();
	private UserBoundary createdBy = new UserBoundary(); // This line might change
	private String location = "{}";
	private String itemAttributes = "{}";
	
	
	
	public ItemEntity() {
		super();
	}
	@Id
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
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
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="TIMESTAMP")
	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}
	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}
	@Transient
	public UserBoundary getCreatedBy() {
		return createdBy;
	}
	@Transient
	public void setCreatedBy(UserBoundary createdBy) {
		this.createdBy = createdBy;
	}
	@Lob
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	@Lob
	public String getItemAttributes() {
		return itemAttributes;
	}
	public void setItemAttributes(String itemAttributes) {
		this.itemAttributes = itemAttributes;
	}
}

