package app.controllers;

import java.util.ArrayList;
import java.util.List;

import app.dummyData.DummyData;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.boundaries.OperationBoundary;
import app.boundaries.UserBoundary;
import app.jsonViews.Views;

@RestController
public class AdminController
{
	/**
	 * Returns all users in the requested space
	 * @param id		- Requested user space
	 * @param userEmail - Invoker's email
	 * @return List of {@link UserBoundary}
	 */
	@RequestMapping(
		path = "/twins/admin/users/{userSpace}/{userEmail}",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
		)
	@JsonView(Views.User.class)
	public List<UserBoundary> exportAllUsers(@PathVariable("userSpace") String id, @PathVariable("userEmail") String userEmail)
	{
		List<UserBoundary> allUsers = new ArrayList<>();
		
		allUsers.add(
			new UserBoundary(
				"Bestest Guy",
				"Dima",
				"D",
				"dima@bestguy.com"
			)
		);
		allUsers.add(
			new UserBoundary(
				"Bank",
				"Yarin Top Londong Mizrahi Tfahot",
				"Y",
				"yarin@whatismyname.com"
			)
		);

		return allUsers;
	}

	/**
	 * Returns all operations in the requested space
	 * @param id		- Requested user space
	 * @param userEmail - Invoker's email
	 * @return List of {@link OperationBoundary}
	 */
	@RequestMapping(
		path = "/twins/admin/operations/{userSpace}/{userEmail}",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	@JsonView(Views.Operation.class)
	public List<OperationBoundary> exportAllOperations(@PathVariable("userSpace") String id, @PathVariable("userEmail") String userEmail)
	{
		List<OperationBoundary> allOperations = new ArrayList<>();
		
		allOperations.add(DummyData.getRandomOperation());
		allOperations.add(DummyData.getRandomOperation());

		return allOperations;
	}

	/**
	 * Deletes all users in the requested space
	 * @param id		- Requested user space
	 * @param userEmail - Invoker's email
	 */
	@RequestMapping(
		path = "/twins/admin/users/{userSpace}/{userEmail}",
		method = RequestMethod.DELETE
	)
	public void deleteAllUsers(@PathVariable("userSpace") String id, @PathVariable("userEmail") String userEmail)
	{
		//TODO
	}

	/**
	 * Deletes all items in the requested space
	 * @param id		- Requested user space
	 * @param userEmail - Invoker's email
	 */
	@RequestMapping(
		path = "/twins/admin/items/{userSpace}/{userEmail}",
		method = RequestMethod.DELETE
	)
	public void deleteAllItems(@PathVariable("userSpace") String id, @PathVariable("userEmail") String userEmail)
	{
		//TODO
	}

	/**
	 * Deletes all operations in the requested space
	 * @param id		- Requested user space
	 * @param userEmail - Invoker's email
	 */
	@RequestMapping(
		path = "/twins/admin/operations/{userSpace}/{userEmail}",
		method = RequestMethod.DELETE
	)
	public void deleteAllOperations(@PathVariable("userSpace") String id, @PathVariable("userEmail") String userEmail)
	{
		//TODO
	}	
}
