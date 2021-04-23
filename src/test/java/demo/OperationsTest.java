package demo;

import app.Application;
import app.boundaries.DigitalItemBoundary;
import app.boundaries.OperationBoundary;
import app.boundaries.UserBoundary;
import app.dummyData.DummyData;
import app.twins.logic.ItemsService;
import app.twins.logic.OperationsService;
import app.twins.logic.UsersService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OperationsTest {

    private int port;
    private String baseUrl;
    private RestTemplate restTemplate;
    private DummyData dataGenerator;
    private UsersService usersService;
    private ItemsService itemsService;
    private UserBoundary testUser;
    private OperationsService operationsService;

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
        this.usersService = usersService;
        this.itemsService = itemsService;
        this.operationsService = operationsService;
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
        this.baseUrl = "http://localhost:" + this.port + "/twins/operations/";
        this.testUser = dataGenerator.getRandomUser();
        this.testUser.setEmail("admin@admin.com");
        this.testUser.setRole("Admin");

    }

    @BeforeEach
    public void setup() {
        usersService.createUser(testUser);
    }


    @AfterEach
    public void teardown() {
        operationsService.deleteAllOperations(testUser.getUserId().getSpace(), testUser.getUserId().getEmail());
        itemsService.deleteAllItems(testUser.getUserId().getSpace(), testUser.getUserId().getEmail());
        usersService.deleteAllUsers(testUser.getUserId().getSpace(), testUser.getUserId().getEmail());

    }

    /**
     * Tests that invoking a sync operation returns a JSON and code 200
     *
     * @throws Exception if there is an error when making the request
     */
    @Test
    public void testPostOperationReturnsJSON() throws Exception {
        // GIVEN the server is up (OLD)
        // GIVEN the server is up and I want to invoke an operation with 1 item invoked by 1 user
        // and given that the user & item already in the DB
        OperationBoundary operation = dataGenerator.getRandomOperation(false);
        UserBoundary user = operation.getInvokedBy();
        DigitalItemBoundary item = operation.getItem();
        usersService.createUser(user);
        itemsService.createItem(user.getUserId().getSpace(), user.getUserId().getEmail(), item);


        // WHEN I POST using /twins/operations with an operation
        ResponseEntity<String> entity = restTemplate.postForEntity(baseUrl, operation, String.class);
        int returnCode = entity.getStatusCodeValue();
        MediaType contentType = entity.getHeaders().getContentType();

        // THEN the server invokes the operation and return JSON result

        // assert that the server returned OK code
        assertThat(returnCode).isEqualTo(200);

        // Must be of type application/json and not empty
        assertThat(contentType).isNotNull();
        assertThat(contentType.toString()).isEqualTo(MediaType.APPLICATION_JSON_VALUE);
    }

    /**
     * Tests that invoking an async operation creates new ID, and returns the same boundary just with an ID.
     *
     * @throws Exception if there is an error when making the request
     */
    @Test
    public void testPostAsyncOperationReturnsOperation() throws Exception {
        // GIVEN the server is up (OLD)
        // GIVEN the server is up and I want to invoke an operation with 1 item invoked by 1 user
        // and given that the user & item already in the DB
        OperationBoundary operation = dataGenerator.getRandomOperation(false);
        UserBoundary user = operation.getInvokedBy();
        DigitalItemBoundary item = operation.getItem();
        usersService.createUser(user);
        itemsService.createItem(user.getUserId().getSpace(), user.getUserId().getEmail(), item);

        // WHEN I POST using /twins/operations with an operation

        ResponseEntity<OperationBoundary> entity = restTemplate.postForEntity(baseUrl + "async", operation, OperationBoundary.class);
        int returnCode = entity.getStatusCodeValue();
        OperationBoundary res = entity.getBody();

        // THEN the server invokes the operation and return JSON result

        // assert that the server returned OK code
        assertThat(returnCode).isEqualTo(200);

        // assert result is not null
        assertThat(res).isNotNull();

        // assert all the fields that are not ID, equal to the original (Except for timestamp)
        assertThat(res.getType()).isEqualTo(operation.getType());
        assertThat(res.getOperationAttributes()).isEqualTo(operation.getOperationAttributes());

        assertThat(res.getItem().getItemId()).isEqualTo(operation.getItem().getItemId());
        assertThat(res.getInvokedBy().getUserId()).isEqualTo(operation.getInvokedBy().getUserId());

        // assert that a new ID was generated
        assertThat(res.getOperationId()).isNotNull();
    }


}
