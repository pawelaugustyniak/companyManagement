package augustyniak.pawel.companymanagement.controller;

import augustyniak.pawel.companymanagement.model.EmployeeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import augustyniak.pawel.companymanagement.model.Employee;
import augustyniak.pawel.companymanagement.repo.EmployeeRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class WebController {
    @Autowired
    EmployeeRepository repository;

    @RequestMapping("/findall")
    public String findAll() {
        String result = "";

        for (Employee employee : repository.findAll()) {
            result += employee.toString() + "<br>";
        }

        return result;
    }

    @RequestMapping(
            value = "/add",
            method = POST,
            produces = "application/json"
    )
    public String add(@RequestBody EmployeeDto employeeDto) {
        Employee employee = new Employee(
                employeeDto.getFirstName(),
                employeeDto.getLastName(),
                employeeDto.getRole(),
                employeeDto.getEmail());
        repository.save(employee);
        return employee.toString();
    }

    @RequestMapping(
            value = "/delete/{email}",
            method = DELETE)
    public String delete(
//            @RequestParam(value = "email", required = true)
            @PathVariable("email")
                    String email) {
        repository.deleteByEmail(email);
        return "Employee {email} has been deleted";
    }

    @RequestMapping(
            value = "/find")
//            params = {"firstName", "lastName", "email"})
    public List<Employee> find(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email) {
        return repository.findByFirstNameOrLastNameOrEmail(firstName, lastName, email);
    }

//    @RequestMapping(
//            value = "/find")
//    public List<Employee> find(
//            @RequestParam("firstName") String firstName,
//            @RequestParam("email") String email) {
//        return repository.findByFirstNameOrLastNameOrEmail(firstName,null, email);
//    }


    @RequestMapping(value = "/role")
    public String role() {
        Map<String, Long> map =
                repository.findAll().
                        stream().
                        collect(Collectors.groupingBy((a -> a.getRole()), Collectors.counting()));

        return map.toString();
    }
}