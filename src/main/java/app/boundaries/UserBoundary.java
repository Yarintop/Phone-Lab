package app.boundaries;

import com.fasterxml.jackson.annotation.JsonView;
import java.util.HashMap;
import java.util.Map;

import app.jsonViews.Views;

public class UserBoundary
{
	@JsonView(Views.Public.class)
	private Map<String, String> userId = new HashMap<>(); // This line might change

	@JsonView(Views.User.class)
	private String role = "undefined";

	@JsonView(Views.User.class)
	private String username = "undefined";

	@JsonView(Views.User.class)
    private String avatar = "undefined";
    
    public UserBoundary() { /* Default Constructor */ }

    public UserBoundary(String role, String username, String avatar, String email)
    {
        this.userId = new HashMap<>();
        this.role = role;
        this.username = username;
        this.avatar = avatar;
        
        // Setting userId's details
        userId.put("space", "2021b.twins");
        userId.put("email", email);
        
    }
    
    // Constructor to create a new user directly from a NewUserDetails object
    public UserBoundary(NewUserDetails userDetails)
    {
    	this(userDetails.getRole(), userDetails.getUsername(), 
    			userDetails.getAvatar(), userDetails.getEmail());
    }

	public Map<String, String> getUserId() {
		return userId;
	}

	public void setUserId(Map<String, String> userId) {
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
}
