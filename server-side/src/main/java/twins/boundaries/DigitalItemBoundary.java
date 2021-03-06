package twins.boundaries;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DigitalItemBoundary implements Boundary {

    private ItemIdBoundary itemId;
    private String type;
    private String name;
    private Boolean active;
    private Date createdTimestamp;
    private UserBoundary createdBy;
    // private UserIdBoundary createdBy = new UserIdBoundary(); // This line might change

    // private Map<String, Object> location = new HashMap<>();
    private LocationBoundary location;

    private Map<String, Object> itemAttributes;

    public DigitalItemBoundary() { /* Default Constructor */ }

    public DigitalItemBoundary(ItemIdBoundary itemId, String type, String name, Boolean active, Date createdTimestamp,
                               UserBoundary createdBy, LocationBoundary location, Map<String, Object> itemAttributes) {
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
                               UserBoundary createdBy, LocationBoundary location, Map<String, Object> itemAttributes) {
        this(new ItemIdBoundary(itemSpace, itemId), type, name, active, createdTimestamp, createdBy, location, itemAttributes);
    }

    @JsonIgnore
    public UserBoundary getCreatedBy() {
        return createdBy;
    }

    @JsonProperty("createdBy")
    // @JsonInclude(JsonInclude.Include.NON_NULL)
    // @JsonIgnore
    public void setCreatedBy(UserBoundary createdBy) {
        this.createdBy = createdBy;
    }

    @JsonProperty("createdBy")
    public Map<String, UserIdBoundary> getCreatedById() {
        if (createdBy == null) return null;
        HashMap<String, UserIdBoundary> temp = new HashMap<>();
        temp.put("userId", createdBy.getUserId());
        return temp;
        // return createdBy == null ? null : createdBy.getUserId();
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

    public Boolean isActive() {
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

    public LocationBoundary getLocation() {
        return location;
    }

    public void setLocation(double lat, double lng) {
        this.location = new LocationBoundary(lat, lng);
    }

    public void setLocation(LocationBoundary location) {
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
            return other.type == null;
        } else return type.equals(other.type);
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
                + "createdBy=" + createdBy + "\n"
                + "location=" + location + "\n"
                + "itemAttributes=" + itemAttributes;
    }


}
