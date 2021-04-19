package app.boundaries;

import app.jsonViews.Views;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.Objects;

public class UserIdBoundary {

    @JsonView(Views.User.class)
    private String space;

    @JsonView(Views.User.class)
    private String email;

    public UserIdBoundary() {
        /* Default Constructor */
    }

    public UserIdBoundary(String space, String email) {
        this.email = email;
        this.space = space;

    }

    public String getSpace() {
        return space;
    }

    public String getEmail() {
        return email;
    }

    public void setSpace(String space) {
        this.space = space;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserIdBoundary that = (UserIdBoundary) o;
        return space.equals(that.space) && email.equals(that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(space, email);
    }
}
