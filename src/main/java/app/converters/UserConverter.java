package app.converters;

import app.boundaries.UserBoundary;
import app.exceptions.BadRequestException;
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

        if (boundaryObject == null)
            return null;

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
            try {
                ue.setRole(UserRole.valueOf(boundaryObject.getRole().toUpperCase()));
            }catch (IllegalArgumentException e){
                throw (new BadRequestException("Invalid user Role!!"));
            }
        }
        return ue;
    }

    @Override
    public UserBoundary toBoundary(UserEntity entityObject) {
        UserBoundary ub = new UserBoundary();

        if (entityObject == null)
            return null;

        ub.setAvatar(entityObject.getAvatar());
        ub.setUsername(entityObject.getUsername());
        Map<String,String> userId = new HashMap<>();
        userId.put("space", entityObject.getSpace());
        userId.put("email", entityObject.getEmail());
        ub.setUserId(userId);

        if (entityObject.getRole() != null) // Making sure the role is not null
            ub.setRole(entityObject.getRole().toString());
        
        return ub;
    }
}
