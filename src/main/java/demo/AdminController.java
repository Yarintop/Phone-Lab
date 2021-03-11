package demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController
{
	@RequestMapping(
		path = "/twins/admin/users/{userSpace}/{userEmail}",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public List<UserBoundary> exportAllUsers(@PathVariable("userSpace") long id, @PathVariable("userEmail") String userEmail)
	{
		List<UserBoundary> allUsers = new ArrayList<>();
		
		allUsers.add(
			new UserBoundary("Dima is", "The Bestest Guy", "dima@bestguy.com", 0)
		);
		allUsers.add(
			new UserBoundary("Yarin", "Top Londong Mizrahi Tfahot", "yarin@whatismyname.com", 0)
		);

		return allUsers;
	}

	@RequestMapping(
		path = "/twins/admin/operations/{userSpace}/{userEmail}",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public List<OperationBoundary> exportAllOperations(@PathVariable("userSpace") long id, @PathVariable("userEmail") String userEmail)
	{
		List<OperationBoundary> allOperations = new ArrayList<OperationBoundary>();
		
		allOperations.add(
			new OperationBoundary()
		);

		return allOperations;
	}

	@RequestMapping(
		path = "/twins/admin/users/{userSpace}/{userEmail}",
		method = RequestMethod.DELETE
	)
	public void deleteAllUsers(@PathVariable("userSpace") long id, @PathVariable("userEmail") String userEmail)
	{
		//TODO
	}

	@RequestMapping(
		path = "/twins/admin/items/{userSpace}/{userEmail}",
		method = RequestMethod.DELETE
	)
	public void deleteAllItems(@PathVariable("userSpace") long id, @PathVariable("userEmail") String userEmail)
	{
		//TODO
	}

	@RequestMapping(
		path = "/twins/admin/operations/{userSpace}/{userEmail}",
		method = RequestMethod.DELETE
	)
	public void deleteAllOperations(@PathVariable("userSpace") long id, @PathVariable("userEmail") String userEmail)
	{
		//TODO
	}
}
