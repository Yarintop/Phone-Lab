package twins.converters;

import twins.boundaries.Boundary;
import twins.data.Entity;

public interface EntityConverter<E extends Entity, B extends Boundary> {

    public E toEntity(B boundaryObject);

    public B toBoundary(E entityObject);

}
