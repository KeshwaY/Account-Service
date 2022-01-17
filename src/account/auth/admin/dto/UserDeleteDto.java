package account.auth.admin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserDeleteDto {

    @NotBlank
    @JsonProperty("user")
    @Email(regexp = ".+@acme\\.com", message = "Unsupported email address!")
    private String email;

    private String status;

    public UserDeleteDto() {
    }

    public UserDeleteDto(String email, String status) {
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
