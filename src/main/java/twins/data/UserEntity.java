package twins.data;

import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.persistence.Id;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "USERS")
public class UserEntity implements Entity {

    private String id;
    private String email;
    private UserRole role;
    private String username;
    private String avatar;

    public UserEntity() { /* Default Constructor */ }

    public String getEmail() {
        return email;
    }

    @Id
    @MongoId
    public String getId() {
        return id;
    }

    public void setId(String userId) {
        this.id = userId;
    }

    public void setUserId(String email, String space) {
        this.id = email + "&" + space;
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
