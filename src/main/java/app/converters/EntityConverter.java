package app.converters;

import app.boundaries.Boundary;
import app.twins.data.Entity;

public interface EntityConverter<E extends Entity, B extends Boundary> {

    public E toEntity(B boundaryObject);

    public B toBoundary(E entityObject);

}
