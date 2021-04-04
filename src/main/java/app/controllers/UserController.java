package app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.boundaries.NewUserDetails;
import app.boundaries.UserBoundary;
import app.twins.logic.UsersService;

@RestController
public class UserController {
	
	
	// New Part
	
	private UsersService userService;
	
	@Autowired
	public UserController(UsersService userService) {
		this.userService = userService;
	}
	
	// New Part

	@RequestMapping(
		path = "/twins/users/login/{userSpace}/{userEmail}",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public UserBoundary getUserDetails(@PathVariable("userSpace") String userSpace, @PathVariable("userEmail") String userEmail)
	{
		return new UserBoundary(
			"The Killer",
			"Rafi",
			"R",
			userEmail
		);
	}
	
	
	@RequestMapping(
	path = "/twins/users/{userSpace}/{userEmail}",
	method = RequestMethod.PUT,
	consumes = MediaType.APPLICATION_JSON_VALUE
	)
	public void updateUserDetails(@PathVariable("userSpace") String userSpace, @PathVariable("userEmail") String userEmail, @RequestBody UserBoundary user)
	{
		// STUB implementation - this methods does nothing (For now)	
	}

	@RequestMapping(
		path = "/twins/users",
		method = RequestMethod.POST,
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public UserBoundary createNewUsers(@RequestBody NewUserDetails newUserDetails)
	{
		return new UserBoundary(
			newUserDetails
		);
	}
}
