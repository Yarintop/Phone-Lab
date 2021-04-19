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

    private String spaceIdKey;
    private String spaceId;
    private String idKey;

    /**
     * Set the id key, to avoid hard coding the "id" as the key value (future proofing)
     *
     * @param idKey - the key for the id in the map values
     */
    @Value("${key.id:id}")
    public void setIdKey(String idKey) {
        this.idKey = idKey;
    }

    /**
     * Set the spaceId key, to avoid hard coding the "spaceId" as the key value (future proofing)
     *
     * @param spaceIdKey - the key for the spaceId in the map values
     */
    @Value("${key.space:spaceId}")
    public void setSpaceIdKey(String spaceIdKey) {
        this.spaceIdKey = spaceIdKey;
    }

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
        ItemIdBoundary itemId = new ItemIdBoundary(spaceId, getRandomId());

        itemId.setId(getRandomId());

        UserBoundary user = new UserBoundary(
                "Random",
                "Name",
                "Something",
                "Else@else.com",
                this.spaceId
        );

        DigitalItemBoundary item = new DigitalItemBoundary(
                itemId,
                "RandomItem",
                "RandomName",
                false,
                new Date(),
                user,
                new HashMap<>(),
                new HashMap<>()
        );

        OperationBoundary operation = new OperationBoundary();
        operation.setOperationType("RandomOperation");
        operation.setItem(item);
        operation.setInvokedBy(user);
        if (withId) {
            Map<String, String> operationId = new HashMap<>();
            operationId.put(spaceIdKey, spaceId);
            operationId.put(idKey, getRandomId());
            operation.setOperationId(operationId);
        }
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
