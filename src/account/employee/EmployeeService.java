package account.employee;

import account.accountant.Payroll;
import account.accountant.PayrollRepository;
import account.accountant.dto.PayrollDto;
import account.employee.dto.EmployeeSalaryGetDto;
import account.accountant.exceptions.UserDoesNotExistsException;
import account.user.User;
import account.user.UserRepository;
import org.springframework.stereotype.Service;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.*;

@Service
public class EmployeeService {

    private final PayrollRepository payrollRepository;
    private final UserRepository userRepository;

    public EmployeeService(PayrollRepository payrollRepository, UserRepository userRepository) {
        this.payrollRepository = payrollRepository;
        this.userRepository = userRepository;
    }

    public EmployeeSalaryGetDto findPayment(String email, String period) throws UserDoesNotExistsException {
        User user = userRepository.findByEmail(email).orElseThrow(UserDoesNotExistsException::new);
        List<Payroll> payrollList = payrollRepository.findByUserEmailAndPayrollPeriod(email, period);
        if (payrollList.size() == 0) {
            return null;
        }
        EmployeeSalaryGetDto dto = createDto(user, payrollList.get(0));
        dto.setPeriod(translatePeriod(dto.getPeriod()));
        return dto;
    }

    public List<EmployeeSalaryGetDto> findPayments(String email) throws UserDoesNotExistsException {
        User user = userRepository.findByEmail(email).orElseThrow(UserDoesNotExistsException::new);
        List<Payroll> payrollList = payrollRepository.findByUserEmail(email);
        if (payrollList.size() == 0) {
            return null;
        }
        List<EmployeeSalaryGetDto> salaryGetDtos = new ArrayList<>();
        for (Payroll payroll : payrollList) {
            salaryGetDtos.add(createDto(user, payroll));
        }
        DateTimeFormatter DATEFORMATTER1 = DateTimeFormatter.ofPattern("MM-yyyy");
        DateTimeFormatter DATEFORMATTER = new DateTimeFormatterBuilder().append(DATEFORMATTER1)
                .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                .toFormatter();
        salaryGetDtos.sort(Comparator.comparing(s -> LocalDateTime.parse(s.getPeriod(), DATEFORMATTER)));
        Collections.reverse(salaryGetDtos);
        salaryGetDtos.forEach(e -> e.setPeriod(translatePeriod(e.getPeriod())));
        return salaryGetDtos;
    }

    private EmployeeSalaryGetDto createDto(User user, Payroll payroll) {
        String salary = payroll.getSalary().toString();
        String dollars = salary.substring(0, salary.length() - 2);
        String payrollCents = salary.substring(salary.length() - 2);
        if (dollars.length() == 0) {
            dollars = "0";
        }
        if (payrollCents.length() == 0) {
            payrollCents = "0";
        }
        return new EmployeeSalaryGetDto(
                user.getName(),
                user.getLastname(),
                payroll.getPeriod(),
                String.format("%s dollar(s) %s cent(s)", dollars, payrollCents)
        );
    }

    private String translatePeriod(String period) {
        String[] split = period.split("-");
        String monthName = new DateFormatSymbols().getMonths()[Integer.parseInt(split[0])-1];
        return monthName + "-" + split[1];
    }

}
