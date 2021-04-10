package app.twins.logic;

import java.util.List;

import app.boundaries.DigitalItemBoundary;

public interface ItemsService {
    DigitalItemBoundary createItem(String userSpace, String userEmail, DigitalItemBoundary item);
    DigitalItemBoundary updateItem(String userSpace, String userEmail, String itemSpace, String itemId, DigitalItemBoundary update);
    List<DigitalItemBoundary> getAllItems(String userSpace, String userEmail);
    DigitalItemBoundary getSpecificItem(String userSpace, String userEmail, String itemSpace, String itemId);
    void deleteAllItems(String adminSpace, String adminEmail);
}
