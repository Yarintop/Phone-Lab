package app.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.annotation.JsonView;

import app.jsonViews.Views;
import app.boundaries.DigitalItemBoundary;
import app.boundaries.UserBoundary;
import app.dummyData.DummyData;

@RestController
public class DigitalItemController {
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
			@RequestBody DigitalItemBoundary newAccount)
	{
		// STUB implementation - this methods does nothing (For now)	
		Map<String,String> itemId = new HashMap<>();
		
		itemId.put("space", userSpace);
		itemId.put("id", "99");
		newAccount.setItemId(itemId);
		
		return newAccount;
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
		// STUB implementation - this methods does nothing (For now)	
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
		UserBoundary userId = new UserBoundary("","","",userEmail);
		Map<String, String> complexItemId = new HashMap<>();
		DigitalItemBoundary item = new DigitalItemBoundary();
		Map<String,Double> location = new HashMap<>();
		Map<String,Object> itemAttributes = new HashMap<>();

		
		complexItemId.put("space", userSpace);
		complexItemId.put("id", itemId);
		
		location.put("lat", 32.115139);
		location.put("lng", 34.817804);
		
		itemAttributes.put("test1","hello");
		itemAttributes.put("test2",58);
		itemAttributes.put("test3",false);
		
		item.setCreatedBy(userId);
		item.setItemId(complexItemId);
		item.setLocation(location);
		item.setItemAttributes(itemAttributes);
		return item;
	}
	
    @JsonView(Views.Item.class)
	@RequestMapping(
		path = "/twins/items/{userSpace}/{userEmail}",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public List<DigitalItemBoundary> getAllItems(
	// public DigitalItemBoundary[] getAllItems(
			@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail)
	{
		ArrayList<DigitalItemBoundary> res = new ArrayList<>();

		res.add(DummyData.getRandomDigitalItem(userSpace, userEmail));
		res.add(DummyData.getRandomDigitalItem(userSpace, userEmail));

		return res;

		// UserBoundary userId = new UserBoundary("","","",userEmail);
		// Map<String,Double> location = new HashMap<>();
		// Map<String,Object> itemAttributes = new HashMap<>();
		// AtomicLong counter = new AtomicLong(1L);
		
		// location.put("lat", 32.115139);
		// location.put("lng", 34.817804);
		
		// itemAttributes.put("test1","hello");
		// itemAttributes.put("test2",58);
		// itemAttributes.put("test3",false);
		
		// return Stream
		// 	.of(
		// 		new DigitalItemBoundary(),
		// 		new DigitalItemBoundary(),
		// 		new DigitalItemBoundary()
		// 	)
		// 	.map(input -> {
		// 		Map<String, String> complexItemId = new HashMap<>();
		// 		complexItemId.put("space", userSpace);
		// 		complexItemId.put("id", "" + counter.getAndIncrement());
		// 		input.setCreatedBy(userId);
		// 		input.setItemId(complexItemId);
		// 		input.setLocation(location);
		// 		input.setItemAttributes(itemAttributes);
		// 		return input;
		// 	})
		// 	.collect(Collectors.toList())
		// 	.toArray(new DigitalItemBoundary[0]);
	}
}
