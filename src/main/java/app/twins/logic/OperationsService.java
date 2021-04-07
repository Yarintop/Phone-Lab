package app.twins.logic;

import app.boundaries.OperationBoundary;

public interface OperationsService {
    public Object invokeOperation(OperationBoundary operation);
    public OperationBoundary invokeAsynchronous(OperationBoundary operation);
    public OperationBoundary[] getAllOperations(String adminSpace, String adminEmail);
    public void deleteAllOperations(String adminSpace, String adminEmail);
}
