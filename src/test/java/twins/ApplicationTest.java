package twins;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import twins.Application;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTest {

    /**
     * Testing that the server is up
     */
    @Test
    public void testContext() {
    }
}
