package account.auth.admin;

import account.auth.admin.exceptions.AdministratorRoleInterestException;
import account.auth.admin.exceptions.AdministratorRoleRemovalException;
import account.auth.admin.exceptions.OnlyRoleRemovalException;
import account.auth.admin.exceptions.UserRoleNotFoundException;
import account.auth.user.User;

public class AdminServiceValidator {

    private final String adminRole = "ROLE_ADMINISTRATOR";

    public AdminServiceValidator() {
    }

    void validateRoleGrant(User user, String role) throws AdministratorRoleInterestException {
        if (user.getRoles().contains(adminRole) || role.equals(adminRole)) {
            throw new AdministratorRoleInterestException();
        };
    }

    void validateRoleRemove(User user, String role) throws OnlyRoleRemovalException, AdministratorRoleRemovalException, UserRoleNotFoundException {
        if (role.equals(adminRole)) {
            throw new AdministratorRoleRemovalException();
        }  else if (!user.getRoles().contains(role)) {
            throw new UserRoleNotFoundException();
        } else if (user.getRoles().size() == 1) {
            throw new OnlyRoleRemovalException();
        }
    }

    void validateUserRemove(User user) throws AdministratorRoleRemovalException {
        if (user.getRoles().contains(adminRole)) {
            throw new AdministratorRoleRemovalException();
        }
    }
}