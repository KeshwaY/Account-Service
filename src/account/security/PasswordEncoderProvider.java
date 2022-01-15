package account.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoderProvider {
    @Bean
    public PasswordEncoder getInstance() {
        return new BCryptPasswordEncoder();
    }
}
