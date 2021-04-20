package app.twins.data;

import app.boundaries.ItemIdBoundary;
import app.boundaries.UserIdBoundary;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@javax.persistence.Entity
@Table(name = "OPERATIONS")
public class OperationEntity implements Entity {
    private String operationId = "";
    private String operationType = "undefined";
    private ItemIdBoundary item;
    private Date createdTimestamp = new Date();
    private UserIdBoundary invokedBy ;
    private Map<String, Object> operationAttributes = new HashMap<>();

    public OperationEntity() {
    }

    @Id
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

    @Transient
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

    @Transient
    public UserIdBoundary getInvokedBy() {
        return invokedBy;
    }

    public void setInvokedBy(UserIdBoundary invokedBy) {
        this.invokedBy = invokedBy;
    }

    @Transient
    public Map<String, Object> getOperationAttributes() {
        return operationAttributes;
    }

    public void setOperationAttributes(Map<String, Object> operationAttributes) {
        this.operationAttributes = operationAttributes;
    }


}
