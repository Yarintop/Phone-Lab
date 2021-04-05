package app.converters;

import app.boundaries.UserBoundary;
import app.twins.data.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserConverter implements EntityConverter<UserEntity, UserBoundary> {
    @Override
    public UserEntity toEntity(UserBoundary boundaryObject) {
        return null;
    }

    @Override
    public UserBoundary toBoundary(UserEntity entityObject) {
        return null;
    }
}
