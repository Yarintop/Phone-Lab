package twins;

import twins.Application;
import twins.boundaries.DigitalItemBoundary;
import twins.boundaries.NewUserDetails;
import twins.boundaries.OperationBoundary;
import twins.boundaries.UserBoundary;
import twins.dummyData.DummyData;
import twins.logic.ItemsService;
import twins.logic.OperationsService;
import twins.logic.UsersService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class AdminTest {
    private int port;
    private RestTemplate restTemplate;
    private String baseUrl;
    private String spaceId;
    private DummyData dataGenerator;
    private UsersService usersService;
    private OperationsService operationsService;
    private ItemsService itemsService;

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

    @Autowired
    public void setServices(UsersService usersService, ItemsService itemsService, OperationsService operationsService) {
        this.operationsService = operationsService;
        this.usersService = usersService;
        this.itemsService = itemsService;
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

    @PostConstruct
    public void init() {
        this.restTemplate = new RestTemplate();
        this.baseUrl = "http://localhost:" + this.port + "/twins/";

    }

    @BeforeEach
    public void setup() {
        UserBoundary user = dataGenerator.getRandomUser();
        user.setRole("Admin");
        user.setEmail("admin@gmail.com");
        usersService.createUser(user);

    }


    @AfterEach
    public void teardown() {
        System.err.println("After test..");
        if (usersService.getAllUsers(spaceId, "admin@gmail.com").size() > 0) {
            operationsService.deleteAllOperations(spaceId, "admin@gmail.com");
            itemsService.deleteAllItems(spaceId, "admin@gmail.com");
            usersService.deleteAllUsers(spaceId, "admin@gmail.com");
        }
    }

    /**
     * Tests that all operations are correctly retrieved when using GET
     *
     * @throws Exception if there is an error when making the request
     */
    @Test
    public void testGetAllOperations() throws Exception {
        // GIVEN the server is up
        // do nothing

        // WHEN I GET all using /twins/admin/operations/{userSpace}/{userEmail}
        String space = this.spaceId;
        String email = "admin@gmail.com";
        String theUrl = this.baseUrl + "admin/operations/" + space + "/" + email;

        OperationBoundary operation = dataGenerator.getRandomOperation(false);

        // Save generated data to database
        UserBoundary invokedBy = operation.getInvokedBy();
        DigitalItemBoundary item = operation.getItem();
        usersService.createUser(invokedBy);
        itemsService.createItem(invokedBy.getUserId().getSpace(), invokedBy.getUserId().getEmail(), item);
        // First check that currently operations are empty

        OperationBoundary[] response = this.restTemplate
                .getForEntity(theUrl, OperationBoundary[].class).getBody();
        assertThat(response).isNotNull().hasSizeGreaterThanOrEqualTo(0);
        int operationCount = response.length;

        // Then add operations by using POST

        restTemplate.postForEntity(baseUrl + "operations", operation, OperationBoundary.class);
        restTemplate.postForEntity(baseUrl + "operations/async", operation, OperationBoundary.class);

        response = this.restTemplate
                .getForEntity(theUrl, OperationBoundary[].class).getBody();


        // Make sure we got array of size 2 from get all 
        assertThat(response).isNotNull().hasSize(operationCount + 2);

    }

    /**
     * Tests that all users are correctly retrieved when using GET
     *
     * @throws Exception if there is an error when making the request
     */
    @Test
    public void testGetAllUsers() throws Exception {
        // GIVEN the server is up
        // do nothing

        // WHEN I GET all using /twins/admin/users/{userSpace}/{userEmail}

        UserBoundary user = dataGenerator.getRandomUser();
        NewUserDetails userDetails = new NewUserDetails(user.getUserId().getEmail(), "Admin",
                user.getUsername(), user.getAvatar());
        String space = this.spaceId;
        String email = user.getUserId().getEmail();
        String theUrl = this.baseUrl + "admin/users/" + space + "/" + email;

        // First creating a user by using POST

        restTemplate.postForEntity(baseUrl + "users", userDetails, UserBoundary.class);

        UserBoundary[] response = this.restTemplate
                .getForEntity(theUrl, UserBoundary[].class).getBody();

        // THEN get an array of all users
        // Make sure we got array of size 1 from get all 
        assertThat(response).isNotNull().hasSizeGreaterThanOrEqualTo(1);
    }

    /**
     * Tests that all operations are being deleted when using DELETE
     *
     * @throws Exception if there is an error when making the request
     */
    @Test
    public void testDeleteAllOperations() throws Exception {
        // GIVEN the server is up
        // do nothing
        // WHEN I DELETE all using /twins/admin/operations/{userSpace}/{userEmail}
        // THEN delete all operations

        String space = this.spaceId;
        String email = "admin@gmail.com";
        String theUrl = this.baseUrl + "admin/operations/" + space + "/" + email;


        // First check that currently operations is not empty

        OperationBoundary[] response = this.restTemplate
                .getForEntity(theUrl, OperationBoundary[].class).getBody();

        // Check that the response not null
        assertThat(response).isNotNull();

        if (response.length == 0) {
            // If empty then add operations by using POST

            // Save generated data to database
            OperationBoundary operation = dataGenerator.getRandomOperation(false);
            UserBoundary invokedBy = operation.getInvokedBy();
            DigitalItemBoundary item = operation.getItem();
            usersService.createUser(invokedBy);
            itemsService.createItem(invokedBy.getUserId().getSpace(), invokedBy.getUserId().getEmail(), item);

            restTemplate.postForEntity(baseUrl + "operations", operation, OperationBoundary.class);
            restTemplate.postForEntity(baseUrl + "operations/async", operation, OperationBoundary.class);

            response = this.restTemplate.getForEntity(theUrl, OperationBoundary[].class).getBody();

            // Make sure we got array of size 2 from get all 
            assertThat(response).isNotNull().hasSize(2);
        }

        // Now Delete All and check size is zero
        restTemplate.delete(theUrl);
        response = this.restTemplate.getForEntity(theUrl, OperationBoundary[].class).getBody();
        assertThat(response).isNotNull().hasSize(0);

    }

    /**
     * Tests that all users are being deleted when using DELETE
     *
     * @throws Exception if there is an error when making the request
     */
    @Test
    public void testDeleteAllUsers() throws Exception {
        // GIVEN the server is up
        // do nothing

        // WHEN I DELETE all using /twins/admin/users/{userSpace}/{userEmail}
        // THEN delete all users

        UserBoundary user = dataGenerator.getRandomUser();
        NewUserDetails userDetails1 = new NewUserDetails(user.getUserId().getEmail() + "1", "Admin",
                user.getUsername(), user.getAvatar());
        NewUserDetails userDetails2 = new NewUserDetails(user.getUserId().getEmail() + "2", "Admin",
                user.getUsername(), user.getAvatar());
        NewUserDetails userDetails3 = new NewUserDetails(user.getUserId().getEmail() + "3", "Admin",
                user.getUsername(), user.getAvatar());
        String space = this.spaceId;
        String email = user.getUserId().getEmail() + "1";
        String theUrl = this.baseUrl + "admin/users/" + space + "/" + email;

        // First adding three users by using POST

        restTemplate.postForEntity(baseUrl + "users", userDetails1, UserBoundary.class);
        restTemplate.postForEntity(baseUrl + "users", userDetails2, UserBoundary.class);
        restTemplate.postForEntity(baseUrl + "users", userDetails3, UserBoundary.class);

        UserBoundary[] response = this.restTemplate
                .getForEntity(theUrl, UserBoundary[].class).getBody();


        assertThat(response).isNotNull().hasSizeGreaterThanOrEqualTo(3);

        // Now Delete All and check size is zero
        restTemplate.delete(theUrl);

        // Add an admin user so I can get all (only admin user has permission to get all)
        restTemplate.postForEntity(baseUrl + "users", userDetails1, UserBoundary.class);
        response = this.restTemplate.getForEntity(theUrl, UserBoundary[].class).getBody();

        // Check that size is one meaning only the newly added admin user
        assertThat(response).isNotNull().hasSize(1);

        setup();
    }


    /**
     * Tests that all items are being deleted correctly when using DELETE
     *
     * @throws Exception if there is an error when making the request
     */
    @Test
    public void testGetAllItems() throws Exception {
        // GIVEN the server is up
        // do nothing
        // WHEN I DELETE all using /twins/admin/items/{userSpace}/{userEmail}
        // THEN delete all items

        String space = this.spaceId;
        String email = "admin@gmail.com";
        String theUrl = this.baseUrl + "admin/items/" + space + "/" + email;
        String itemUrl = this.baseUrl + "items/" + this.spaceId + "/" + email;


        // First check that currently operations is not empty

        DigitalItemBoundary[] response = this.restTemplate
                .getForEntity(itemUrl, DigitalItemBoundary[].class).getBody();

        // Check that the response not null
        assertThat(response).isNotNull();
        if (response.length == 0) {
            // If empty then add operations by using POST
            DigitalItemBoundary randomItem = dataGenerator.getRandomDigitalItem(space, email);
            this.restTemplate.postForObject(itemUrl, randomItem, DigitalItemBoundary.class);
            this.restTemplate.postForObject(itemUrl, randomItem, DigitalItemBoundary.class);

            response = this.restTemplate.getForEntity(itemUrl, DigitalItemBoundary[].class).getBody();

            // Make sure we got 2 items from get all as we only added 2 items
            assertThat(response).isNotNull().hasSize(2);
        }

        // Now Delete All and check size is zero
        restTemplate.delete(theUrl);
        response = this.restTemplate.getForEntity(itemUrl, DigitalItemBoundary[].class).getBody();
        assertThat(response).isNotNull().hasSize(0);
    }
}
