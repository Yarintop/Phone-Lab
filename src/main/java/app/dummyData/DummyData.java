package app.dummyData;

import app.boundaries.DigitalItemBoundary;
import app.boundaries.NewUserDetails;
import app.boundaries.OperationBoundary;
import app.boundaries.UserBoundary;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DummyData {
    public static OperationBoundary getRandomOperation() {
//        (Map<String, String> itemId, String type, String name, Boolean active,
//			Date createdTimestamp, UserBoundary createdBy, Map<String, Double> location,
//			Map<String, Object> itemAttributes) {
        Map<String, String> itemId = new HashMap<>();
        itemId.put("space", "2021b.twins"); //TODO: pull it from a resource, make it less hard-coded.
        itemId.put("id", (int) (new Random().nextDouble() * 10000) + "");
        UserBoundary user = new UserBoundary("Random", "Name", "Something", "Else@else.com");
        DigitalItemBoundary item = new DigitalItemBoundary(itemId,
                "RandomItem", "RandomName", false,
                new Date(), user, new HashMap<>(), new HashMap<>());

        return new OperationBoundary((int) (new Random().nextDouble() * 10000) + "",
                "RandomOperation", item, user);

    }
}
