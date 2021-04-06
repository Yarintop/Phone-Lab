package app.controllers;

import java.util.stream.Collectors;
import java.util.stream.Stream;

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
	 * @return Array of {@link UserBoundary}
	 */
	@RequestMapping(
		path = "/twins/admin/users/{userSpace}/{userEmail}",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
		)
	@JsonView(Views.User.class)
	public UserBoundary[] exportAllUsers(@PathVariable("userSpace") String id, @PathVariable("userEmail") String userEmail)
	{
		return Stream
			.of(
				DummyData.getRandomUser(),
				DummyData.getRandomUser(),
				DummyData.getRandomUser()
			)
			.collect(Collectors.toList())
			.toArray(new UserBoundary[0]);
	}

	/**
	 * Returns all operations in the requested space
	 * @param id		- Requested user space
	 * @param userEmail - Invoker's email
	 * @return Array of {@link OperationBoundary}
	 */
	@RequestMapping(
		path = "/twins/admin/operations/{userSpace}/{userEmail}",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	@JsonView(Views.Operation.class)
	public OperationBoundary[] exportAllOperations(@PathVariable("userSpace") String id, @PathVariable("userEmail") String userEmail)
	{
		return Stream
			.of(
				DummyData.getRandomOperation(),
				DummyData.getRandomOperation(),
				DummyData.getRandomOperation()
			)
			.collect(Collectors.toList())
			.toArray(new OperationBoundary[0]);
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
		// STUB implementation - this methods does nothing (For now)
	}

	/**
	 * Deletes all items in the requested space
	 * @param id		- Requested user space
	 * @param userEmail - Invoker's email
	 */
//	@RequestMapping(
//		path = "/twins/admin/items/{userSpace}/{userEmail}",
//		method = RequestMethod.DELETE
//	)
//	public void deleteAllItems(@PathVariable("userSpace") String id, @PathVariable("userEmail") String userEmail)
//	{
//		// STUB implementation - this methods does nothing (For now)
//	}

	
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
		// STUB implementation - this methods does nothing (For now)
	}	
}
