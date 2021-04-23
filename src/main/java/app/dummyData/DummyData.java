package app.dummyData;

import app.boundaries.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DummyData {

    private final String[] types = {"phone", "placeholder", "sparepart", "person"};
    private final String[] names = {"donnu", "thing", "screw", "lg5"};
    private final String[] roles = {"Manager", "Player", "Admin"};
    private final String[][] users = {
            {"dima", "dima@guy.com"},
            {"yarin.mizrahiTfahot ", "yarin@guy.com"},
            {"rafi", "rafi@guy.com"}
    };

    private String spaceId;

    /**
     * Set the spaceId , to avoid hard coding the "2021b.twins" as the key value (future proofing)
     *
     * @param spaceId - the value for the spaceId in the map values
     */
    @Value("${spring.application.name:2021b.notdef}")
    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    public String getRandomId() {
        return String.valueOf(UUID.randomUUID());
    }


    public OperationBoundary getRandomOperation(boolean withId) {

        // Get random user
        UserBoundary user = getRandomUser();

        // Get random item
        DigitalItemBoundary item = getRandomDigitalItem(spaceId, getRandomId());

        OperationBoundary operation = new OperationBoundary();
        operation.setType("RandomOperation");
        operation.setItem(item);
        operation.setInvokedBy(user);
        if (withId) {
            OperationIdBoundary operationId = new OperationIdBoundary(spaceId, getRandomId());
            operation.setOperationId(operationId);
        }
        return operation;
    }

    public DigitalItemBoundary getRandomDigitalItem(String userSpace, String userEmail) {
        Random rand = new Random();
        // User ID
        ItemIdBoundary itemId = new ItemIdBoundary(userSpace, getRandomId());

        //Lat/Long
        LocationBoundary latLng = new LocationBoundary(rand.nextDouble() * 40, rand.nextDouble() * 40);

        //Attrs
        Map<String, Object> attrs = new HashMap<>();
        attrs.put("randomList", "of objects");
        attrs.put("a1", false);
        attrs.put("b2", 5);
        attrs.put("c3", -1.5);
        return new DigitalItemBoundary(
                itemId,
                types[rand.nextInt(types.length)],
                names[rand.nextInt(names.length)],
                (rand.nextInt() % 2 == 0),
                new Date(),
                new UserBoundary(userEmail, userSpace),
                latLng,
                attrs
        );
    }

    public UserBoundary getRandomUser() {
        Random rand = new Random();
        String[] user = users[rand.nextInt(users.length)];
        String role = roles[rand.nextInt(roles.length)];

        return new UserBoundary(
                role,
                user[0],
                user[0].toUpperCase().charAt(0) + "",
                user[1],
                this.spaceId
        );
    }
}
