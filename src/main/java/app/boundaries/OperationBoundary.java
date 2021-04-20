package app.boundaries;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class OperationBoundary implements Boundary {

    //    private Map<String, String> operationId = new HashMap<>();
    private OperationIdBoundary operationId;
    private String type = "undefined";
    private ItemIdBoundary item;
    private Date createdTimestamp = new Date();
    private UserIdBoundary invokedBy;
    private Map<String, Object> operationAttributes = new HashMap<>();


    /**
     * This is the default constructor, all the fields will be the default values
     */
    public OperationBoundary() {
        operationId = new OperationIdBoundary();
        /* Default Constructor */
    }

    /**
     * This is constructor, will create an operation with the given parameters
     *
     * @param id        - an ID for the operation, it will part of the {@link HashMap} of the Boundary Item
     * @param type      - Operation type
     * @param itemId      - The item that the operation require {@link DigitalItemBoundary}
     * @param userId - The user whom invoked the operation {@link UserBoundary}
     */
    public OperationBoundary(String id, String space, String type, ItemIdBoundary itemId, UserIdBoundary userId) {
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

    public String getOperationType() {
        return type;
    }

    public void setOperationType(String type) {
        this.type = type;
    }

    public ItemIdBoundary getItem() {
        return item;
    }

    public void setItem(ItemIdBoundary item) {
        this.item = item;
    }

    public Date getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Date createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public UserIdBoundary getInvokedBy() {
        return invokedBy;
    }

    public void setInvokedBy(UserIdBoundary invokedBy) {
        this.invokedBy = invokedBy;
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
