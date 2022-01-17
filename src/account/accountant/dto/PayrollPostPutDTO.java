package account.accountant.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.*;

public class PayrollPostPutDTO {

    @JsonProperty("employee")
    @NotBlank
    @Email(regexp = ".+@acme\\.com", message = "Unsupported email address!")
    private String email;

    @NotBlank
    @Pattern(regexp = "([0][1-9]|[1][0-2])\\-\\d{4}", message = "Wrong date!")
    private String period;

    @Min(value = 0, message = "Salary must be non negative!")
    @NotNull
    private Long salary;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }
}
