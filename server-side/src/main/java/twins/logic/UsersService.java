package twins.logic;

import java.util.List;

import twins.boundaries.UserBoundary;

public interface UsersService {
    public UserBoundary createUser(UserBoundary user);

    public UserBoundary login(String userSpace, String userEmail);

    public UserBoundary updateUser(String userSpace, String userEmail, UserBoundary update);

    public List<UserBoundary> getAllUsers(String adminSpace, String adminEmail, int size, int page);
    
    public void deleteAllUsers(String adminSpace, String adminEmail);

    /* ####################################### */
    /* ####################################### */

    @Deprecated
    public List<UserBoundary> getAllUsers(String adminSpace, String adminEmail);
}
