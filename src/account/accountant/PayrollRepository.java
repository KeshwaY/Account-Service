package account.accountant;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PayrollRepository extends MongoRepository<Payroll, String> {

    @Aggregation(pipeline = {
            "{$lookup: {from: 'user', localField: 'employee_id', foreignField: '_id', as: 'employee'}}",
            "{$match : {'employee.email': ?0, 'period': ?1 }}",
            "{$project: {'employee': 0}}",
    })
    List<Payroll> findByUserEmailAndPayrollPeriod(String email, String period);

    @Aggregation(pipeline = {
            "{$lookup: {from: 'user', localField: 'employee_id', foreignField: '_id', as: 'employee'}}",
            "{$match : {'employee.email': ?0}}",
            "{$project: {'employee': 0}}",
    })
    List<Payroll> findByUserEmail(String email);

}
