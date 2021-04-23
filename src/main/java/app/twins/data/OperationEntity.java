package app.twins.data;

import javax.persistence.*;
import java.util.Date;

@javax.persistence.Entity
@Table(name = "OPERATIONS")
public class OperationEntity implements Entity {
    private String operationId = "";
    private String operationType = "undefined";
    private ItemEntity item;
    private Date createdTimestamp = new Date();
    private UserEntity invokedBy;
    private String operationAttributes;

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

    @ManyToOne(fetch = FetchType.LAZY)
    public ItemEntity getItem() {
        return item;
    }

    public void setItem(ItemEntity item) {
        this.item = item;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Date createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    public UserEntity getInvokedBy() {
        return invokedBy;
    }

    public void setInvokedBy(UserEntity invokedBy) {
        this.invokedBy = invokedBy;
    }

    @Lob
    public String getOperationAttributes() {
        return operationAttributes;
    }

    public void setOperationAttributes(String operationAttributes) {
        this.operationAttributes = operationAttributes;
    }


}
