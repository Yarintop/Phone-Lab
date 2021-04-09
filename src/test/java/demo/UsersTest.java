package demo;

import app.Application;
import app.boundaries.DigitalItemBoundary;
import app.boundaries.NewUserDetails;
import app.boundaries.OperationBoundary;
import app.boundaries.UserBoundary;
import app.dummyData.DummyData;
import app.jsonViews.Views;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes= Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsersTest {
    private int port;
    private String baseUrl;
    private RestTemplate restTemplate;
    private String spaceId;
    private DummyData dataGenerator;

    /**
     * Sets the spaceId value from the application.properties file
     *
     * @param spaceId the loaded spaceId value, default would be "2021b.twins"
     */
    @Value("${spring.application.name:2021b.twins}")
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

    @PostConstruct
    public void init() {
        System.out.println("Hello");
        this.restTemplate = new RestTemplate();
        this.baseUrl = "http://localhost:" + this.port + "/twins/users/";

    }

    @BeforeEach
    public void setup() {
        // init operations before each test
        System.err.println("init before test..");
    }


    @AfterEach
    public void teardown() {
        System.err.println("After test..");
    }

    /**
     * Tests that invoking a sync operation returns a JSON and code 200
     *
     * @throws Exception if there is an error when making the request
     */
    @Test
    public void testPostUsersReturnsJSON() throws Exception {
        // GIVEN the server is up
        // do nothing

        // WHEN I POST using /twins/users with an user

        UserBoundary user = dataGenerator.getRandomUser();
        NewUserDetails userDetails = new NewUserDetails(user.getUserId().get("email"), user.getRole(),
                user.getUsername(), user.getAvatar());
        ResponseEntity<UserBoundary> entity = restTemplate.postForEntity(baseUrl, userDetails, UserBoundary.class);
        int returnCode = entity.getStatusCodeValue();
        MediaType contentType = entity.getHeaders().getContentType();

        // THEN the server invokes the operation and return JSON result

        // assert that the server returned OK code
        assertThat(returnCode).isEqualTo(200);

        // Must be of type application/json
        assertThat(contentType.toString()).isEqualTo(MediaType.APPLICATION_JSON_VALUE);

        UserBoundary createdUser = entity.getBody(); // Getting the user that was created

        assertThat(createdUser).isEqualToComparingFieldByFieldRecursively(user);
        // Checking that the user that was created is the same as the one that was given

        // TODO: Create tests for PUT & GET
    }

    /**
     * Tests that invoking an async operation creates new ID, and returns the same boundary just with an ID.
     *
     * @throws Exception if there is an error when making the request
     */
   /* @Test
    public void testPostUsersReturnsUser() throws Exception {
        // GIVEN the server is up
        // do nothing

        // WHEN I POST using /twins/users with an user

        UserBoundary user = dataGenerator.getRandomUser();
        NewUserDetails userDetails = new NewUserDetails(user.getUserId().get("email"), user.getRole(),
                user.getUsername(), user.getAvatar());
        ResponseEntity<UserBoundary> entity = restTemplate.postForEntity(baseUrl, userDetails, UserBoundary.class);
        int returnCode = entity.getStatusCodeValue();
        MediaType contentType = entity.getHeaders().getContentType();
        UserBoundary res = entity.getBody();

        // THEN the server invokes the operation and return JSON result

        // assert that the server returned OK code
        assertThat(returnCode).isEqualTo(200);

        // assert result is not null
        assertThat(res).isNotNull();

        // assert all the fields that are not ID, equal to the original
        assertThat(res.getRole()).isEqualTo(userDetails.getRole());
        assertThat(res.getOperationType()).isEqualTo(operation.getOperationType());
        assertThat(res.getOperationAttributes()).isEqualTo(operation.getOperationAttributes());

        /TODO: Implement equals method for UserBoundary & ItemBoundary, below methods are deprecated!/
        assertThat(res.getItem()).isEqualToComparingFieldByFieldRecursively(operation.getItem());
        assertThat(res.getInvokedBy()).isEqualToComparingFieldByFieldRecursively(operation.getInvokedBy());

        // assert that a new ID was generated
        assertThat(res.getUserId()).isNotNull();
    } */


}
