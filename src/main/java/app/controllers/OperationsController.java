package app.controllers;

import com.fasterxml.jackson.annotation.JsonView;
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
    @RequestMapping(
        path = "/twins/operations",
        method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Object InvokeOperationOnItem(@RequestBody OperationBoundary operationBoundary) {
        return operationBoundary;
    }

    @JsonView(Views.Operation.class)
    @RequestMapping(
        path = "/twins/operations/async",
        method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public OperationBoundary aSynchronousOperation(@RequestBody OperationBoundary operationBoundary) {
        //TODO: Implement here operationHandler to handle the operations (async)
        return new OperationBoundary(
            "IlNvbWUgbWF5IHRoaW5rIGl0J3MgYSB3YXN0ZSBvZiB0aW1lIG9uIGRlY29kaW5nIHRoaXMsIGJ1dCBub3QgeW91ISA6KSIgDQo=",
            "Operation Type",
            operationBoundary.getItem(),
            operationBoundary.getInvokedBy()
        );
    }
}
