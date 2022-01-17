package account.accountant;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PayrollRepository extends MongoRepository<Payroll, String> {

    @Aggregation(pipeline = {
            "{$lookup: {from: 'user', localField: 'employee_id', foreignField: '_id', as: 'employee'}}",
            "{$match : {'employee.email': ?0, 'period': ?1 }}",
            "{$project: {'employee': 0}}",
    })
    Optional<Payroll> findByUserEmailAndPayrollPeriod(String email, LocalDateTime period);

    @Aggregation(pipeline = {
            "{$lookup: {from: 'user', localField: 'employee_id', foreignField: '_id', as: 'employee'}}",
            "{$match : {'employee.email': ?0}}",
            "{$project: {'employee': 0}}",
            "{$sort : {'period' : -1}}"
    })
    List<Payroll> findByUserEmailAndOrderByPeriodDesc(String email);

}
