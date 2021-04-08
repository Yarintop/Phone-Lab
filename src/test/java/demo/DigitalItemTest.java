package demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

import app.Application;
import app.boundaries.DigitalItemBoundary;
import app.dummyData.DummyData;

@SpringBootTest(classes= Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class DigitalItemTest {
	private int port;
	private RestTemplate restTemplate;
	private String baseUrl;
	
	@LocalServerPort
	public void setPort(int port) {
		this.port = port;
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
	}

	
	@AfterEach
	public void teardown () {
//		this.restTemplate
//			.delete(this.baseUrl);
		System.err.println("After test..");
	}
	
	@Test
	public void testContext() throws Exception {
		// GIVEN the server is up
		
		// WHEN the application initializes
		
		// THEN spring starts up with no errors
	}
	
	@Test
	public void testPostEmptyMessageOnServer() throws Exception {
		// GIVEN the server is up
		// do nothing
		
		// WHEN I POST using /twins/items/{userSpace}/{userEmail} with {}
		String space = "2021b.phone";
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
		assertThat(actualItem.getItemId().get("id")).isNotNull();
		assertThat(actualItem.getItemId().get("space")).isNotNull().isEqualTo(space);
		
		// assert that the User Boundary is not null
		assertThat(actualItem.getCreatedBy()).isNotNull();
		
		// Must have userId map that is not null and have the keys email and space with correct values
		assertThat(actualItem.getCreatedBy().getUserId()).isNotNull();
		assertThat(actualItem.getCreatedBy().getUserId().get("email")).isNotNull().isEqualTo(email);
		assertThat(actualItem.getCreatedBy().getUserId().get("space")).isNotNull().isEqualTo(space);
		
		// AND initialized non null time-stamp
		assertThat(actualItem.getCreatedTimestamp())
			.isNotNull();
		
		// AND initialized not null Item Attributes(e.g. {}) 
		assertThat(actualItem.getItemAttributes())
			.overridingErrorMessage("expected Item Attributes not to be null")
			.isNotNull();
	}
	
	@Test
	public void testPostSuccessfulAllKeys() throws Exception {
		// GIVEN the server is up
		// do nothing
		
		// WHEN I POST using /twins/items/{userSpace}/{userEmail} with a specific JSON
		String space = "2021b.phone";
		String email = "lol@gmail.com";
		String theUrl = this.baseUrl + space + "/" + email;
        ObjectMapper mapper = new ObjectMapper();
		DigitalItemBoundary randomItem = DummyData.getRandomDigitalItem(space, email);
		
		Map<String, Object> myItem = mapper.convertValue(randomItem, Map.class);
		DigitalItemBoundary actualItem = this.restTemplate
			.postForObject(theUrl, myItem, DigitalItemBoundary.class);
		
		// Date time should not be null
		assertThat(actualItem.getCreatedTimestamp()).isNotNull();

		// Date time should be based on real creation and not about what was given so should have different value than given
		assertThat(actualItem.getCreatedTimestamp().toString())
		.isNotEqualTo(randomItem.getCreatedTimestamp());
		
		// Make sure name value is same as was given
		assertThat(actualItem.getName())
		.isEqualTo(randomItem.getName());
		
		// Make sure type value is same as was given
		assertThat(actualItem.getType())
		.isEqualTo(randomItem.getType());
		
		// Make sure active value is same as was given
		assertThat(actualItem.getActive())
		.isEqualTo(randomItem.getActive());
		
		// Must have userId map that is not null and have the keys email and space with correct values
		assertThat(actualItem.getCreatedBy()).isNotNull();
		assertThat(actualItem.getCreatedBy().getUserId()).isNotNull();
		assertThat(actualItem.getCreatedBy().getUserId().get("email")).isEqualTo(email);
		assertThat(actualItem.getCreatedBy().getUserId().get("space")).isEqualTo(space);
		
		// The Item attributes should exist and have the same key and values
		assertThat(actualItem.getItemAttributes()).isNotNull();
		assertThat(actualItem.getItemAttributes()).isEqualTo(randomItem.getItemAttributes());
	}
	
	@Test
	public void testGetAllItems() throws Exception {
		// GIVEN the server is up
		// do nothing
		
		// WHEN I get using /twins/items/{userSpace}/{userEmail} with {}

		String space = "2021b.phone";
		String email = "lol@gmail.com";
		String theUrl = this.baseUrl + space + "/" + email;

        ObjectMapper mapper = new ObjectMapper();
		DigitalItemBoundary randomItem = DummyData.getRandomDigitalItem(space, email);
		Map<String, Object> myItem = mapper.convertValue(randomItem, Map.class);
		

		// Add items to the existing items so that I can get all after that
		this.restTemplate.postForObject(theUrl, myItem, DigitalItemBoundary.class);
		this.restTemplate.postForObject(theUrl, myItem, DigitalItemBoundary.class);
		
		DigitalItemBoundary[] response = this.restTemplate
			.getForEntity(theUrl, DigitalItemBoundary[].class).getBody();
		
		// Make sure we got 2 items from get all as we only added 2 items
		assertThat(response).isNotNull().hasSize(2);
		
//		System.out.println(response[0].getName());
//		System.out.println(response[0].getType());
//		System.out.println(response[0].getActive());
//		System.out.println(response[0].getLocation());
//		System.out.println(response[0].getItemAttributes());
//		System.out.println(response[0].getItemId());
//		System.out.println(response[0].getCreatedBy().getUserId());
//		
//		System.out.println(response[1].getName());
//		System.out.println(response[1].getType());
//		System.out.println(response[1].getActive());
//		System.out.println(response[1].getLocation());
//		System.out.println(response[1].getItemAttributes());
//		System.out.println(response[1].getItemId());
//		System.out.println(response[1].getCreatedBy().getUserId());


	}
	
	
}
