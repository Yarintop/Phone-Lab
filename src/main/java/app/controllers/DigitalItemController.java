package app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.annotation.JsonView;

import app.jsonViews.Views;
import app.twins.logic.ItemsService;
import app.boundaries.DigitalItemBoundary;

@RestController
public class DigitalItemController {
	private ItemsService itemLogic;
	

	@Autowired	
	public DigitalItemController(ItemsService itemLogic) {
		super();
		this.itemLogic = itemLogic;
	}
	
    @JsonView(Views.Item.class)
	@RequestMapping(
		path = "/twins/items/{userSpace}/{userEmail}",
		method = RequestMethod.POST,
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public DigitalItemBoundary updateUserDetails(
			@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail,
			@RequestBody DigitalItemBoundary newItem)
	{
		return itemLogic
				.createItem(userSpace, userEmail, newItem);
	}
	
	@RequestMapping(
		path = "/twins/items/{userSpace}/{userEmail}/{itemSpace}/{itemId}",
		method = RequestMethod.PUT,
		consumes = MediaType.APPLICATION_JSON_VALUE
	)
	public void updateItem(
			@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail,
			@PathVariable("itemSpace") String itemSpace,
			@PathVariable("itemId") String itemId,
			@RequestBody DigitalItemBoundary update)

	{
		this.itemLogic
		.updateItem(userSpace, userEmail, itemSpace, itemId, update);
	}
	
    @JsonView(Views.Item.class)
	@RequestMapping(
		path = "/twins/items/{userSpace}/{userEmail}/{itemSpace}/{itemId}",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public DigitalItemBoundary getSpecificItem(
			@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail,
			@PathVariable("itemSpace") String itemSpace,
			@PathVariable("itemId") String itemId)
	{
		return this.itemLogic
				.getSpecificItem(userSpace, userEmail, itemSpace, itemId);
	}
	
    @JsonView(Views.Item.class)
	@RequestMapping(
		path = "/twins/items/{userSpace}/{userEmail}",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public DigitalItemBoundary[] getAllItems(
	// public DigitalItemBoundary[] getAllItems(
			@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail)
	{
		List<DigitalItemBoundary> allItems = 
				this.itemLogic.getAllItems(userSpace, userEmail);
			
		return allItems.toArray(new DigitalItemBoundary[0]);
	}
    
	/**
	 * Deletes all items in the requested space
	 * @param adminSpace - Requested admin space
	 * @param adminEmail - Invoker's admin email
	 */
	@RequestMapping(
		path = "/twins/admin/items/{userSpace}/{userEmail}",
		method = RequestMethod.DELETE
	)
	public void deleteAllItems(@PathVariable("userSpace") String adminSpace, @PathVariable("userEmail") String adminEmail)
	{
		this.itemLogic.deleteAllItems(adminSpace, adminEmail);
	}
}
