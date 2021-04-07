package app.converters;

import app.boundaries.UserBoundary;
import app.twins.data.UserEntity;
import app.twins.data.UserRole;
import org.springframework.stereotype.Component;

import java.util.HashMap;
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
        if (boundaryObject.getUserId() != null) {
            ue.setSpace(boundaryObject.getUserId().get("space"));
            ue.setEmail(boundaryObject.getUserId().get("email"));
        }
        if (boundaryObject.getRole() != null) {
            ue.setRole(UserRole.valueOf(boundaryObject.getRole()));
        }
        return ue;
    }

    @Override
    public UserBoundary toBoundary(UserEntity entityObject) {
        UserBoundary ub =new UserBoundary();
        ub.setAvatar(entityObject.getAvatar());
        ub.setUsername(entityObject.getUsername());
        Map<String,String> userId = new HashMap<>();
        userId.put("space", entityObject.getSpace());
        userId.put("email", entityObject.getEmail());
        ub.setRole(entityObject.getRole().toString());
        ub.setUserId(userId);
        return ub;
    }
}
