package account.auth.user;

import account.auth.dto.NewPasswordPostDto;
import account.auth.dto.PasswordChangedDto;
import account.auth.dto.UserAuthGetDto;
import account.auth.dto.UserAuthPostDto;
import account.auth.exceptions.PasswordHasNotChangedException;
import account.auth.exceptions.PasswordIsBreachedException;
import account.auth.exceptions.UserExistException;
import account.auth.user.exceptions.UserDoesNotExistsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDtoValidator userDtoValidator = new UserDtoValidator(this);

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public UserAuthGetDto signUpUser(UserAuthPostDto postDto) throws UserExistException, PasswordIsBreachedException {
        postDto.setEmail(postDto.getEmail().toLowerCase(Locale.ROOT));
        userDtoValidator.validatePostDto(postDto);
        User user = createUserFromDto(postDto);
        userRepository.save(user);
        return createUserGetDtoFromUser(user);
    }

    // Will be replaced with mapper
    private User createUserFromDto(UserAuthPostDto postDto) {
        User user = new User();
        user.setName(postDto.getName());
        user.setLastname(postDto.getLastname());
        user.setEmail(postDto.getEmail());
        user.setPassword(postDto.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        return user;
    }

    // Will be replaced with mapper
    private UserAuthGetDto createUserGetDtoFromUser(User user) {
        UserAuthGetDto userAuthGetDto = new UserAuthGetDto();
        userAuthGetDto.setName(user.getName());
        userAuthGetDto.setLastname(user.getLastname());
        userAuthGetDto.setEmail(user.getEmail());
        return userAuthGetDto;
    }

    public PasswordChangedDto changeUserPassword(
            String email,
            String password,
            NewPasswordPostDto newPasswordPostDto
    ) throws PasswordIsBreachedException, PasswordHasNotChangedException, UserDoesNotExistsException {
        userDtoValidator.validateNewPasswordDto(email, password, newPasswordPostDto);
        String newHashedPassword = passwordEncoder.encode(newPasswordPostDto.getNewPassword());
        userRepository.findUserByEmailAndUpdatePassword(email, newHashedPassword);
        return new PasswordChangedDto(email, "The password has been updated successfully");
    }

}
