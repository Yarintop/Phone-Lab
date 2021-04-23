package twins.data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


import javax.persistence.*;

@javax.persistence.Entity
@Table(name = "ITEMS")
public class ItemEntity implements Entity {
    private String itemId = ""; // This line might change
    private String type = "no type";
    private String name = "no name yet";
    private boolean active = false;
    private Date createdTimestamp = new Date();
//    private UserIdBoundary createdBy = new UserIdBoundary(); // This line might change
    private UserEntity createdBy;
    private String location = "{}";
    private String itemAttributes = "{}";

    private Set<ItemEntity> children;

    private Set<ItemEntity> parents;


    public ItemEntity() {
        super();
        children = new HashSet<>();
        parents = new HashSet<>();
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
    @Column(name = "TIMESTAMP")
    public Date getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Date createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    @ManyToOne
    public UserEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserEntity createdBy) {
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

    @ManyToMany()
    public Set<ItemEntity> getChildren() {
        return children;
    }

    public void setChildren(Set<ItemEntity> children) {
        this.children = children;
    }

    @ManyToMany(mappedBy = "children")
    public Set<ItemEntity> getParents() {
        return parents;
    }

    public void setParents(Set<ItemEntity> parents) {
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

}

