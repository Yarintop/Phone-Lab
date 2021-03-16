package demo;

import com.fasterxml.jackson.annotation.JsonView;
import demo.jsonViews.Views;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OperationBoundary {

    @JsonView(Views.Public.class)
    private Map<String, String> operationId;

    @JsonView(Views.Operation.class)
    private String operationType;

    @JsonView(Views.Operation.class)
    private DigitalItemBoundary item;

    @JsonView(Views.Operation.class)
    private Date createdTimestamp;

    @JsonView(Views.Operation.class)
    private UserBoundary invokedBy;

    @JsonView(Views.Operation.class)
    private Map<String, Object> operationAttributes;


    /**
     * This is the default constructor, all the fields will be get the default values
     */
    public OperationBoundary() {
        this.operationId = new HashMap<>();
        this.operationType = "undefined";
        this.item = new DigitalItemBoundary();
        this.createdTimestamp = new Date();
        this.invokedBy = new UserBoundary();
        this.operationAttributes = new HashMap<>();
    }

    /**
     * This is constructor, will create an operation with the given parameters
     *
     * @param id            - an ID for the operation, it will part of the {@link HashMap} of the Boundary Item
     * @param operationType - Operation type
     * @param item          - The item that the operation require {@link DigitalItemBoundary}
     * @param invokedBy     - The user whom invoked the operation {@link UserBoundary}
     */
    public OperationBoundary(String id, String operationType, DigitalItemBoundary item, UserBoundary invokedBy) {
        this();
        this.operationId.put("space", "2021b.twins"); //TODO: pull it from a resource, make it less hard-coded.
        this.operationId.put("id", id);
        this.operationType = operationType;
        this.item = item;
        this.invokedBy = invokedBy;
    }

    public Map<String, String> getOperationId() {
        return operationId;
    }

    public void setOperationId(Map<String, String> operationId) {
        this.operationId = operationId;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public DigitalItemBoundary getItem() {
        return item;
    }

    public void setItem(DigitalItemBoundary item) {
        this.item = item;
    }

    public Date getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Date createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public UserBoundary getInvokedBy() {
        return invokedBy;
    }

    public void setInvokedBy(UserBoundary invokedBy) {
        this.invokedBy = invokedBy;
    }

    public Map<String, Object> getOperationAttributes() {
        return operationAttributes;
    }

    public void setOperationAttributes(Map<String, Object> operationAttributes) {
        this.operationAttributes = operationAttributes;
    }
}
