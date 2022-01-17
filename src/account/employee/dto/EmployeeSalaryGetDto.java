package account.employee.dto;

import javax.validation.constraints.NotBlank;

public class EmployeeSalaryGetDto {

    @NotBlank
    private String name;
    @NotBlank
    private String lastname;
    @NotBlank
    private String period;
    @NotBlank
    private String salary;

    public EmployeeSalaryGetDto() {}

    public EmployeeSalaryGetDto(String name, String lastname, String period, String salary) {
        this.name = name;
        this.lastname = lastname;
        this.period = period;
        this.salary = salary;
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

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }
}
