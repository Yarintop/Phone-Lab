package app.twins.logic;

import app.boundaries.DigitalItemBoundary;
import app.boundaries.ItemIdBoundary;

import java.util.List;

public interface UpdatedItemsService extends ItemsService{
    public void bindChild(String userSpace, String userEmail,
                          String itemSpace, String itemId, ItemIdBoundary child);
    public List<DigitalItemBoundary> getAllChildren(String userSpace, String userEmail,
                                                    String itemSpace, String itemId);
    public List<DigitalItemBoundary> getParents(String userSpace, String userEmail,
                                                String itemSpace, String itemId);

}
