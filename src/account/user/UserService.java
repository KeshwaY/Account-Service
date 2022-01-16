package account.user;

import account.auth.BreachedPasswordsSupplier;
import account.auth.dto.*;
import account.auth.exceptions.PasswordIsBreachedException;
import account.auth.exceptions.PasswordHasNotChangedException;
import account.auth.exceptions.UserExistException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class UserService {

    private final UserRepository userRepository;
    //private final UserAuthMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        //this.mapper = mapper;
    }

    public UserAuthGetDto signUpUser(UserAuthPostDto postDto) throws UserExistException, PasswordIsBreachedException {
        postDto.setEmail(postDto.getEmail().toLowerCase(Locale.ROOT));
        if (checkIfUserExistsByEmail(postDto.getEmail())) {
           throw new UserExistException();
        } else if (checkIfPasswordIsBreached(postDto.getPassword())) {
            throw new PasswordIsBreachedException();
        }
        //User user = mapper.userAuthPostDtoToUser(postDto);
        User user = new User();
        user.setName(postDto.getName());
        user.setLastname(postDto.getLastname());
        user.setEmail(postDto.getEmail());
        user.setPassword(postDto.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        userRepository.save(user);
        //return mapper.userToUserAuthGetDto(userRepository.save(user));
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
    ) throws PasswordIsBreachedException, PasswordHasNotChangedException {
        if (checkIfPasswordIsBreached(newPasswordPostDto.getNewPassword())) {
            throw new PasswordIsBreachedException();
        } else if (checkIfPasswordHasNotChanged(newPasswordPostDto.getNewPassword(), password)) {
            throw new PasswordHasNotChangedException();
        }
        String newHashedPassword = passwordEncoder.encode(newPasswordPostDto.getNewPassword());
        userRepository.findUserByEmailAndUpdatePassword(email, newHashedPassword);
        return new PasswordChangedDto(email, "The password has been updated successfully");
    }


    public boolean checkIfUserExistsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean checkIfPasswordIsBreached(String password) {
        return BreachedPasswordsSupplier.getBreachedPasswords().contains(password);
    }

    public boolean checkIfPasswordHasNotChanged(String newPassword, String hashOfOldPassword) {
        return passwordEncoder.matches(newPassword, hashOfOldPassword);
    }

}
