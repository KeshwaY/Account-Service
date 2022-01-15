package account.auth;

import account.auth.dto.UserAuthGetDto;
import account.auth.dto.UserAuthMapper;
import account.auth.dto.UserAuthPostDto;
import account.auth.exceptions.UserExistException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserAuthMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserAuthMapper mapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
    }

    public UserAuthGetDto signUpUser(UserAuthPostDto postDto) throws UserExistException {
        postDto.setEmail(postDto.getEmail().toLowerCase(Locale.ROOT));
        if (checkIfUserExistsByEmail(postDto.getEmail())) {
           throw new UserExistException();
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

    public boolean checkIfUserExistsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

}
