package app.converters;

import app.boundaries.UserBoundary;
import app.twins.data.UserEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UserConverter implements EntityConverter<UserEntity, UserBoundary> {
    @Override
    public UserEntity toEntity(UserBoundary boundaryObject) {
        UserEntity ue = new UserEntity();
        if (boundaryObject.getAvatar() != null) {
            ue.setAvatar(boundaryObject.getAvatar());
        }
        if (boundaryObject.getUsername() != null) {
            ue.setUsername(boundaryObject.getUsername());
        }
        return ue;
    }

    @Override
    public UserBoundary toBoundary(UserEntity entityObject) {
        UserBoundary ub =new UserBoundary();
        ub.setAvatar(entityObject.getAvatar());
        ub.setUsername(entityObject.getUsername());
        ub.setUserId((new UserBoundary(entityObject.getEmail(),entityObject.getSpace())).getUserId());
        return ub;
    }
}
