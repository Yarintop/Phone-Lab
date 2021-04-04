package app.converters;

import app.boundaries.Boundary;
import app.twins.data.Entity;

public interface EntityConverter {
	
	 Entity toEntity(Boundary boundaryObject);
	 
	 Boundary toBoundary(Entity entityObject);
	
}
