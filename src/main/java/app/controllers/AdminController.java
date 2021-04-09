package app.controllers;

import app.twins.logic.ItemsService;
import app.twins.logic.OperationsService;
import app.twins.logic.UsersService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.boundaries.OperationBoundary;
import app.boundaries.UserBoundary;
import app.jsonViews.Views;

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
    @JsonView(Views.User.class)
    public UserBoundary[] exportAllUsers(@PathVariable("userSpace") String id, @PathVariable("userEmail") String userEmail) {
        return usersService.getAllUsers(id, userEmail).toArray(new UserBoundary[0]);
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
    @JsonView(Views.Operation.class)
    public OperationBoundary[] exportAllOperations(@PathVariable("userSpace") String id, @PathVariable("userEmail") String userEmail) {
        return operationsService.getAllOperations(id, userEmail).toArray(new OperationBoundary[0]);
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
