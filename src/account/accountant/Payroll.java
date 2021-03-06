package account.accountant;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Document
public class Payroll {

    @Id private String id;

    @Field("employee_id")
    @NotNull
    private ObjectId employeeId;

    @NotBlank
    private LocalDateTime period;

    @NotNull
    private Long salary;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ObjectId getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(ObjectId employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDateTime getPeriod() {
        return period;
    }

    public void setPeriod(LocalDateTime period) {
        this.period = period;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }
}
