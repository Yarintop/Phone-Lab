package app.twins.logic;

import java.util.List;

import app.boundaries.DigitalItemBoundary;

public interface ItemsService {
    public DigitalItemBoundary createItem(String userSpace, String userEmail, DigitalItemBoundary item);
    public DigitalItemBoundary updateItem(String userSpace, String userEmail, String itemSpace, String itemId, DigitalItemBoundary update);
    public List<DigitalItemBoundary> getAllItems(String userSpace, String userEmail);
    public DigitalItemBoundary getSpecificItem(String userSpace, String userEmail, String itemSpace, String itemId);
    public void deleteAllItems(String adminSpace, String adminEmail);
}
