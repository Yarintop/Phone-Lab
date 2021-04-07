package app.twins.logic;

import app.boundaries.OperationBoundary;

import java.util.List;

public interface OperationsService {
    Object invokeOperation(OperationBoundary operation);

    OperationBoundary invokeAsynchronous(OperationBoundary operation);

    List<OperationBoundary> getAllOperations(String adminSpace, String adminEmail);

    void deleteAllOperations(String adminSpace, String adminEmail);
}
