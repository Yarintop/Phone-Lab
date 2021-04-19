package app.boundaries;

import com.fasterxml.jackson.annotation.JsonView;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import app.jsonViews.Views;

public class OperationBoundary implements Boundary {

    @JsonView(Views.Public.class)
    private Map<String, String> operationId = new HashMap<>();

    @JsonView(Views.Operation.class)
    private String operationType = "undefined";

    @JsonView(Views.Operation.class)
    private DigitalItemBoundary item = new DigitalItemBoundary();

    @JsonView(Views.Operation.class)
    private Date createdTimestamp = new Date();

    @JsonView(Views.Operation.class)
    private UserBoundary invokedBy = new UserBoundary();

    private Map<String, Object> operationAttributes = new HashMap<>();


    /**
     * This is the default constructor, all the fields will be the default values
     */
    public OperationBoundary() { /* Default Constructor */ }


    public OperationIdBoundary getOperationId() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperationBoundary that = (OperationBoundary) o;
        return Objects.equals(operationId, that.operationId) &&
                Objects.equals(operationType, that.operationType) &&
                Objects.equals(item, that.item) && Objects.equals(createdTimestamp, that.createdTimestamp) &&
                Objects.equals(invokedBy, that.invokedBy) &&
                Objects.equals(operationAttributes, that.operationAttributes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operationId, operationType, item, createdTimestamp, invokedBy, operationAttributes);
    }
}
