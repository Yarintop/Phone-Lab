package twins.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import twins.boundaries.OperationBoundary;
import twins.boundaries.UserBoundary;
import twins.logic.ItemsService;
import twins.logic.OperationsService;
import twins.logic.UsersService;

@CrossOrigin //Because we don't value security, everyone is welcomed
@RestController
public class AdminController {

    private UsersService usersService;
    private ItemsService itemsService;
    private OperationsService operationsService;

    /**
     * In this constructor, all the needed services will be injected to with Spring (User, Item, Operation)
     *
     * @param usersService      - the user service that would be injected
     * @param itemsService      - the user item that would be injected
     * @param operationsService - the operation service that would be injected
     */
    @Autowired
    public AdminController(UsersService usersService, ItemsService itemsService, OperationsService operationsService) {
        this.usersService = usersService;
        this.itemsService = itemsService;
        this.operationsService = operationsService;
    }

    /**
     * Returns all users in the requested space
     *
     * @param id        - Requested user space
     * @param userEmail - Invoker's email
     * @return Array of {@link UserBoundary}
     */
    @RequestMapping(
            path = "/twins/admin/users/{userSpace}/{userEmail}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public UserBoundary[] exportAllUsers(
            @PathVariable("userSpace") String id,
            @PathVariable("userEmail") String userEmail,
            @RequestParam(name="size", required=false, defaultValue="20") int size,
            @RequestParam(name="page", required=false, defaultValue="0") int page) {
        return usersService
                .getAllUsers(id, userEmail, size, page)
                .toArray(new UserBoundary[0]);
    }

    /**
     * Returns all operations in the requested space
     *
     * @param id        - Requested user space
     * @param userEmail - Invoker's email
     * @return Array of {@link OperationBoundary}
     */
    @RequestMapping(
            path = "/twins/admin/operations/{userSpace}/{userEmail}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public OperationBoundary[] exportAllOperations(
            @PathVariable("userSpace") String id,
            @PathVariable("userEmail") String userEmail,
            @RequestParam(name="size", required=false, defaultValue="20") int size,
            @RequestParam(name="page", required=false, defaultValue="0") int page) {
        return operationsService
                .getAllOperations(id, userEmail, size, page)
                .toArray(new OperationBoundary[0]);
    }

    /**
     * Deletes all users in the requested space
     *
     * @param id        - Requested user space
     * @param userEmail - Invoker's email
     */
    @RequestMapping(
            path = "/twins/admin/users/{userSpace}/{userEmail}",
            method = RequestMethod.DELETE
    )
    public void deleteAllUsers(@PathVariable("userSpace") String id, @PathVariable("userEmail") String userEmail) {
        usersService.deleteAllUsers(id, userEmail);
    }


    /**
     * Deletes all items in the requested space
     *
     * @param id        - Requested user space
     * @param userEmail - Invoker's email
     */
    @RequestMapping(
            path = "/twins/admin/items/{userSpace}/{userEmail}",
            method = RequestMethod.DELETE
    )
    public void deleteAllItems(@PathVariable("userSpace") String id, @PathVariable("userEmail") String userEmail) {
        itemsService.deleteAllItems(id, userEmail);
    }


    /**
     * Deletes all operations in the requested space
     *
     * @param id        - Requested user space
     * @param userEmail - Invoker's email
     */
    @RequestMapping(
            path = "/twins/admin/operations/{userSpace}/{userEmail}",
            method = RequestMethod.DELETE
    )
    public void deleteAllOperations(@PathVariable("userSpace") String id, @PathVariable("userEmail") String userEmail) {
        operationsService.deleteAllOperations(id, userEmail);
    }
}
