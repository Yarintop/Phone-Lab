package twins.logic;

import twins.data.ErrorType;
import twins.data.UserRole;

/**
 * This interface is to add utils function to a service OR to make just a utils service.
 * <p>
 * we could also extend the UserService here but this make more sense...
 */
public interface UserUtilsService {
    public ErrorType checkRoleUser(String space, String email, UserRole role);

}
