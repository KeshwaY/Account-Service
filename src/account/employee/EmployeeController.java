package account.employee;

import account.user.User;
import account.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("api/empl")
public class EmployeeController {

    private final UserRepository userRepository;

    public EmployeeController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/payment")
    public ResponseEntity<JBResponse> getPayment(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        User user = userRepository.findByEmail(userDetails.getUsername()).get();
        return new ResponseEntity<>(new JBResponse(user), HttpStatus.OK);
    }

    private final static class JBResponse {
        private static long id;

        private String name;
        private String lastname;
        private String email;

        public JBResponse(User authGetDto) {
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
