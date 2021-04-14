package app.twins.logic;

import app.boundaries.UserBoundary;
import app.converters.UserConverter;
import app.dao.UserDao;
import app.exceptions.BadRequestException;
import app.exceptions.NoPermissionException;
import app.exceptions.NotFoundException;
import app.twins.data.UserEntity;
import app.twins.data.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UsersServiceJpa implements UsersService {

    private String spaceId;
    private UserConverter converter;
    private UserDao usersDao; // Users data

    @Autowired
    public void setUsersDao(UserDao usersDao) {
        this.usersDao = usersDao;
    }

    @Autowired
    public void setUserConverter(UserConverter converter) {
        this.converter = converter;
    }

    // Sets the space ID
    @Value("${spring.application.name:${spring.application.name:2021b.notdef}}")
    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }


    @Override
    @Transactional
    public UserBoundary createUser(UserBoundary user) throws RuntimeException {

        if (user == null)  // If user doesn't have email
            throw (new BadRequestException("User can't be null")); // User can't be null

        if (user.getUserId().get("email") == null)  // If user doesn't have email
            throw (new BadRequestException("Can't create a user without an email")); // Users must have email

        // Making sure the user has a valid role (Player, Manager or Admin)
        try {
            UserRole.valueOf(user.getRole().toUpperCase());
        }

        // If it doesn't changing it to 'Player' by default
        catch (IllegalArgumentException e) {
            user.setRole("PLAYER");
        }

        // Adding user to the system
        UserEntity userEntity = this.converter.toEntity(user);
        userEntity.setSpace(spaceId); // Setting the user's space

        // Generating key
        String key = userEntity.getSpace() + "&" + userEntity.getEmail();

        if (!this.usersDao.findById(key).isPresent()) // Making sure the user is a new user in the system
            this.usersDao.save(userEntity); // Saving user to the database
        else
            throw (new RuntimeException("There is already a user with email: " + user.getUserId().get("email")));


        return this.converter.toBoundary(userEntity); // Just testing the converter
    }

    @Override
    @Transactional(readOnly = true)
    public UserBoundary login(String userSpace, String userEmail) throws RuntimeException {

        // Getting key
        String key = userSpace + "&" + userEmail;
        Optional<UserEntity> res = usersDao.findById(key);

        // Making sure the user exists
        if (!res.isPresent())
            throw (new NotFoundException("Could not find user with the given email and space"));

        return this.converter.toBoundary(res.get()); // Returning result
    }

    @Override
    @Transactional
    public UserBoundary updateUser(String userSpace, String userEmail, UserBoundary update) throws RuntimeException {

        // Getting key
        String key = userSpace + "&" + userEmail;
        Optional<UserEntity> optionalUser = this.usersDao.findById(key);
        UserEntity updateEntity = this.converter.toEntity(update);

        // In case user doesn't exist
        if (!optionalUser.isPresent()) {
            throw (new NotFoundException("Could not find user with the given email and space"));
        }

        UserEntity user = optionalUser.get(); // Getting the user itself from the response object

        // Updating only the desired fields
        if (updateEntity.getAvatar() != null)
            user.setAvatar(updateEntity.getAvatar());

        if (updateEntity.getRole() != null)
            user.setRole(updateEntity.getRole());

        if (updateEntity.getUsername() != null)
            user.setUsername(updateEntity.getUsername());

        this.usersDao.save(user); // Updating the user in the database

        return this.converter.toBoundary(user); // Returning the boundary object of the user
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserBoundary> getAllUsers(String adminSpace, String adminEmail) throws RuntimeException {

        // Getting key and user to check if they are an admin
        String key = adminSpace + "&" + adminEmail;
        Optional<UserEntity> optionalUser = this.usersDao.findById(key);

        // Checking if the user exists in the database
        if (!optionalUser.isPresent())
            throw (new NotFoundException("Could not find user with the given email and space"));

        // Getting the user itself after finding out they exist
        UserEntity user = optionalUser.get();

        // If the user is not an admin
        if (user.getRole() != UserRole.ADMIN)
            throw (new NoPermissionException("User doesn't have permissions"));

        Iterable<UserEntity> allEntities = this.usersDao
                .findAll();

        return StreamSupport
                .stream(allEntities.spliterator(), false) // get stream from iterable
                .map(this.converter::toBoundary)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteAllUsers(String adminSpace, String adminEmail) throws RuntimeException {

        // Getting key and user to check if they are an admin
        String key = adminSpace + "&" + adminEmail;
        Optional<UserEntity> optionalUser = this.usersDao.findById(key);

        // Checking if the user exists in the database
        if (!optionalUser.isPresent())
            throw (new NotFoundException("Could not find user with the given email and space"));

        // Getting the user itself after finding out they exist
        UserEntity user = optionalUser.get();

        // If the user is not an admin
        if (user.getRole() != UserRole.ADMIN)
            throw (new NoPermissionException("User doesn't have permissions"));

        // Deleting all the users from the users table
        this.usersDao.deleteAll();

    }
}