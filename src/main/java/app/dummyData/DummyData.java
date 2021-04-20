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
//        ItemIdBoundary itemId = new ItemIdBoundary(spaceId, getRandomId());
//        UserIdBoundary userId = new UserIdBoundary(spaceId, "Else@else.com");
        DigitalItemBoundary item = getRandomDigitalItem(spaceId, "else@aaa.com");
        UserBoundary user = getRandomUser();

        OperationBoundary operation = new OperationBoundary(getRandomId(), spaceId, "random type", item, user);
        if (!withId)
            operation.setOperationId(null);

        return operation;
    }

    public DigitalItemBoundary getRandomDigitalItem(String userSpace, String userEmail) {
        Random rand = new Random();
        // User ID
        ItemIdBoundary itemId = new ItemIdBoundary(spaceId, getRandomId());

        //Lat/Long
        Map<String, Double> latlng = new HashMap<>();
        latlng.put("lat", rand.nextDouble() * 40);
        latlng.put("lng", rand.nextDouble() * 40);

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
                latlng,
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
