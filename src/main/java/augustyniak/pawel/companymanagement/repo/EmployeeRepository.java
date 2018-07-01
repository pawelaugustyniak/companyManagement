package augustyniak.pawel.companymanagement.repo;

import org.springframework.data.repository.CrudRepository;
import augustyniak.pawel.companymanagement.model.Employee;

import javax.transaction.Transactional;
import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    List<Employee> findByLastName(String lastName);

    List<Employee> findAll();

    @Transactional
    Long deleteByEmail(String email);

    List<Employee> findByFirstNameOrLastNameOrEmail(String firstName, String lastName, String email);
}

