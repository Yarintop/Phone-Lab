package app.controllers;

import java.util.ArrayList;
import java.util.List;
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

	@RequestMapping(
		path = "/twins/admin/operations/{userSpace}/{userEmail}",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	@JsonView(Views.Operation.class)
	public List<OperationBoundary> exportAllOperations(@PathVariable("userSpace") String id, @PathVariable("userEmail") String userEmail)
	{
		List<OperationBoundary> allOperations = new ArrayList<OperationBoundary>();
		
		allOperations.add(
			new OperationBoundary()
		);
		allOperations.add(
			new OperationBoundary()
		);

		return allOperations;
	}

	@RequestMapping(
		path = "/twins/admin/users/{userSpace}/{userEmail}",
		method = RequestMethod.DELETE
	)
	public void deleteAllUsers(@PathVariable("userSpace") String id, @PathVariable("userEmail") String userEmail)
	{
		//TODO
	}

	@RequestMapping(
		path = "/twins/admin/items/{userSpace}/{userEmail}",
		method = RequestMethod.DELETE
	)
	public void deleteAllItems(@PathVariable("userSpace") String id, @PathVariable("userEmail") String userEmail)
	{
		//TODO
	}

	@RequestMapping(
		path = "/twins/admin/operations/{userSpace}/{userEmail}",
		method = RequestMethod.DELETE
	)
	public void deleteAllOperations(@PathVariable("userSpace") String id, @PathVariable("userEmail") String userEmail)
	{
		//TODO
	}	
}
