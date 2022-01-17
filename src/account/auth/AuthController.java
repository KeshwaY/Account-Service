package account.auth;


import account.auth.dto.UserAuthPostDto;
import account.auth.exceptions.PasswordIsBreachedException;
import account.auth.dto.NewPasswordPostDto;
import account.auth.dto.PasswordChangedDto;
import account.auth.dto.UserAuthGetDto;
import account.auth.exceptions.PasswordHasNotChangedException;
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
    public ResponseEntity<JBResponse> signUpUser(@RequestBody @Valid UserAuthPostDto userAuthPostDto) {
        return new ResponseEntity<>(new JBResponse(userService.signUpUser(userAuthPostDto)), HttpStatus.OK);
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

    // Will be replaced
    private final static class JBResponse {
        private static long id;

        private String name;
        private String lastname;
        private String email;

        public JBResponse(UserAuthGetDto authGetDto) {
            JBResponse.id++;
            this.name = authGetDto.getName();
            this.lastname = authGetDto.getLastname();
            this.email = authGetDto.getEmail();
        }

        public long getId() {
            return JBResponse.id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

}
