package account.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class WebSecurityConfigurerImpl extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    public WebSecurityConfigurerImpl(
            UserDetailsService userDetailsService,
            RestAuthenticationEntryPoint restAuthenticationEntryPoint,
            PasswordEncoder passwordEncoder
    ) {
        this.userDetailsService = userDetailsService;
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Main configuration
        http
                .sessionManagement()
                .and()
                .httpBasic()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                .csrf().disable();

        // Admin User access configuration
        http.authorizeRequests()
                .mvcMatchers(HttpMethod.PUT, "api/admin/user/role").hasRole("ADMINISTRATOR")
                .mvcMatchers(HttpMethod.DELETE, "api/admin/user").hasRole("ADMINISTRATOR")
                .mvcMatchers(HttpMethod.GET, "api/admin/user").hasRole("ADMINISTRATOR");

        // Accountant User access configuration
        http.authorizeRequests()
                .mvcMatchers(HttpMethod.POST, "api/acct/payments").hasRole("ACCOUNTANT")
                .mvcMatchers(HttpMethod.PUT, "api/acct/payments").hasRole("ACCOUNTANT");

        // User access configuration
        http.authorizeRequests()
                .mvcMatchers(HttpMethod.POST, "api/auth/changepass").hasAnyRole("USER", "ACCOUNTANT", "ADMINISTRATOR")
                .mvcMatchers(HttpMethod.GET, "api/empl/payment").hasAnyRole("USER", "ACCOUNTANT");

        // Anonymous User access configuration
        http.authorizeRequests()
                .mvcMatchers(HttpMethod.PUT, "api/auth/signup").permitAll();
    }

}
