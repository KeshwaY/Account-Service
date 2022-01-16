package account.auth.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class PasswordChangedDto {

    @NotBlank
    @Email(regexp = ".+@acme\\.com")
    private String email;

    @NotBlank
    private String status;

    public PasswordChangedDto(String email, String status) {
        this.email = email;
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
