package app.twins.logic;

import java.util.List;

import org.springframework.stereotype.Service;

import app.boundaries.UserBoundary;

@Service
public class UsersServiceMockup implements UsersService {
    
    @Override
    public UserBoundary createUser(UserBoundary user)
    {
        return null;
    }

    @Override
    public UserBoundary login(String userSpace, String userEmail)
    {
        return null;
    }

    @Override
    public UserBoundary updateUser(String userSpace, String userEmail, UserBoundary update)
    {
        return null;
    }

    @Override
    public List<UserBoundary> getAllUsers(String adminSpace, String adminEmail)
    {
        return null;
    }

    @Override
    public void deleteAllUsers(String adminSpace, String adminEmail)
    {
    }
}