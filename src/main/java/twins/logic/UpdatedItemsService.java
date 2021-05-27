package twins.logic;

import java.util.List;

import twins.boundaries.DigitalItemBoundary;
import twins.boundaries.ItemIdBoundary;


public interface UpdatedItemsService extends ItemsService {
    public List<DigitalItemBoundary> getAllItems(String userSpace, String userEmail, int size, int page, String type);

    public void bindChild(String userSpace, String userEmail,
                          String itemSpace, String itemId, ItemIdBoundary child);

    @Deprecated
    public List<DigitalItemBoundary> getAllChildren(String userSpace, String userEmail,
                                                    String itemSpace, String itemId);

    @Deprecated
    public List<DigitalItemBoundary> getAllParents(String userSpace, String userEmail,
                                                String itemSpace, String itemId);
    
    public List<DigitalItemBoundary> getAllChildren(String userSpace, String userEmail,
                                                    String itemSpace, String itemId,
                                                    int size, int page);

    public List<DigitalItemBoundary> getAllParents(String userSpace, String userEmail,
                                                String itemSpace, String itemId,
                                                int size, int page);


}
