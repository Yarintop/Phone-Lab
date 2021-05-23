package twins.data;

import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.persistence.*;
import java.util.Date;

@javax.persistence.Entity
@Table(name = "OPERATIONS")
public class OperationEntity implements Entity {
    private String id = "";
    private String operationType = "undefined";
//    private ItemEntity item;
    private String item;
    private Date createdTimestamp = new Date();
//    private UserEntity invokedBy;
    private String invokedBy;
    private String operationAttributes;

    public OperationEntity() {
    }

    @Id
    @MongoId
    public String getId() {
        return id;
    }

    public void setId(String operationId) {
        this.id = operationId;
    }

    public void setOperationId(String id, String spaceId) {
        this.id = id + "&" + spaceId;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    @Lob
    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Date createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    @Lob
    public String getInvokedBy() {
        return invokedBy;
    }

    public void setInvokedBy(String invokedBy) {
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
