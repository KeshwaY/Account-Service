package account.accountant.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Payroll does not exist!")
public class PayrollDoesNotExistException extends Exception {
}
