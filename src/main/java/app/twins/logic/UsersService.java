package app.twins.logic;

import java.util.List;

import app.boundaries.UserBoundary;

public interface UsersService
{
    UserBoundary createUser(UserBoundary user);
    UserBoundary login(String userSpace, String userEmail);
    UserBoundary updateUser(String userSpace, String userEmail, UserBoundary update);
    List<UserBoundary> getAllUsers(String adminSpace, String adminEmail);
    void deleteAllUsers(String adminSpace, String adminEmail);
}
