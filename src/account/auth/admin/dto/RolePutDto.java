package account.auth.admin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class RolePutDto {

    @NotBlank
    @JsonProperty("user")
    private String email;

    @NotBlank
    private String role;

    @NotBlank
    @Pattern(regexp = "^GRANT$|^REMOVE$")
    private String operation;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

}
