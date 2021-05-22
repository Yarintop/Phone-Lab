package twins.logic;

import twins.boundaries.DigitalItemBoundary;

import java.util.List;

public interface ItemsService {
    public DigitalItemBoundary createItem(String userSpace, String userEmail, DigitalItemBoundary item);

    public DigitalItemBoundary updateItem(String userSpace, String userEmail, String itemSpace, String itemId, DigitalItemBoundary update);

    @Deprecated
    public List<DigitalItemBoundary> getAllItems(String userSpace, String userEmail);

    public List<DigitalItemBoundary> getAllItems(String userSpace, String userEmail, int size, int page);

    public DigitalItemBoundary getSpecificItem(String userSpace, String userEmail, String itemSpace, String itemId);

    public void deleteAllItems(String adminSpace, String adminEmail);
}
