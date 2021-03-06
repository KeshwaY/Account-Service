package account.auth.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Document
public class User {

    @Transient
    public static final String SEQUENCE_NAME = "users_sequence";

    @Id
    private String id;

    @NotEmpty
    private int jbaId;

    @NotBlank
    private String name;

    @NotBlank
    private String lastname;

    @NotBlank
    @Email(regexp = ".+@acme\\.com")
    private String email;

    @NotBlank
    private String password;

    private List<String> roles;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getJbaId() {
        return jbaId;
    }

    public void setJbaId(int jbaId) {
        this.jbaId = jbaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

}
