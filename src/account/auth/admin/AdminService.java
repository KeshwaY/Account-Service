package account.auth.admin;

import account.auth.admin.dto.RolePutDto;
import account.auth.admin.dto.UserDeleteDto;
import account.auth.admin.exceptions.*;
import account.auth.dto.UserAuthGetDto;
import account.auth.user.User;
import account.auth.user.UserRepository;
import account.auth.user.exceptions.UserDoesNotExistsException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final AdminServiceValidator adminServiceValidator = new AdminServiceValidator();

    private final List<String> validRoles = List.of("ACCOUNTANT", "USER", "ADMINISTRATOR");

    public AdminService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserAuthGetDto setUserRole(RolePutDto rolePutDto) throws UserDoesNotExistsException, AdministratorRoleRemovalException, UserRoleNotFoundException, OnlyRoleRemovalException, AdministratorRoleInterestException, RoleNotFoundException {
        if (!validRoles.contains(rolePutDto.getRole())) {
            throw new RoleNotFoundException();
        }
        rolePutDto.setEmail(rolePutDto.getEmail().toLowerCase(Locale.ROOT));
        User user = userRepository.findByEmail(rolePutDto.getEmail()).orElseThrow(UserDoesNotExistsException::new);
        UserAuthGetDto response;
        if (rolePutDto.getOperation().equals("GRANT")) {
            response = handleRoleGrant(user, rolePutDto.getRole());
        } else {
            response = handleRoleRemove(user, rolePutDto.getRole());
        }
        userRepository.save(user);
        return response;
    }

    private UserAuthGetDto handleRoleGrant(User user, String role) throws AdministratorRoleInterestException {
        role = "ROLE_" + role;
        adminServiceValidator.validateRoleGrant(user, role);
        if (!user.getRoles().contains(role)){
            user.getRoles().add(role);
            Collections.sort(user.getRoles());
        }
        return createUserAuthGetDto(user);
    }

    private UserAuthGetDto handleRoleRemove(User user, String role) throws AdministratorRoleRemovalException, UserRoleNotFoundException, OnlyRoleRemovalException {
        role = "ROLE_" + role;
        adminServiceValidator.validateRoleRemove(user, role);
        user.getRoles().remove(role);
        return createUserAuthGetDto(user);
    }

    // Will be replaced with mapper
    private UserAuthGetDto createUserAuthGetDto(User user) {
        return new UserAuthGetDto(
                user.getJbaId(),
                user.getName(),
                user.getLastname(),
                user.getEmail(),
                user.getRoles()
        );
    }

    public UserDeleteDto deleteUser(String userEmail) throws UserDoesNotExistsException, AdministratorRoleRemovalException {
        userEmail = userEmail.toLowerCase(Locale.ROOT);
        User user = userRepository.findByEmail(userEmail).orElseThrow(UserDoesNotExistsException::new);
        adminServiceValidator.validateUserRemove(user);
        userRepository.delete(user);
        return new UserDeleteDto(userEmail, "Deleted successfully!");
    }

    public List<UserAuthGetDto> getAllUsers() {
        List<User> users = userRepository.getAllASC();
        return users.stream()
                .map(this::createUserAuthGetDto)
                .collect(Collectors.toUnmodifiableList());
    }

}
