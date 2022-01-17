package account.accountant.employee;

import account.accountant.Payroll;
import account.accountant.PayrollRepository;
import account.accountant.PeriodTranslator;
import account.accountant.employee.dto.PayrollGetDto;
import account.accountant.employee.exceptions.CouldNotFindPayrollsException;
import account.accountant.exceptions.PayrollDoesNotExistException;
import account.auth.user.User;
import account.auth.user.UserRepository;
import account.auth.user.exceptions.UserDoesNotExistsException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    private final PayrollRepository payrollRepository;
    private final UserRepository userRepository;
    private final PeriodTranslator periodTranslator;

    public EmployeeService(PayrollRepository payrollRepository, UserRepository userRepository, PeriodTranslator periodTranslator) {
        this.payrollRepository = payrollRepository;
        this.userRepository = userRepository;
        this.periodTranslator = periodTranslator;
    }

    public PayrollGetDto findPayment(String email, String period) throws UserDoesNotExistsException, PayrollDoesNotExistException {
        User user = userRepository.findByEmail(email).orElseThrow(UserDoesNotExistsException::new);
        Payroll payroll = payrollRepository.findByUserEmailAndPayrollPeriod(email, periodTranslator.translatePeriodToLocalDateTime(period)).orElseThrow(PayrollDoesNotExistException::new);
        PayrollGetDto dto = createDto(user, payroll);
        dto.setPeriod(translatePeriod(payroll.getPeriod()));
        return dto;
    }

    private String translatePeriod(LocalDateTime dateTime) {
        return periodTranslator.translatePeriodNumericMonthToLiteral(periodTranslator.translateLocalDateTimeToPeriod(dateTime));
    }

    public List<PayrollGetDto> findPayments(String email) throws UserDoesNotExistsException, CouldNotFindPayrollsException {
        User user = userRepository.findByEmail(email).orElseThrow(UserDoesNotExistsException::new);
        List<Payroll> payrolls = getPayrollsForUser(email);
        return createSortedDescPayrollGetDtos(user, payrolls);
    }

    private List<Payroll> getPayrollsForUser(String email) throws CouldNotFindPayrollsException {
        List<Payroll> payrollList = payrollRepository.findByUserEmailAndOrderByPeriodDesc(email);
        if (payrollList.size() == 0) {
            throw new CouldNotFindPayrollsException();
        }
        return payrollList;
    }

    private List<PayrollGetDto> createSortedDescPayrollGetDtos(User user, List<Payroll> payrolls) {
        List<PayrollGetDto> payrollGetDtos = new ArrayList<>();
        for (Payroll payroll : payrolls) {
            payrollGetDtos.add(createDto(user, payroll));
        }
        return payrollGetDtos;
    }

    private PayrollGetDto createDto(User user, Payroll payroll) {
        String salary = payroll.getSalary().toString();
        String dollars = salary.substring(0, salary.length() - 2);
        if (dollars.length() == 0) {
            dollars = "0";
        }
        String payrollCents = salary.substring(salary.length() - 2);
        if (payrollCents.length() == 0) {
            payrollCents = "0";
        }
        return new PayrollGetDto(
                user.getName(),
                user.getLastname(),
                translatePeriod(payroll.getPeriod()),
                String.format("%s dollar(s) %s cent(s)", dollars, payrollCents)
        );
    }

}
