package app.controllers;

import app.twins.logic.OperationsService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.boundaries.OperationBoundary;
// import app.boundaries.DigitalItemBoundary;
// import app.boundaries.UserBoundary;
import app.jsonViews.Views;


@RestController
public class OperationsController {

    private OperationsService operationService;

    @Autowired
    public OperationsController(OperationsService operationService) {
        this.operationService = operationService;
    }

    @RequestMapping(
            path = "/twins/operations",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Object InvokeOperationOnItem(@RequestBody OperationBoundary operationBoundary) {
        return operationService.invokeOperation(operationBoundary);
    }

    @RequestMapping(
            path = "/twins/operations/async",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public OperationBoundary aSynchronousOperation(@RequestBody OperationBoundary operationBoundary) {
        return operationService.invokeAsynchronous(operationBoundary);
    }
}
