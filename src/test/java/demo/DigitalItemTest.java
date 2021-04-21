package demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import app.boundaries.UserBoundary;
import app.twins.data.UserEntity;
import app.twins.data.UserRole;
import app.twins.logic.ItemsService;
import app.twins.logic.OperationsService;
import app.twins.logic.UpdatedItemsService;
import app.twins.logic.UsersService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import app.Application;
import app.boundaries.DigitalItemBoundary;
import app.dummyData.DummyData;

@SpringBootTest(classes= Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class DigitalItemTest {
	private int port;
	private RestTemplate restTemplate;
	private String baseUrl;
	private String spaceId;
	private DummyData dataGenerator;
	private UsersService usersService;
	private UpdatedItemsService itemsService;

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
     * This function will set the dummy data generator
     *
     * @param dataGenerator - an instance of {@link DummyData}
     */
    @Autowired
    public void setDummyDataGenerator(DummyData dataGenerator) {
        this.dataGenerator = dataGenerator;
    }
	
    /**
     * Sets the port for the testing environment
     *
     * @param port - the port given from Spring
     */
	@LocalServerPort
	public void setPort(int port) {
		this.port = port;
	}

	@Autowired
	public void setServices(UsersService usersService, UpdatedItemsService itemsService) {
		this.usersService = usersService;
		this.itemsService = itemsService;
	}
	
	@PostConstruct
	public void init (){
		this.restTemplate = new RestTemplate();
		this.baseUrl = "http://localhost:" + this.port + "/twins/items/";
		
	}
	
	@BeforeEach
	public void setup() {
		// init operations before each test
		System.err.println("init before test..");
		this.usersService.createUser(new UserBoundary(UserRole.ADMIN.toString(), "tests", "yup", "lol@gmail.com", spaceId));
	}

	
	@AfterEach
	public void teardown () {
//		this.restTemplate
//			.delete(this.baseUrl);
		System.err.println("After test..");
		this.itemsService.deleteAllItems(spaceId, "lol@gmail.com");
		this.usersService.deleteAllUsers(spaceId, "lol@gmail.com");
	}
	
	@Test
	public void testContext() throws Exception {
		// GIVEN the server is up
		
		// WHEN the application initializes
		
		// THEN spring starts up with no errors
	}
	
	
    /**
     * Tests that Item is created with POST (given empty JSON {}), returns a JSON , code 200
     * and a valid ItemBoundary object.
     *
     * @throws Exception if there is an error when making the request
     */
	@Test
	public void testPostEmptyMessageOnServer() throws Exception {
		// GIVEN the server is up
		// do nothing
		
		// WHEN I POST using /twins/items/{userSpace}/{userEmail} with {}
		String space = this.spaceId;
		String email = "lol@gmail.com";
		String theUrl = this.baseUrl + space + "/" + email;
		
		
		Map<String, Object> emptyItem = new HashMap<>();
		DigitalItemBoundary actualItem = this.restTemplate
			.postForObject(theUrl, emptyItem, DigitalItemBoundary.class);
			
		
		// THEN the server creates a default value and stores it in the database
		
		// assert that the actual item is not null
		assertThat(actualItem).isNotNull();
				
		// Must have ItemId map that is not null and have the keys id and space with correct values
		assertThat(actualItem.getItemId()).isNotNull();
		assertThat(actualItem.getItemId().getId()).isNotNull();
		assertThat(actualItem.getItemId().getSpace()).isNotNull().isEqualTo(space);
		
		// assert that the User Boundary is not null
		assertThat(actualItem.getCreatedBy()).isNotNull();
		
		// Must have userId map that is not null and have the keys email and space with correct values
		assertThat(actualItem.getCreatedBy()).isNotNull();
		assertThat(actualItem.getCreatedBy().getUserId()).isNotNull();
		assertThat(actualItem.getCreatedBy().getUserId().getEmail()).isNotNull().isEqualTo(email);
		assertThat(actualItem.getCreatedBy().getUserId().getSpace()).isNotNull().isEqualTo(space);
		
		// AND initialized non null time-stamp
		assertThat(actualItem.getCreatedTimestamp())
			.isNotNull();
		
		// AND initialized not null Item Attributes(e.g. {}) 
		assertThat(actualItem.getItemAttributes())
			.overridingErrorMessage("expected Item Attributes not to be null")
			.isNotNull();
	}
	
    /**
     * Tests that Item is created with POST, returns a JSON , code 200
     * and a valid ItemBoundary object.
     *
     * @throws Exception if there is an error when making the request
     */
	@Test
	public void testPostSuccessfulAllKeys() throws Exception {
		// GIVEN the server is up
		// do nothing
		
		// WHEN I POST using /twins/items/{userSpace}/{userEmail} with a specific JSON
		String space = this.spaceId;
		String email = "lol@gmail.com";
		String theUrl = this.baseUrl + space + "/" + email;
        // ObjectMapper mapper = new ObjectMapper();
		DigitalItemBoundary randomItem = dataGenerator.getRandomDigitalItem(space, email);
		
		// Map<String, Object> myItem = mapper.convertValue(randomItem, Map.class);
		DigitalItemBoundary actualItem = this.restTemplate
			.postForObject(theUrl, randomItem, DigitalItemBoundary.class);
		
		assertTwoItemsAreEqual(actualItem, randomItem);
	}
	
	private void assertTwoItemsAreEqual(DigitalItemBoundary resultItem, DigitalItemBoundary originalItem) throws Exception {
		String email = originalItem.getCreatedBy().getUserId().getEmail();
		String space = originalItem.getCreatedBy().getUserId().getSpace();
				
		// Must have ItemId map that is not null and have the keys id and space with correct values
		assertThat(resultItem.getItemId()).isNotNull();
		assertThat(resultItem.getItemId().getId()).isNotNull();
		assertThat(resultItem.getItemId().getSpace()).isNotNull().isEqualTo(space);
		
		// Date time should not be null
		assertThat(resultItem.getCreatedTimestamp()).isNotNull();
		
		// Make sure name value is same as was given
		assertThat(resultItem.getName())
		.isEqualTo(originalItem.getName());
		
		// Make sure type value is same as was given
		assertThat(resultItem.getType())
		.isEqualTo(originalItem.getType());
		
		// Make sure active value is same as was given
		assertThat(resultItem.getActive())
		.isEqualTo(originalItem.getActive());
		
		// Must have userId map that is not null and have the keys email and space with correct values
		assertThat(resultItem.getCreatedBy()).isNotNull();
		assertThat(resultItem.getCreatedBy().getUserId()).isNotNull();
		assertThat(resultItem.getCreatedBy().getUserId().getEmail()).isEqualTo(email);
		assertThat(resultItem.getCreatedBy().getUserId().getSpace()).isEqualTo(space);
		
		// The Item attributes should exist and have the same key and values
		assertThat(resultItem.getItemAttributes()).isNotNull();
		assertThat(resultItem.getItemAttributes()).isEqualTo(originalItem.getItemAttributes());
	}
	
	
    /**
     * Tests that a specific item is correctly retrieved when using GET 
     * 
     *
     * @throws Exception if there is an error when making the request
     */
	@Test
	public void testGetSpecificItem() throws Exception {
		// GIVEN the server is up
		// do nothing
		
		// WHEN I GET using /twins/items/{userSpace}/{userEmail}/{itemSpace}/{itemId}
		String space = this.spaceId;
		String email = "lol@gmail.com";
		String theUrl = this.baseUrl + space + "/" + email;
        // ObjectMapper mapper = new ObjectMapper();
		DigitalItemBoundary randomItem = dataGenerator.getRandomDigitalItem(space, email);
		System.out.println(randomItem);
		
		// Add an item to the existing items so that I can use get specific item
		// Map<String, Object> myItem = mapper.convertValue(randomItem, Map.class);
		DigitalItemBoundary actualItem = this.restTemplate.postForObject(theUrl, randomItem, DigitalItemBoundary.class);
		
		
		String itemId = actualItem.getItemId().getId();
		String itemSpace = actualItem.getItemId().getSpace();
		
		System.out.println("Item Id: " + itemId + " Item Space: " + itemSpace);
		System.out.println(actualItem);
		
		theUrl = theUrl + "/" + itemSpace + "/" + itemId;
		// Use the known ID of the created Item and search it
		DigitalItemBoundary retrievedItem = this.restTemplate
				.getForEntity(theUrl, DigitalItemBoundary.class).getBody();
		
		// Check that item id matches
		assertThat(retrievedItem.getItemId().getId()).isEqualTo(itemId);
		assertThat(retrievedItem.getItemId().getSpace()).isEqualTo(itemSpace);
		System.out.println("dasdasdsad afasfsaf \n\n" + retrievedItem);
		// Make sure all values are the same.
		assertTwoItemsAreEqual(retrievedItem, actualItem);
	}
	
    /**
     * Tests that an item is updated when using PUT and checking it
     * by using GET.
     *
     * @throws Exception if there is an error when making the request
     */
	@Test
	public void testUpdateItem() throws Exception {
		// GIVEN the server is up
		// do nothing
		
		// WHEN I PUT using /twins/items/{userSpace}/{userEmail}/{itemSpace}/{itemId}
		String space = this.spaceId;
		String email = "lol@gmail.com";
		String theUrl = this.baseUrl + space + "/" + email;
		DigitalItemBoundary randomItem = dataGenerator.getRandomDigitalItem(space, email);
		
		// First add an item to the existing items so that I can update it
		DigitalItemBoundary actualItem = this.restTemplate.postForObject(theUrl, randomItem, DigitalItemBoundary.class);
		
		
		String itemId = actualItem.getItemId().getId();
		String itemSpace = actualItem.getItemId().getSpace();
		
		randomItem = dataGenerator.getRandomDigitalItem(space, email);
		theUrl = theUrl + "/" + itemSpace + "/" + itemId;
		
		// Update the Item
        restTemplate.put(theUrl, randomItem, DigitalItemBoundary.class);

		
		// Use the known ID of the created Item and search it
		DigitalItemBoundary retrievedItem = this.restTemplate
				.getForEntity(theUrl, DigitalItemBoundary.class).getBody();
		
		// Check that item id matches even though was updated
		assertThat(retrievedItem.getItemId().getId()).isEqualTo(itemId);
		assertThat(retrievedItem.getItemId().getSpace()).isEqualTo(itemSpace);
		
		// Make sure all values are the same as new random data.
		assertTwoItemsAreEqual(retrievedItem, randomItem);
	}
	
	
    /**
     * Tests that all items are correctly retrieved when using GET 
     * 
     *
     * @throws Exception if there is an error when making the request
     */
	@Test
	public void testGetAllItems() throws Exception {
		// GIVEN the server is up
		// do nothing
		
		// WHEN I GET all using /twins/items/{userSpace}/{userEmail}

		String space = this.spaceId;
		String email = "lol@gmail.com";
		String theUrl = this.baseUrl + space + "/" + email;

        // ObjectMapper mapper = new ObjectMapper();
		DigitalItemBoundary randomItem = dataGenerator.getRandomDigitalItem(space, email);
		// Map<String, Object> myItem = mapper.convertValue(randomItem, Map.class);
		

		// Add items to the existing items so that I can get all after that
		this.restTemplate.postForObject(theUrl, randomItem, DigitalItemBoundary.class);
		this.restTemplate.postForObject(theUrl, randomItem, DigitalItemBoundary.class);
		
		DigitalItemBoundary[] response = this.restTemplate
			.getForEntity(theUrl, DigitalItemBoundary[].class).getBody();
				
		// Make sure we got 2 items from get all as we only added 2 items
		assertThat(response).isNotNull().hasSize(2);
	}
	
}
