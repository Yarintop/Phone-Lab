package demo;

import app.Application;
import app.boundaries.OperationBoundary;
import app.dummyData.DummyData;
import org.assertj.core.api.AbstractAssert;
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
        this.restTemplate = new RestTemplate();
        this.baseUrl = "http://localhost:" + this.port + "/twins/operations/";

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
    public void testPostOperationReturnsJSON() throws Exception {
        // GIVEN the server is up
        // do nothing

        // WHEN I POST using /twins/operations with an operation

        OperationBoundary operation = dataGenerator.getRandomOperation(false);
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
        // GIVEN the server is up
        // do nothing

        // WHEN I POST using /twins/operations with an operation

        OperationBoundary operation = dataGenerator.getRandomOperation(false);
        ResponseEntity<OperationBoundary> entity = restTemplate.postForEntity(baseUrl + "async", operation, OperationBoundary.class);
        int returnCode = entity.getStatusCodeValue();
        OperationBoundary res = entity.getBody();

        // THEN the server invokes the operation and return JSON result

        // assert that the server returned OK code
        assertThat(returnCode).isEqualTo(200);

        // assert result is not null
        assertThat(res).isNotNull();

        // assert all the fields that are not ID, equal to the original
        assertThat(res.getCreatedTimestamp()).isEqualTo(operation.getCreatedTimestamp());
        assertThat(res.getOperationType()).isEqualTo(operation.getOperationType());
        assertThat(res.getOperationAttributes()).isEqualTo(operation.getOperationAttributes());

        assertThat(res.getItem().getItemId()).isEqualTo(operation.getItem().getItemId());
        assertThat(res.getInvokedBy()).isEqualTo(operation.getInvokedBy());

        // assert that a new ID was generated
        assertThat(res.getOperationId()).isNotNull();
    }


}
