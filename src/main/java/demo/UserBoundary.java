package demo;

public class UserBoundary
{
    private String firstName;
    private String lastName;
    private String userEmail;
    private long userSpace;

    public UserBoundary() { }

    public UserBoundary(String firstName, String lastName, String userEmail, long userSpace)
    {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.userEmail = userEmail;
        this.userSpace = userSpace;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserEmail() {
        return this.userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public long getUserSpace() {
        return this.userSpace;
    }

    public void setUserSpace(long userSpace) {
        this.userSpace = userSpace;
    }

}
