package app.twins.data;

import app.boundaries.DigitalItemBoundary;
import app.boundaries.UserBoundary;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OperationEntity implements Entity {
    private String operationId = "";
    private String operationType = "undefined";
    private DigitalItemBoundary item = new DigitalItemBoundary();
    private Date createdTimestamp = new Date();
    private UserBoundary invokedBy = new UserBoundary();
    private Map<String, Object> operationAttributes = new HashMap<>();

    public OperationEntity() {
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public void setOperationId(String id, String spaceId) {
        this.operationId = id + "&" + spaceId;
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
