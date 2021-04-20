package app.boundaries;

import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperationIdBoundary that = (OperationIdBoundary) o;
        return Objects.equals(space, that.space) && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(space, id);
    }
}
