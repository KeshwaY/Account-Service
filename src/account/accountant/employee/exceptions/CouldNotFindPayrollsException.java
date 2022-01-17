package account.accountant.employee.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Could not find payrolls for the user!")
public class CouldNotFindPayrollsException extends Exception{
}
