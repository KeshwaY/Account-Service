package account.auth.admin;

import account.auth.admin.dto.RolePutDto;
import account.auth.admin.dto.UserDeleteDto;
import account.auth.admin.exceptions.*;
import account.auth.dto.UserAuthGetDto;
import account.auth.user.exceptions.UserDoesNotExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PutMapping("user/role")
    public ResponseEntity<UserAuthGetDto> setRoles(
            @RequestBody @Valid RolePutDto rolePutDto
    ) throws AdministratorRoleRemovalException,
            UserRoleNotFoundException,
            UserDoesNotExistsException,
            OnlyRoleRemovalException,
            AdministratorRoleInterestException,
            RoleNotFoundException {
        UserAuthGetDto rolePutResponseDto = adminService.setUserRole(rolePutDto);
        return new ResponseEntity<>(rolePutResponseDto, HttpStatus.OK);
    }

    // Will be removed
    @DeleteMapping("user")
    public ResponseEntity<?> jbaTest() {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping("user/{user_email}")
    public ResponseEntity<UserDeleteDto> deleteUser(
            @PathVariable("user_email") String userEmail
    ) throws UserDoesNotExistsException, AdministratorRoleRemovalException {
        UserDeleteDto userDeleteDto = adminService.deleteUser(userEmail);
        return new ResponseEntity<>(userDeleteDto, HttpStatus.OK);
    }

    @GetMapping("user")
    public ResponseEntity<List<UserAuthGetDto>> getUsers() {
        List<UserAuthGetDto> userAuthGetDtos = adminService.getAllUsers();
        return new ResponseEntity<>(userAuthGetDtos, HttpStatus.OK);
    }

}
