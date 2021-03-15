package demo;

import com.fasterxml.jackson.annotation.JsonView;
import demo.jsonViews.Views;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class OperationsController {

    @JsonView(Views.Operation.class)
    @RequestMapping(
            path = "/twins/operations",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Object InvokeOperationOnItem(@RequestBody OperationBoundary operationBoundary) {
//        return "Yese of json object";
        return  operationBoundary;
    }


    @RequestMapping(
            path = "/twins/operations/async",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public OperationBoundary aSynchronousOperation(@RequestBody OperationBoundary operationBoundary) {
////        FOR REFERENCE FROM DIGITAL ITEM CONTROLLER , TODA BAUOMM
//        Map<String,String> userId = new HashMap<>();
//        Map<String,Map<String,String>> createdBy = new HashMap<>();
//        userId.put("space", userSpace);
//        userId.put("email", userEmail);
//        createdBy.put("userId", userId);



        return new OperationBoundary(null, "Operation Type", new DigitalItemBoundary(), new UserBoundary());
    }
}
