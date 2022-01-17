package account.auth.admin.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Can't remove ADMINISTRATOR role!")
public class AdministratorRoleRemovalException extends Exception {
}
