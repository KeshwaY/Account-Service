package account.auth.user;

import account.auth.dto.NewPasswordPostDto;
import account.auth.dto.PasswordChangedDto;
import account.auth.dto.UserAuthGetDto;
import account.auth.dto.UserAuthPostDto;
import account.auth.exceptions.PasswordHasNotChangedException;
import account.auth.exceptions.PasswordIsBreachedException;
import account.auth.exceptions.UserExistException;
import account.auth.user.exceptions.UserDoesNotExistsException;
import account.auth.user.sequence.SequenceGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDtoValidator userDtoValidator = new UserDtoValidator(this);

    private final SequenceGenerator sequenceGenerator;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, SequenceGenerator sequenceGenerator) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.sequenceGenerator = sequenceGenerator;
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
        user.setJbaId(sequenceGenerator.generateSequence(User.SEQUENCE_NAME));
        user.setName(postDto.getName());
        user.setLastname(postDto.getLastname());
        user.setEmail(postDto.getEmail());
        user.setPassword(postDto.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of(getUserRole()));
        return user;
    }

    private String getUserRole() {
        Optional<User> user = userRepository.findByRolesIsContaining("ROLE_ADMINISTRATOR");
        if (user.isPresent()) {
            return "ROLE_USER";
        }
        return "ROLE_ADMINISTRATOR";
    }

    // Will be replaced with mapper
    private UserAuthGetDto createUserGetDtoFromUser(User user) {
        UserAuthGetDto userAuthGetDto = new UserAuthGetDto();
        userAuthGetDto.setId(user.getJbaId());
        userAuthGetDto.setName(user.getName());
        userAuthGetDto.setLastname(user.getLastname());
        userAuthGetDto.setEmail(user.getEmail());
        userAuthGetDto.setRoles(user.getRoles());
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
