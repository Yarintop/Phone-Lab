package app.twins.logic;

import java.util.*;
import java.util.stream.Collectors;

import app.converters.UserConverter;
import app.twins.data.UserEntity;
import app.twins.data.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import app.boundaries.UserBoundary;

@Service
public class UsersServiceMockup implements UsersService {


    private String spaceId;
    private UserConverter converter;
    private Map<String, UserEntity> users; // Users data


    public UsersServiceMockup() {
        this.users = Collections.synchronizedMap(new HashMap<>());
    }

    @Autowired
    public void setUserConverter(UserConverter converter) {
        this.converter = converter;
    }

    // Sets the space ID
    @Value("${spring.application.name:2021b.twins}")
    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    @Override
    public UserBoundary createUser(UserBoundary user) {
        UserEntity userEntity = this.converter.toEntity(user);

        userEntity.setSpace(spaceId); // Setting the user's space

        // Generating key
        String key = userEntity.getSpace() + "&" + userEntity.getEmail();
        this.users.put(key, userEntity);


        return this.converter.toBoundary(userEntity); // Just testing the converter
    }

    @Override
    public UserBoundary login(String userSpace, String userEmail) {

        // Getting key
        String key = userSpace + "&" + userEmail;
        UserEntity res = users.get(key);

        // Making sure the user exists
        if (res == null)
            return null;

        return this.converter.toBoundary(res); // Returning result
    }

    @Override
    public UserBoundary updateUser(String userSpace, String userEmail, UserBoundary update) {

        // Getting key
        String key = userSpace + "&" + userEmail;
        UserEntity user = users.get(key);
        UserEntity updateEntity = this.converter.toEntity(update);
        
        // In case user doesn't exist
        if (user == null)
            return null;

        // Updating only the desired fields
        if (updateEntity.getAvatar() != null)
            user.setAvatar(updateEntity.getAvatar());

        if (updateEntity.getRole() != null)
            user.setRole(updateEntity.getRole());

        if (updateEntity.getUsername() != null)
            user.setUsername(updateEntity.getUsername());

        return this.converter.toBoundary(user); // Returning the boundary object of the user
    }

    @Override
    public List<UserBoundary> getAllUsers(String adminSpace, String adminEmail) {

        // Getting key and user
        String key = adminSpace + "&" + adminEmail;
        UserEntity user = users.get(key);
        
        if ((user != null) && (user.getRole() == UserRole.ADMIN))
            return users.values().stream().map(this.converter::toBoundary).collect(Collectors.toList());

        return new ArrayList<>(); // If the user is not an admin he will get an empty list
    }

    @Override
    public void deleteAllUsers(String adminSpace, String adminEmail) {

        // Getting key and user
        String key = adminSpace + "&" + adminEmail;
        UserEntity user = users.get(key);

        if ((user != null) && (user.getRole() == UserRole.ADMIN))
            this.users.clear();
    }
}