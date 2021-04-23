package twins.converters;

import org.springframework.stereotype.Component;
import twins.boundaries.UserBoundary;
import twins.data.UserEntity;
import twins.data.UserRole;
import twins.exceptions.BadRequestException;

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
            ue.setUserId(boundaryObject.getUserId().toString());
            ue.setEmail(boundaryObject.getUserId().getEmail());
        }
        if (boundaryObject.getRole() != null) {
            try {
                ue.setRole(UserRole.valueOf(boundaryObject.getRole().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw (new BadRequestException("Invalid user Role!!"));
            }
        }
        return ue;
    }

    @Override
    public UserBoundary toBoundary(UserEntity entityObject) {

        if (entityObject == null)
            return null;

        String[] idParts = entityObject.getUserId().split("&");
        if (idParts.length != 2)
            throw new BadRequestException("User with ID: " + entityObject.getUserId() + " not formatted correctly");
        String email = idParts[0];
        String space = idParts[1];
        UserBoundary ub = new UserBoundary(email, space);

        ub.setAvatar(entityObject.getAvatar());
        ub.setUsername(entityObject.getUsername());


        if (entityObject.getRole() != null) // Making sure the role is not null
            ub.setRole(entityObject.getRole().toString());

        return ub;
    }
}
