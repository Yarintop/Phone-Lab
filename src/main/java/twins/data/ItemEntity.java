package twins.data;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

//@javax.persistence.Entity
//@Table(name = "ITEMS")
@Document(collection="Items")
public class ItemEntity implements Entity {
    private String id = ""; // This line might change
    private String type = "no type";
    private String name = "no name yet";
    private boolean active = false;
    private Date createdTimestamp = new Date();
    private String createdBy;
    private double longitude;
    private double latitude;
    private String itemAttributes = "{}";
    @DBRef(lazy = true)
    private Set<ItemEntity> children;
    @DBRef(lazy = true)
    private Set<ItemEntity> parents;
    //  private UserIdBoundary createdBy = new UserIdBoundary(); // This line might change
    //  private UserEntity createdBy;

    
    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public ItemEntity() {
        super();
        children = new HashSet<>();
        parents = new HashSet<>();
    }

    @Id
    public String getId() {
        return id;
    }

    public void setId(String itemId) {
        this.id = itemId;
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
    @Column(name = "TIMESTAMP")
    public Date getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Date createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    @Lob
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Lob
    public String getItemAttributes() {
        return itemAttributes;
    }

    public void setItemAttributes(String itemAttributes) {
        this.itemAttributes = itemAttributes;
    }

//    @ManyToMany(fetch=FetchType.LAZY)
    public Set<ItemEntity> getChildren() {
        return children;
    }

    public void setChildren(Set<ItemEntity> children) {
        this.children = children;
    }

//    @ManyToMany(mappedBy="children", fetch=FetchType.LAZY)
    public Set<ItemEntity> getAllParents() {
        return parents;
    }

    public void setAllParents(Set<ItemEntity> parents) {
        this.parents = parents;
    }

    public void addChild(ItemEntity ie, boolean bidirectional) {
        this.children.add(ie);
        if (bidirectional)
            ie.addParent(this, false);
    }

    public void addParent(ItemEntity ie, boolean bidirectional) {
        this.parents.add(ie);
        if (bidirectional)
            ie.addChild(this, false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemEntity that = (ItemEntity) o;
        return active == that.active && Double.compare(that.longitude, longitude) == 0 && Double.compare(that.latitude, latitude) == 0 && Objects.equals(id, that.id) && Objects.equals(type, that.type) && Objects.equals(name, that.name) && Objects.equals(createdTimestamp, that.createdTimestamp) && Objects.equals(createdBy, that.createdBy) && Objects.equals(itemAttributes, that.itemAttributes) && Objects.equals(children, that.children) && Objects.equals(parents, that.parents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, name, active, createdTimestamp, createdBy, longitude, latitude, itemAttributes, children, parents);
    }
}

