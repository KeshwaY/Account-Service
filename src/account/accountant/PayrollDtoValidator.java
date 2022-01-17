package account.accountant;

import account.accountant.dto.PayrollPostPutDTO;
import account.accountant.exceptions.PayrollDoesNotExistException;
import account.accountant.exceptions.PeriodIsNotUniqueException;
import account.auth.user.User;
import account.auth.user.exceptions.UserDoesNotExistsException;

import java.util.Optional;

public class PayrollDtoValidator {
    private final AccountantService accountantService;

    public PayrollDtoValidator(AccountantService accountantService) {
        this.accountantService = accountantService;
    }

    User checkIfUserExistsAndPayrollDoesNot(PayrollPostPutDTO dto) throws UserDoesNotExistsException, PeriodIsNotUniqueException {
        User user = accountantService.getUserRepository().findByEmail(dto.getEmail()).orElseThrow(UserDoesNotExistsException::new);
        Optional<Payroll> payroll = accountantService.getPayrollRepository()
                .findByUserEmailAndPayrollPeriod(
                        dto.getEmail(),
                        accountantService.getPeriodTranslator().translatePeriodToLocalDateTime(dto.getPeriod())
                );
        if (payroll.isPresent()) {
            throw new PeriodIsNotUniqueException();
        }
        return user;
    }

    Payroll checkIfUserAndPayrollExists(PayrollPostPutDTO dto) throws UserDoesNotExistsException, PayrollDoesNotExistException {
        accountantService.getUserRepository().findByEmail(dto.getEmail()).orElseThrow(UserDoesNotExistsException::new);
        return accountantService.getPayrollRepository().findByUserEmailAndPayrollPeriod(
                dto.getEmail(),
                accountantService.getPeriodTranslator().translatePeriodToLocalDateTime(dto.getPeriod())
        ).orElseThrow(PayrollDoesNotExistException::new);
    }
}