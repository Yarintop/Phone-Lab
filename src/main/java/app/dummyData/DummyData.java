package app.dummyData;

import app.boundaries.DigitalItemBoundary;
// import app.boundaries.NewUserDetails;
import app.boundaries.OperationBoundary;
import app.boundaries.UserBoundary;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DummyData
{
    private static String getRandomIdString(int bound)
    {
        Random rand = new Random();
        String res = String.valueOf( rand.nextInt(bound) );

        return res;
    }

    // (Map<String, String> itemId, String type, String name, Boolean active,
    //         Date createdTimestamp, UserBoundary createdBy, Map<String, Double> location,
    //         Map<String, Object> itemAttributes) {
    public static OperationBoundary getRandomOperation()
    {
        Map<String, String> itemId = new HashMap<>();
        itemId.put("space", "2021b.twins"); //TODO: pull it from a resource, make it less hard-coded.
        itemId.put("id", getRandomIdString(10000));

        UserBoundary user = new UserBoundary(
            "Random",
            "Name",
            "Something",
            "Else@else.com"
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

        return new OperationBoundary(
            getRandomIdString(10000),
            "RandomOperation",
            item,
            user
        );
    }

    private static final String[] roles = { "bestGuy", "bank", "thug" };
    private static final String[][] users = {
        {"dima", "dima@guy.com"},
        {"yarin.mizrahiTfahot", "yarin@guy.com"},
        {"rafi", "rafi@guy.com"}
    };
    public static UserBoundary getRandomUser()
    {
        Random rand = new Random();
        String[] user = users[rand.nextInt(users.length)];
        String role = roles[rand.nextInt(roles.length)];

        return new UserBoundary(
            role,
            user[0],
            user[0].toUpperCase().charAt(0) + "",
            user[1]
        );
    }

    private static final String[] types = { "phone", "placeholder", "sparepart", "person" };
    private static final String[] names = { "donnu", "thing", "screw", "lg5" };
    public static DigitalItemBoundary getRandomDigitalItem(String userSpace, String userEmail)
    {
        Random rand = new Random();

        return new DigitalItemBoundary(
            Map.of(
                "id", getRandomIdString(10000),
                "space", userSpace
            ),
            types[rand.nextInt(types.length)],
            names[rand.nextInt(names.length)],
            (rand.nextInt()%2 == 0),
            new Date(),
            new UserBoundary(
                userEmail,
                userSpace
            ),
            Map.of(
                "lat", rand.nextDouble()*40,
                "lng", rand.nextDouble()*40
            ),
            Map.of(
                "randomList", "of objects",
                "a1", false,
                "b2", 5,
                "c3", -1.5
            )
        );
    }
}
