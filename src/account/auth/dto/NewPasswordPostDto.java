package account.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class NewPasswordPostDto {

    @JsonProperty("new_password")
    @NotBlank
    @Size(min = 12, message = "Password length must be 12 chars minimum!")
    private String newPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
