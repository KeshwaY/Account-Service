package account.auth.user;

import account.utils.BreachedPasswordsSupplier;
import account.auth.dto.NewPasswordPostDto;
import account.auth.dto.UserAuthPostDto;
import account.auth.exceptions.PasswordHasNotChangedException;
import account.auth.exceptions.PasswordIsBreachedException;
import account.auth.exceptions.UserExistException;
import account.auth.user.exceptions.UserDoesNotExistsException;

public class UserDtoValidator {
    private final UserService userService;

    public UserDtoValidator(UserService userService) {
        this.userService = userService;
    }

    void validatePostDto(UserAuthPostDto postDto) {
        if (checkIfUserExistsByEmail(postDto.getEmail())) {
            throw new UserExistException();
        } else if (checkIfPasswordIsBreached(postDto.getPassword())) {
            throw new PasswordIsBreachedException();
        }
    }

    void validateNewPasswordDto(String email, String password, NewPasswordPostDto newPasswordPostDto) throws PasswordIsBreachedException, PasswordHasNotChangedException, UserDoesNotExistsException {
        if (!checkIfUserExistsByEmail(email)) {
            throw new UserDoesNotExistsException();
        } else if (checkIfPasswordIsBreached(newPasswordPostDto.getNewPassword())) {
            throw new PasswordIsBreachedException();
        } else if (checkIfPasswordHasNotChanged(newPasswordPostDto.getNewPassword(), password)) {
            throw new PasswordHasNotChangedException();
        }
    }

    boolean checkIfUserExistsByEmail(String email) {
        return userService.getUserRepository().findByEmail(email).isPresent();
    }

    boolean checkIfPasswordIsBreached(String password) {
        return BreachedPasswordsSupplier.getBreachedPasswords().contains(password);
    }

    boolean checkIfPasswordHasNotChanged(String newPassword, String hashOfOldPassword) {
        return userService.getPasswordEncoder().matches(newPassword, hashOfOldPassword);
    }

}