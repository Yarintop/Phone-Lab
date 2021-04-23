package app.boundaries;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class OperationBoundary implements Boundary {

    //    private Map<String, String> operationId = new HashMap<>();
    private OperationIdBoundary operationId;
    private String type = "undefined";
    private DigitalItemBoundary item;
    private Date createdTimestamp = new Date();
    private UserBoundary invokedBy;
    private Map<String, Object> operationAttributes = new HashMap<>();


    /**
     * This is the default constructor, all the fields will be the default values
     */
    public OperationBoundary() {
//        operationId = new OperationIdBoundary();
        /* Default Constructor */
    }

    /**
     * This is constructor, will create an operation with the given parameters
     *
     * @param id     - an ID for the operation, it will part of the {@link HashMap} of the Boundary Item
     * @param type   - Operation type
     * @param itemId - The item that the operation require {@link DigitalItemBoundary}
     * @param userId - The user whom invoked the operation {@link UserBoundary}
     */
    public OperationBoundary(String id, String space, String type, DigitalItemBoundary itemId, UserBoundary userId) {
        this();
        operationId.setId(id);
        operationId.setSpace(space);
        this.type = type;
        this.item = itemId;
        this.invokedBy = userId;
    }


    public OperationIdBoundary getOperationId() {
        return operationId;
    }

    public void setOperationId(OperationIdBoundary operationId) {
        this.operationId = operationId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @JsonIgnore
    public DigitalItemBoundary getItem() {
        return item;
    }

    @JsonProperty("item")
    public void setItem(DigitalItemBoundary item) {
        this.item = item;
    }

    @JsonProperty("item")
    public HashMap<String, ItemIdBoundary> getItemId() {
        if (item == null) return null;
        HashMap<String, ItemIdBoundary> temp = new HashMap<>();
        temp.put("itemId", item.getItemId());
        return temp;
//        return item == null ? null : item.getItemId();
    }


    public Date getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Date createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    @JsonIgnore
    public UserBoundary getInvokedBy() {
        return invokedBy;
    }

    @JsonProperty("invokedBy")
    public void setInvokedBy(UserBoundary invokedBy) {
        this.invokedBy = invokedBy;
    }

    @JsonProperty("invokedBy")
    public HashMap<String, UserIdBoundary> getInvokedById() {
        if (invokedBy == null) return null;
        HashMap<String, UserIdBoundary> temp = new HashMap<>();
        temp.put("userId", invokedBy.getUserId());
        return temp;
    }

    public Map<String, Object> getOperationAttributes() {
        return operationAttributes;
    }

    public void setOperationAttributes(Map<String, Object> operationAttributes) {
        this.operationAttributes = operationAttributes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperationBoundary that = (OperationBoundary) o;
        return Objects.equals(operationId, that.operationId) &&
                Objects.equals(type, that.type) &&
                Objects.equals(item, that.item) && Objects.equals(createdTimestamp, that.createdTimestamp) &&
                Objects.equals(invokedBy, that.invokedBy) &&
                Objects.equals(operationAttributes, that.operationAttributes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operationId, type, item, createdTimestamp, invokedBy, operationAttributes);
    }


//        /** OLD CONSTRUCTOR
//     * This is constructor, will create an operation with the given parameters
//     *
//     * @param id            - an ID for the operation, it will part of the {@link HashMap} of the Boundary Item
//     * @param operationType - Operation type
//     * @param item          - The item that the operation require {@link DigitalItemBoundary}
//     * @param invokedBy     - The user whom invoked the operation {@link UserBoundary}
//     */
//    public OperationBoundary(Map<String, String> id, String operationType, DigitalItemBoundary item, UserBoundary invokedBy) {
//        this();
//        operationId.setId(id.);
//        this.operationType = operationType;
//        this.item = item;
//        this.invokedBy = invokedBy;
//    }
}
