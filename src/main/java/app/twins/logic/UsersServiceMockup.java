package app.twins.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.converters.UserConverter;
import app.twins.data.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.boundaries.UserBoundary;

@Service
public class UsersServiceMockup implements UsersService {


    private UserConverter converter;
    private Map<String, UserEntity> users;

    @Autowired
    public void setUserConverter(UserConverter converter) {
        this.converter = converter;
        System.err.println("Set user converter");

    }

    @Override
    public UserBoundary createUser(UserBoundary user) {
        return null;
    }

    @Override
    public UserBoundary login(String userSpace, String userEmail) {
        return null;
    }

    @Override
    public UserBoundary updateUser(String userSpace, String userEmail, UserBoundary update) {
        return null;
    }

    @Override
    public List<UserBoundary> getAllUsers(String adminSpace, String adminEmail) {
        return null;
    }

    @Override
    public void deleteAllUsers(String adminSpace, String adminEmail) {
    }
}