package twins.logic;

import twins.boundaries.DigitalItemBoundary;
import twins.boundaries.ItemIdBoundary;

import java.util.List;

public interface UpdatedItemsService extends ItemsService {
    public void bindChild(String userSpace, String userEmail,
                          String itemSpace, String itemId, ItemIdBoundary child);

    public List<DigitalItemBoundary> getAllChildren(String userSpace, String userEmail,
                                                    String itemSpace, String itemId);

    public List<DigitalItemBoundary> getParents(String userSpace, String userEmail,
                                                String itemSpace, String itemId);

    public List<DigitalItemBoundary> getAllItems(String userSpace, String userEmail, int size, int page);


}
