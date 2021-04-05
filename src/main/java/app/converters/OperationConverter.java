package app.converters;

import app.boundaries.OperationBoundary;
import app.twins.data.OperationEntity;
import org.springframework.stereotype.Component;

@Component
public class OperationConverter  implements  EntityConverter<OperationEntity, OperationBoundary> {
    @Override
    public OperationEntity toEntity(OperationBoundary boundaryObject) {
        return null;
    }

    @Override
    public OperationBoundary toBoundary(OperationEntity entityObject) {
        return null;
    }
}
