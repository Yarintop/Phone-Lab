package demo;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OperationsController
{
	@RequestMapping(
		path = "/twins/operations",
		method = RequestMethod.POST,
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public Object InvokeOperationOnItem(@RequestBody OperationBoundary operationBoundary)
	{
		return "Yese;";
	}


	@RequestMapping(
		path = "/twins/operations/async",
		method = RequestMethod.POST,
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public OperationBoundary aSynchronousOperation(@RequestBody OperationBoundary operationBoundary)
	{
		return new OperationBoundary();
	}
}
