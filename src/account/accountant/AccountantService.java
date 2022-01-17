package account.accountant;

import account.accountant.dto.PayrollPostPutDTO;
import account.accountant.dto.StatusDTO;
import account.accountant.exceptions.PayrollDoesNotExistException;
import account.accountant.exceptions.PeriodIsNotUniqueException;
import account.auth.user.exceptions.UserDoesNotExistsException;
import account.auth.user.User;
import account.auth.user.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountantService {

    private final UserRepository userRepository;
    private final PayrollRepository payrollRepository;
    private final PeriodTranslator periodTranslator;

    private final PayrollDtoValidator payrollDtoValidator = new PayrollDtoValidator(this);

    public AccountantService(UserRepository userRepository, PayrollRepository payrollRepository, PeriodTranslator periodTranslator) {
        this.userRepository = userRepository;
        this.payrollRepository = payrollRepository;
        this.periodTranslator = periodTranslator;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public PayrollRepository getPayrollRepository() {
        return payrollRepository;
    }

    public StatusDTO createPayments(List<PayrollPostPutDTO> payrollDtos) throws UserDoesNotExistsException, PeriodIsNotUniqueException {
        List <Payroll> payrolls = new ArrayList<>();
        for(PayrollPostPutDTO payrollDto : payrollDtos) {
            Payroll payroll = createPayroll(payrollDto);
            payrolls.add(payroll);
        }
        payrollRepository.saveAll(payrolls);
        return new StatusDTO("Added successfully!");
    }

    // Will be replaced with mapper
    private Payroll createPayroll(PayrollPostPutDTO payrollDto) throws UserDoesNotExistsException, PeriodIsNotUniqueException {
        User user = payrollDtoValidator.checkIfUserExistsAndPayrollDoesNot(payrollDto);
        Payroll payroll = new Payroll();
        payroll.setEmployeeId(
                new ObjectId(user.getId())
        );
        payroll.setPeriod(periodTranslator.translatePeriodToLocalDateTime(payrollDto.getPeriod()));
        payroll.setSalary(payrollDto.getSalary());
        return payroll;
    }

    public StatusDTO updatePayments(PayrollPostPutDTO payrollDto) throws UserDoesNotExistsException, PayrollDoesNotExistException {
        Payroll payroll = payrollDtoValidator.checkIfUserAndPayrollExists(payrollDto);
        payroll.setPeriod(periodTranslator.translatePeriodToLocalDateTime(payrollDto.getPeriod()));
        payroll.setSalary(payrollDto.getSalary());
        payrollRepository.save(payroll);
        return new StatusDTO("Updated successfully!");
    }

}
