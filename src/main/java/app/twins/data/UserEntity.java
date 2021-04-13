package app.twins.data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name="USERS")
public class UserEntity implements Entity {
	
	private String space;
	
	private String email;

	private UserRole role;

	private String username;

    private String avatar;

    
    public UserEntity() { /* Default Constructor */ }

	@Column(name="DOMAIN")
	public String getSpace() {
		return space;
	}

	public void setSpace(String space) {
		this.space = space;
	}
	@Id
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
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
