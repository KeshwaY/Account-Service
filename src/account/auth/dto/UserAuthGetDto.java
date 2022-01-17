package account.auth.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public class UserAuthGetDto {

    @NotEmpty
    private int id;

    @NotBlank
    private String name;

    @NotBlank
    private String lastname;

    @NotBlank
    @Email(regexp = ".+@acme\\.com")
    private String email;

    private List<String> roles;

    public UserAuthGetDto() {
    }

    public UserAuthGetDto(int id, String name, String lastname, String email, List<String> roles) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.roles = roles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

}
