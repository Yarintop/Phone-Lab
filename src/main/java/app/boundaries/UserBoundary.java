package app.boundaries;

import com.fasterxml.jackson.annotation.JsonView;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import app.jsonViews.Views;

public class UserBoundary implements Boundary {

    private UserIdBoundary userId;

    private String role;

    private String username;

    private String avatar;

    public UserBoundary() { /* Default Constructor */ }

    // @Value("${spring.application.name:2021b.notdef}")
    public UserBoundary(String role, String username, String avatar, String email, String space) {
        this.userId = new UserIdBoundary();
        this.role = role.toUpperCase();
        this.username = username;
        this.avatar = avatar;

        // Setting userId's details
        userId.setSpace(space);
        userId.setEmail(email);
    }

    public UserBoundary(String email, String space) {
        this.userId = new UserIdBoundary();

        // Setting userId's details
        userId.setSpace(space);
        userId.setEmail(email);
    }

    // Constructor to create a new user directly from a NewUserDetails object
    public UserBoundary(NewUserDetails userDetails, String space) {
        this(userDetails.getRole(), userDetails.getUsername(),
                userDetails.getAvatar(), userDetails.getEmail(), space);
    }

    public UserIdBoundary getUserId() {
        return userId;
    }

    public void setSpace(String userSpace) {
        this.userId.setSpace(userSpace);
    }

    public void setEmail(String userEmail) {
        this.userId.setEmail(userEmail);
    }

    public void setUserId(UserIdBoundary userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserBoundary that = (UserBoundary) o;
        return userId.equals(that.userId) && role.equals(that.role) && username.equals(that.username) && avatar.equals(that.avatar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, role, username, avatar);
    }

}


