package account.accountant.dto;

import javax.validation.constraints.NotBlank;

public class StatusDTO {

    @NotBlank
    private String status;

    public StatusDTO(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
