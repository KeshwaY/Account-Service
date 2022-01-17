package account.employee;

import account.employee.dto.EmployeeSalaryGetDto;
import account.accountant.exceptions.UserDoesNotExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;
import java.util.List;

@Validated
@RestController
@RequestMapping("api/empl")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(value = "/payment", params = {"period"})
    public ResponseEntity<EmployeeSalaryGetDto> getPayment(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("period") @Pattern(regexp = "([0][1-9]|[1][0-2])\\-\\d{4}", message = "Wrong date!") String period
    ) throws UserDoesNotExistsException {
        EmployeeSalaryGetDto employeeSalaryGetDto = employeeService.findPayment(userDetails.getUsername(), period);
        return new ResponseEntity<>(employeeSalaryGetDto, HttpStatus.OK);
    }

    @GetMapping("/payment")
    public ResponseEntity<List<EmployeeSalaryGetDto>> getPayments(
            @AuthenticationPrincipal UserDetails userDetails
    ) throws UserDoesNotExistsException {
        List<EmployeeSalaryGetDto> employeeSalaryGetDtos = employeeService.findPayments(userDetails.getUsername());
        return new ResponseEntity<>(employeeSalaryGetDtos, HttpStatus.OK);
    }

}
