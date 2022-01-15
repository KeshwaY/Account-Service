package account.auth.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserAuthGetDto {

    @NotBlank
    private String name;

    @NotBlank
    private String lastname;

    @NotBlank
    @Email(regexp = ".+@acme\\.com")
    private String email;

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
}
