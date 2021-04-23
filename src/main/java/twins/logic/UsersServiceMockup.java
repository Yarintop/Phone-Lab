package twins.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import twins.boundaries.UserBoundary;
import twins.converters.UserConverter;
import twins.data.UserEntity;
import twins.data.UserRole;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//@Service
public class UsersServiceMockup { //implements UsersService {


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
    @Value("${spring.application.name:2021b.notdef}")
    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    //@Override
    public UserBoundary createUser(UserBoundary user) throws RuntimeException {

        if (user == null)  // If user doesn't have email
            throw (new RuntimeException("User can't be null")); // User can't be null

        if (user.getUserId().getEmail() == null)  // If user doesn't have email
            throw (new RuntimeException("Can't create a user without email")); // Users must have email

        // Making sure the user has a valid role (Player, Manager or Admin)
        try {
            UserRole.valueOf(user.getRole().toUpperCase());
        }

        // If it doesn't changing it to 'Player' by default
        catch (IllegalArgumentException e) {
            user.setRole("PLAYER");
        }

//        System.out.println("Email " + user.getUserId().getEmail() + " Space: " + user.getUserId().getSpace());


        // Adding user to the system
        UserEntity userEntity = this.converter.toEntity(user);

        // Generating key
        String key = userEntity.getUserId();


//        System.out.println("Email " + userEntity.getEmail() + " Space: " + userEntity.getSpace());


        if (users.get(key) == null) // Making sure the user is a new user in the system
            this.users.put(key, userEntity);
        else
            throw (new RuntimeException("There is already a user with email: " + user.getUserId().getEmail()));

        UserBoundary temp = this.converter.toBoundary(userEntity);

//        System.out.println("Email " + temp.getUserId().getEmail() + " Space: " + temp.getUserId().getSpace());

        return temp; // Just testing the converter
    }

    //@Override
    public UserBoundary login(String userSpace, String userEmail) throws RuntimeException {

        // Getting key
        String key = userSpace + "&" + userEmail;
        UserEntity res = users.get(key);

        // Making sure the user exists
        if (res == null)
            throw (new RuntimeException("Could not find user with the given email and space"));

        return this.converter.toBoundary(res); // Returning result
    }

    //@Override
    public UserBoundary updateUser(String userSpace, String userEmail, UserBoundary update) throws RuntimeException {

        // Getting key
        String key = userSpace + "&" + userEmail;
        UserEntity user = users.get(key);
        UserEntity updateEntity = this.converter.toEntity(update);

        // In case user doesn't exist
        if (user == null)
            throw (new RuntimeException("Could not find user with the given email and space"));

        // Updating only the desired fields
        if (updateEntity.getAvatar() != null)
            user.setAvatar(updateEntity.getAvatar());

        if (updateEntity.getRole() != null)
            user.setRole(updateEntity.getRole());

        if (updateEntity.getUsername() != null)
            user.setUsername(updateEntity.getUsername());

        return this.converter.toBoundary(user); // Returning the boundary object of the user
    }

    //@Override
    public List<UserBoundary> getAllUsers(String adminSpace, String adminEmail) throws RuntimeException {

        // Getting key and user
        String key = adminSpace + "&" + adminEmail;
        UserEntity user = users.get(key);

        if (user == null)
            throw (new RuntimeException("Could not find user with the given email and space"));

        if (user.getRole() != UserRole.ADMIN)
            throw (new RuntimeException("User doesn't have permissions"));


        return users.values().stream().map(this.converter::toBoundary).collect(Collectors.toList());
    }

    //@Override
    public void deleteAllUsers(String adminSpace, String adminEmail) throws RuntimeException {

        // Getting key and user
        String key = adminSpace + "&" + adminEmail;
        UserEntity user = users.get(key);

        if (user == null)
            throw (new RuntimeException("Could not find user with the given email and space"));

        if (user.getRole() != UserRole.ADMIN)
            throw (new RuntimeException("User doesn't have permissions"));

        this.users.clear();
    }
}