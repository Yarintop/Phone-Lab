package demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import app.Application;
import app.boundaries.DigitalItemBoundary;

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
		String theUrl = this.baseUrl + "2021b.phone/lol@gmail.com";
		Map<String, Object> emptyItem = new HashMap<>();
		DigitalItemBoundary actualItem = this.restTemplate
			.postForObject(theUrl, emptyItem, DigitalItemBoundary.class);
			
		
		// THEN the server creates a default value and stores it in the database
		
//		if (actualMessage.getId() == null) {
//			throw new Exception("no id was generated for new message");
//		}
		// assert that the actual message is not null
		assertThat(actualItem)
			.isNotNull();
		
		// assert that the id is not null
		// AND returns message with initialized unique id
		assertThat(actualItem.getItemId())
			.isNotNull();

		// AND initialized non null time-stamp
		assertThat(actualItem.getCreatedTimestamp())
			.isNotNull();
		
		// AND initialized not null version History (e.g. []) 
		assertThat(actualItem.getItemAttributes())
			.overridingErrorMessage("expected Item Attributes not to be null")
			.isNotNull();
	}
}
