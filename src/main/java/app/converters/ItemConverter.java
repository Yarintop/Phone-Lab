package app.converters;

import app.boundaries.DigitalItemBoundary;
import app.twins.data.ItemEntity;
import org.springframework.stereotype.Component;

@Component
public class ItemConverter implements EntityConverter<ItemEntity, DigitalItemBoundary> {
    @Override
    public ItemEntity toEntity(DigitalItemBoundary boundaryObject) {
        return null;
    }

    @Override
    public DigitalItemBoundary toBoundary(ItemEntity entityObject) {
        return null;
    }
}
