package account.auth;


import account.auth.dto.UserAuthGetDto;
import account.auth.dto.UserAuthPostDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/auth/signup")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping()
    public ResponseEntity<UserAuthGetDto> signUser(@RequestBody @Valid UserAuthPostDto userAuthPostDto) {
        return new ResponseEntity<>(authService.signUser(userAuthPostDto), HttpStatus.OK);
    }

}
