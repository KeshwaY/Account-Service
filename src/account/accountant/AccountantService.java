package account.accountant;

import account.accountant.dto.PayrollDto;
import account.accountant.dto.StatusDTO;
import account.accountant.exceptions.PeriodIsNotUniqueException;
import account.accountant.exceptions.UserDoesNotExistsException;
import account.user.User;
import account.user.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AccountantService {

    private final UserRepository userRepository;
    private final PayrollRepository payrollRepository;

    public AccountantService(UserRepository userRepository, PayrollRepository payrollRepository) {
        this.userRepository = userRepository;
        this.payrollRepository = payrollRepository;
    }

    @Transactional
    public StatusDTO createPayments(List<PayrollDto> payrollDtos) throws UserDoesNotExistsException, PeriodIsNotUniqueException {
        for(PayrollDto payrollDto : payrollDtos) {
            User user = validateCreatePayroll(payrollDto);
            Payroll payroll = new Payroll();
            payroll.setEmployeeId(
                    new ObjectId(user.getId())
            );
            payroll.setPeriod(payrollDto.getPeriod());
            payroll.setSalary(payrollDto.getSalary());
            payrollRepository.save(payroll);
        }
        return new StatusDTO("Added successfully!");
    }

    private User validateCreatePayroll(PayrollDto dto) throws UserDoesNotExistsException, PeriodIsNotUniqueException {
        Optional<User> user = userRepository.findByEmail(dto.getEmail());
        if (user.isEmpty()) {
            throw new UserDoesNotExistsException();
        }
        List<Payroll> payrolls = payrollRepository.findByUserEmailAndPayrollPeriod(dto.getEmail(), dto.getPeriod());
        if (payrolls.size() > 0) {
            throw new PeriodIsNotUniqueException();
        }
        return user.get();
    }

    @Transactional
    public StatusDTO updatePayments(PayrollDto payrollDto) throws UserDoesNotExistsException, PeriodIsNotUniqueException {
        Payroll payroll = validateUpdatePayroll(payrollDto);
        payroll.setPeriod(payrollDto.getPeriod());
        payroll.setSalary(payrollDto.getSalary());
        payrollRepository.save(payroll);
        return new StatusDTO("Updated successfully!");
    }

    private Payroll validateUpdatePayroll(PayrollDto dto) throws UserDoesNotExistsException, PeriodIsNotUniqueException {
        if (userRepository.findByEmail(dto.getEmail()).isEmpty()) {
            throw new UserDoesNotExistsException();
        }
        List<Payroll> payrolls = payrollRepository.findByUserEmailAndPayrollPeriod(dto.getEmail(), dto.getPeriod());
        if (payrolls.size() != 1) {
            throw new PeriodIsNotUniqueException();
        }
        return payrolls.get(0);
    }

}
