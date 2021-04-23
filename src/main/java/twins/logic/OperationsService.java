package twins.logic;

import twins.boundaries.OperationBoundary;

import java.util.List;

public interface OperationsService {
    public Object invokeOperation(OperationBoundary operation);
    public OperationBoundary invokeAsynchronous(OperationBoundary operation);
    public List<OperationBoundary> getAllOperations(String adminSpace, String adminEmail);
    public void deleteAllOperations(String adminSpace, String adminEmail);
}
