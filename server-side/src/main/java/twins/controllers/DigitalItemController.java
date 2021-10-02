package twins.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import twins.boundaries.DigitalItemBoundary;
import twins.boundaries.ItemIdBoundary;
import twins.logic.UpdatedItemsService;

import java.util.List;

@CrossOrigin //Because we don't value security, everyone is welcomed
@RestController
public class DigitalItemController {
    private UpdatedItemsService itemLogic;


    @Autowired
    public DigitalItemController(UpdatedItemsService itemLogic) {
        super();
        this.itemLogic = itemLogic;
    }

    @RequestMapping(
            path = "/twins/items/{userSpace}/{userEmail}",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public DigitalItemBoundary createItem(
            @PathVariable("userSpace") String userSpace,
            @PathVariable("userEmail") String userEmail,
            @RequestBody DigitalItemBoundary newItem) {
        return itemLogic.createItem(userSpace, userEmail, newItem);
    }

    @RequestMapping(
            path = "/twins/items/{userSpace}/{userEmail}/{itemSpace}/{itemId}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public void updateItem(
            @PathVariable("userSpace") String userSpace,
            @PathVariable("userEmail") String userEmail,
            @PathVariable("itemSpace") String itemSpace,
            @PathVariable("itemId") String itemId,
            @RequestBody DigitalItemBoundary update) {
        this.itemLogic
                .updateItem(userSpace, userEmail, itemSpace, itemId, update);
    }

    @RequestMapping(
            path = "/twins/items/{userSpace}/{userEmail}/{itemSpace}/{itemId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public DigitalItemBoundary getSpecificItem(
            @PathVariable("userSpace") String userSpace,
            @PathVariable("userEmail") String userEmail,
            @PathVariable("itemSpace") String itemSpace,
            @PathVariable("itemId") String itemId) {
        return this.itemLogic
                .getSpecificItem(userSpace, userEmail, itemSpace, itemId);
    }

    @RequestMapping(
            path = "/twins/items/{userSpace}/{userEmail}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )

    public DigitalItemBoundary[] getAllItems(
            // public DigitalItemBoundary[] getAllItems(
            @PathVariable("userSpace") String userSpace,
            @PathVariable("userEmail") String userEmail,
            @RequestParam(name="size", required=false, defaultValue="20") int size,
            @RequestParam(name="page", required=false, defaultValue="0") int page,
            @RequestParam(name="type", required=false, defaultValue="") String type) {
        List<DigitalItemBoundary> allItems =
                this.itemLogic.getAllItems(userSpace, userEmail, size, page, type);

        return allItems.toArray(new DigitalItemBoundary[0]);
    }
    //NEW HERE BELOW

    @RequestMapping(
            path = "/twins/items/{userSpace}/{userEmail}/{itemSpace}/{itemId}/children",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public void bindChild(
            @PathVariable("userSpace") String userSpace,
            @PathVariable("userEmail") String userEmail,
            @PathVariable("itemSpace") String itemSpace,
            @PathVariable("itemId") String itemId,
            @RequestBody ItemIdBoundary child) {

        this.itemLogic.bindChild(userSpace, userEmail, itemSpace, itemId, child);
    }

    @RequestMapping(
            path = "/twins/items/{userSpace}/{userEmail}/{itemSpace}/{itemId}/children",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public DigitalItemBoundary[] getAllChildren(
            @PathVariable("userSpace") String userSpace,
            @PathVariable("userEmail") String userEmail,
            @PathVariable("itemSpace") String itemSpace,
            @PathVariable("itemId") String itemId,
            @RequestParam(name="size", required=false, defaultValue="20") int size,
            @RequestParam(name="page", required=false, defaultValue="0") int page) {
        List<DigitalItemBoundary> allChildren = this.itemLogic.getAllChildren(userSpace, userEmail, itemSpace, itemId, size, page);
        return allChildren.toArray(new DigitalItemBoundary[0]);
    }

    @RequestMapping(
            path = "/twins/items/{userSpace}/{userEmail}/{itemSpace}/{itemId}/parents",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public DigitalItemBoundary[] getAllParents(
            @PathVariable("userSpace") String userSpace,
            @PathVariable("userEmail") String userEmail,
            @PathVariable("itemSpace") String itemSpace,
            @PathVariable("itemId") String itemId,
            @RequestParam(name="size", required=false, defaultValue="20") int size,
            @RequestParam(name="page", required=false, defaultValue="0") int page) {
        List<DigitalItemBoundary> allItems =
                this.itemLogic.getAllParents(userSpace, userEmail, itemSpace, itemId, size, page);

        return allItems.toArray(new DigitalItemBoundary[0]);
    }
}
