package app.converters;

import app.boundaries.OperationBoundary;
import app.twins.data.OperationEntity;
import org.springframework.stereotype.Component;

@Component
public class OperationConverter implements EntityConverter<OperationEntity, OperationBoundary> {

    @Override
    public OperationEntity toEntity(OperationBoundary boundary) {
        OperationEntity entity = new OperationEntity();
        entity.setOperationId(boundary.getOperationId());
        entity.setOperationType(boundary.getOperationType());
        entity.setCreatedTimestamp(boundary.getCreatedTimestamp());
        entity.setInvokedBy(boundary.getInvokedBy());
        entity.setItem(boundary.getItem());
        entity.setOperationAttributes(boundary.getOperationAttributes());
        return entity;
    }

    @Override
    public OperationBoundary toBoundary(OperationEntity entity) {
        OperationBoundary boundary = new OperationBoundary();
        boundary.setOperationId(entity.getOperationId());
        boundary.setOperationType(entity.getOperationType());
        boundary.setCreatedTimestamp(entity.getCreatedTimestamp());
        boundary.setInvokedBy(entity.getInvokedBy());
        boundary.setItem(entity.getItem());
        boundary.setOperationAttributes(entity.getOperationAttributes());
        return boundary;
    }
}
