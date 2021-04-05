package app.converters;

import app.boundaries.Boundary;
import app.twins.data.Entity;

public interface EntityConverter <E extends Entity, B extends Boundary> {
	
	 E toEntity(B boundaryObject);
	 
	 B toBoundary(E entityObject);
	
}
