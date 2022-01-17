package account.accountant.employee;

import account.accountant.employee.dto.PayrollGetDto;
import account.accountant.employee.exceptions.CouldNotFindPayrollsException;
import account.accountant.exceptions.PayrollDoesNotExistException;
import account.auth.user.exceptions.UserDoesNotExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Pattern;
import java.util.ArrayList;
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
    public ResponseEntity<PayrollGetDto> getPayment(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("period") @Pattern(regexp = "([0][1-9]|[1][0-2])\\-\\d{4}", message = "Wrong date!") String period
    ) throws UserDoesNotExistsException, PayrollDoesNotExistException {
        PayrollGetDto employeeSalaryGetDto = null;
        // Will be removed after course
        try {
            employeeSalaryGetDto = employeeService.findPayment(userDetails.getUsername(), period);
        } catch (PayrollDoesNotExistException e) {}
        return new ResponseEntity<>(employeeSalaryGetDto, HttpStatus.OK);
    }

    @GetMapping("/payment")
    public ResponseEntity<List<PayrollGetDto>> getPayments(
            @AuthenticationPrincipal UserDetails userDetails
    ) throws UserDoesNotExistsException, CouldNotFindPayrollsException {
        List<PayrollGetDto> employeeSalaryGetDtos = new ArrayList<>();
        // Will be removed after course
        try {
            employeeSalaryGetDtos = employeeService.findPayments(userDetails.getUsername());
        } catch (CouldNotFindPayrollsException e) {}
        return new ResponseEntity<>(employeeSalaryGetDtos, HttpStatus.OK);
    }

}
