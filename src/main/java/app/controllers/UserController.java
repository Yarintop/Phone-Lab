package app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import app.jsonViews.Views;

import app.boundaries.NewUserDetails;
import app.boundaries.UserBoundary;
import app.twins.logic.UsersService;

@RestController
public class UserController {

    // New Part
    private UsersService userService;
    private String spaceId;

    @Autowired
    public UserController(UsersService userService) {
        this.userService = userService;
    }
    // New Part

    /**
     * Sets the spaceId value from the application.properties file
     *
     * @param spaceId the loaded spaceId value, default would be "2021b.twins"
     */
    @Value("${spring.application.name:2021b.notdef}")
    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

	/**
	 * Adds a new user to the system
	 * @param newUserDetails - The details of the user to be created
	 * @return A boundary object of the newly created user
	 */
    @JsonView(Views.User.class)
    @RequestMapping(path = "/twins/users", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserBoundary createNewUsers(@RequestBody NewUserDetails newUserDetails) {

        // Creating a new UserBoundary object from the details
        UserBoundary userBoundary = new UserBoundary(newUserDetails, this.spaceId);
        
        // Saving the user to the system
        userBoundary = this.userService.createUser(userBoundary);
        
        // Returning the newly created user
        return userBoundary;
    }


    /**
     *
     * @param userSpace - Used as part of the key to retrieve the user
     * @param userEmail- Used as part of the key to retrieve the user
     * @return A boundary object of the desired user
     */
    @JsonView(Views.User.class)
    @RequestMapping(path = "/twins/users/login/{userSpace}/{userEmail}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserBoundary getUserDetails(@PathVariable("userSpace") String userSpace,
                                       @PathVariable("userEmail") String userEmail) {

        return this.userService.login(userSpace, userEmail);
    }

    /**
     *
     * @param userSpace - Used as part of the key to retrieve the user
     * @param userEmail- Used as part of the key to retrieve the user
     * @param user - Details to update
     */
    @JsonView(Views.User.class)
    @RequestMapping(path = "/twins/users/{userSpace}/{userEmail}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateUserDetails(@PathVariable("userSpace") String userSpace,
                                  @PathVariable("userEmail") String userEmail, @RequestBody UserBoundary user) {

       this.userService.updateUser(userSpace, userEmail, user);
    }
}
