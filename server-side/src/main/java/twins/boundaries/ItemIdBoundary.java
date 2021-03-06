package twins.boundaries;

public class ItemIdBoundary {

    private String space = null;
    private String id = null;


    /**
     * This is the default constructor, all the fields will be the default values
     */
    public ItemIdBoundary() { /* Default Constructor */ }

    /**
     * This is constructor, will create an operation with the given parameters
     *
     * @param space - space of the item
     * @param id    - an ID for the item
     */
    public ItemIdBoundary(String space, String id) {
        this();
        this.space = space;
        this.id = id;
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
        return this.id + "&" + this.space;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ItemIdBoundary other = (ItemIdBoundary) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (space == null) {
            return other.space == null;
        } else return space.equals(other.space);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((space == null) ? 0 : space.hashCode());
        return result;
    }
}
