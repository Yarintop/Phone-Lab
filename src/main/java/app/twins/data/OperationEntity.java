package app.twins.data;

import app.boundaries.DigitalItemBoundary;
import app.boundaries.UserBoundary;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OperationEntity implements Entity{
    private Map<String, String> operationId = new HashMap<>();
    private String operationType = "undefined";
    private DigitalItemBoundary item = new DigitalItemBoundary();
    private Date createdTimestamp = new Date();
    private UserBoundary invokedBy = new UserBoundary();
    private Map<String, Object> operationAttributes = new HashMap<>();

    public OperationEntity() {

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
