package demo;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DigitalItemController {
	@RequestMapping(
	path = "/twins/items/{userSpace}/{userEmail}",
	method = RequestMethod.POST,
	consumes = MediaType.APPLICATION_JSON_VALUE
	)
	public DigitalItemBoundary updateUserDetails(@PathVariable("userSpace") String userSpace, @PathVariable("userEmail") String userEmail)
	{
		// STUB implementation - this methods does nothing (For now)	
		Map<String,String> userId = new HashMap<>();
		Map<String,Map<String,String>> createdBy = new HashMap<>();
		userId.put("space", userSpace);
		userId.put("email", userEmail);
		createdBy.put("userId", userId);
		
		DigitalItemBoundary item = new DigitalItemBoundary();
		item.setCreatedBy(createdBy);
		return item;
	}
	
	@RequestMapping(
	path = "/twins/items/{userSpace}/{userEmail}/{itemSpace}/{itemId}",
	method = RequestMethod.PUT,
	consumes = MediaType.APPLICATION_JSON_VALUE
	)
	public void updateItem(@PathVariable("userSpace") String userSpace, @PathVariable("userEmail") String userEmail, @PathVariable("itemSpace") String itemSpace, @PathVariable("itemId") Map<String,String> itemId)
	{
		// STUB implementation - this methods does nothing (For now)	
	}
	
	@RequestMapping(
		path = "/twins/items/{userspace}/{userEmail}/{itemSpace}/{itemId}",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public DigitalItemBoundary getSpecificItem(@PathVariable("userSpace") String userSpace, @PathVariable("userEmail") String userEmail, @PathVariable("itemSpace") String itemSpace, @PathVariable("itemId") Map<String,String> itemId)
	{
		Map<String,String> userId = new HashMap<>();
		Map<String,Map<String,String>> createdBy = new HashMap<>();
		userId.put("space", userSpace);
		userId.put("email", userEmail);
		createdBy.put("userId", userId);
		
		DigitalItemBoundary item = new DigitalItemBoundary();
		item.setCreatedBy(createdBy);
		return item;
	}
	
	@RequestMapping(
			path = "/twins/items/{userSpace}/{userEmail}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public DigitalItemBoundary[] getAllItems(@PathVariable("userSpace") String userSpace, @PathVariable("userEmail") String userEmail)
	{
		Map<String,String> userId = new HashMap<>();
		Map<String,Map<String,String>> createdBy = new HashMap<>();
		userId.put("space", userSpace);
		userId.put("email", userEmail);
		createdBy.put("userId", userId);
		
		return Stream.of(new DigitalItemBoundary(),new DigitalItemBoundary(),new DigitalItemBoundary())
				.map(input -> {
					input.setCreatedBy(createdBy);
					return input;
				}).collect(Collectors.toList())
				.toArray(new DigitalItemBoundary[0]);
		}
}
