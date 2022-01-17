package account.accountant;

import account.accountant.dto.PayrollPostPutDTO;
import account.accountant.dto.StatusDTO;
import account.accountant.exceptions.PayrollDoesNotExistException;
import account.accountant.exceptions.PeriodIsNotUniqueException;
import account.auth.user.exceptions.UserDoesNotExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("api/acct")
public class AccountantController {

    private final AccountantService accountantService;

    public AccountantController(AccountantService accountantService) {
        this.accountantService = accountantService;
    }

    @PostMapping("/payments")
    public ResponseEntity<StatusDTO> createPayments(
            @RequestBody List<@Valid PayrollPostPutDTO> payrollDtos
    ) throws UserDoesNotExistsException, PeriodIsNotUniqueException {
        StatusDTO statusDTO = accountantService.createPayments(payrollDtos);
        return new ResponseEntity<>(statusDTO, HttpStatus.OK);
    }

    @PutMapping("/payments")
    public ResponseEntity<StatusDTO> updatePayments(
            @RequestBody @Valid PayrollPostPutDTO payrollDto
    ) throws UserDoesNotExistsException, PayrollDoesNotExistException {
        StatusDTO statusDTO = accountantService.updatePayments(payrollDto);
        return new ResponseEntity<>(statusDTO, HttpStatus.OK);
    }

}
