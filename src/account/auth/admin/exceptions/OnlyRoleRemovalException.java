package account.auth.admin.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "The user must have at least one role!")
public class OnlyRoleRemovalException extends Exception {
}
