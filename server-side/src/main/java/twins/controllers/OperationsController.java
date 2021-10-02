package twins.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import twins.boundaries.OperationBoundary;
import twins.logic.OperationsService;

@CrossOrigin //Because we don't value security, everyone is welcomed
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
