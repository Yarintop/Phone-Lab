package demo;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	@RequestMapping(
		path = "/twins/users/login/{userSpace}/{userEmail}",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public UserBoundary getUserDetails(@PathVariable("userSpace") String userSpace, @PathVariable("userEmail") String userEmail)
	{
		return new UserBoundary("The Killer", "Rafi", "R", userEmail); 
		
		
	}
	
	
	/* Rafi will implement the POST method for this controller */
	
	
	@RequestMapping(
	path = "/twins/users/login/{userSpace}/{userEmail}",
	method = RequestMethod.PUT,
	consumes = MediaType.APPLICATION_JSON_VALUE
	)
	public void updateUserDetails(@PathVariable("userSpace") String userSpace, @PathVariable("userEmail") String userEmail, @RequestBody UserBoundary user)
	{
		// STUB implementation - this methods does nothing (For now)	
	}

	@RequestMapping(
			path = "/twins/users",//yese
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE
	)
	public void createNewUsers(@PathVariable("userSpace") String userSpace, @PathVariable("userEmail") String userEmail, @RequestBody UserBoundary user)
	{
		// STUB implementation - this methods does nothing (For now)
	}
}
