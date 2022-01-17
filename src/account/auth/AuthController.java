package account.auth;


import account.auth.dto.NewPasswordPostDto;
import account.auth.dto.PasswordChangedDto;
import account.auth.dto.UserAuthGetDto;
import account.auth.dto.UserAuthPostDto;
import account.auth.exceptions.PasswordHasNotChangedException;
import account.auth.exceptions.PasswordIsBreachedException;
import account.auth.user.UserService;
import account.auth.user.exceptions.UserDoesNotExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserAuthGetDto> signUpUser(@RequestBody @Valid UserAuthPostDto userAuthPostDto) {
        return new ResponseEntity<>(userService.signUpUser(userAuthPostDto), HttpStatus.OK);
    }

    @PostMapping("/changepass")
    public ResponseEntity<PasswordChangedDto> changeUserPassword(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody @Valid NewPasswordPostDto newPasswordPostDto
    ) throws PasswordIsBreachedException, UserDoesNotExistsException, PasswordHasNotChangedException {
        String userEmail = userDetails.getUsername();
        String userPassword = userDetails.getPassword();
        return new ResponseEntity<>(userService.changeUserPassword(userEmail, userPassword, newPasswordPostDto), HttpStatus.OK);
    }

}
