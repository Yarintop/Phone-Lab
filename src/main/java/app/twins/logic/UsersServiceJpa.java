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

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
        //TODO: If not used, remove
        this.spaceId = spaceId;
    }


    @Override
    @Transactional
    public UserBoundary createUser(UserBoundary user) throws RuntimeException {

        Pattern valid_email_regex = Pattern.compile("[A-Z0-9_.]+@([A-Z0-9]+\\.)+[A-Z0-9]{2,6}$", Pattern.CASE_INSENSITIVE);

        if (user == null)  // If user doesn't have email
            throw (new BadRequestException("User can't be null")); // User can't be null

        if (user.getUserId().getEmail() == null)  // If user doesn't have email
            throw (new BadRequestException("Can't create a user without an email")); // Users must have email

        Matcher matcher = valid_email_regex.matcher(user.getUserId().getEmail());

        if (!matcher.find()) {
            throw (new BadRequestException("Email address is invalid " + user.getUserId().getEmail()));
        }

        // Making sure the user has a valid role (Player, Manager or Admin)
        try {
            UserRole.valueOf(user.getRole().toUpperCase());
        }

        // If it doesn't changing it to 'Player' by default
        catch (IllegalArgumentException e) {
            user.setRole("PLAYER");
        }

        if (user.getAvatar() == null || user.getAvatar().isEmpty()) {
            throw (new BadRequestException("Invalid user avatar!!"));
        }

        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            throw (new BadRequestException("Invalid username!!"));
        }

        // Adding user to the system
        UserEntity userEntity = this.converter.toEntity(user);

//        userEntity.setSpace(spaceId); // Setting the user's space

        // Generating key
//        String key = userEntity.getSpace() + "&" + userEntity.getEmail();
        String key = userEntity.getUserId();


        if (!this.usersDao.findById(key).isPresent()) // Making sure the user is a new user in the system
            this.usersDao.save(userEntity); // Saving user to the database
        else
            throw (new BadRequestException("There is already a user with email: " + user.getUserId().getEmail()));


        return this.converter.toBoundary(userEntity); // Just testing the converter
    }

    @Override
    @Transactional(readOnly = true)
    public UserBoundary login(String userSpace, String userEmail) throws RuntimeException {

        return this.converter.toBoundary(findUser(userSpace, userEmail)); // Returning result
    }

    @Override
    @Transactional
    public UserBoundary updateUser(String userSpace, String userEmail, UserBoundary update) throws RuntimeException {

        UserEntity user = findUser(userSpace, userEmail);

        UserEntity updateEntity = this.converter.toEntity(update);

        // Updating only the desired fields
        if (updateEntity.getAvatar() != null) {
            if (updateEntity.getAvatar().isEmpty())
                throw (new BadRequestException("Invalid user avatar!!"));

            user.setAvatar(updateEntity.getAvatar());
        }


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

        // If the user is not an admin
        if (findUser(adminSpace, adminEmail).getRole() != UserRole.ADMIN)
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
        UserEntity adminUser = findUser(adminSpace, adminEmail);

        // If the user is not an admin
        if (adminUser.getRole() != UserRole.ADMIN)
            throw (new NoPermissionException("User doesn't have permissions"));

        // Deleting all the users from the users table
        this.usersDao.deleteAll();

    }

    @Transactional(readOnly = true)
    public UserEntity findUser(String space, String email) throws RuntimeException {

        // Getting key and user to check if they are an admin
        Optional<UserEntity> optionalUser = this.usersDao.findById(email + "&" + space);

        // Checking if the user exists in the database
        if (!optionalUser.isPresent())
            throw (new NotFoundException("Could not find user with the given email and space"));

        // Return found user's entity
        return optionalUser.get();

    }
}