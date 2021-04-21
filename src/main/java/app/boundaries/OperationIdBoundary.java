package app.boundaries;

public class OperationIdBoundary {
    private String space = "";
    private String id = "";

    public OperationIdBoundary() {
        /* Default Constructor */
    }

    public OperationIdBoundary(String space, String id) {
        this.id = id;
        this.space = space;

    }

    public String getSpace() {
        return space;
    }

    public void setSpace(String space) {
        this.space = space;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id + "&" + space;
    }
}
