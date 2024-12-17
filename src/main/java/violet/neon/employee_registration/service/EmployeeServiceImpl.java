package violet.neon.employee_registration.service;

import org.springframework.stereotype.Service;
import violet.neon.employee_registration.model.Employee;
import violet.neon.employee_registration.exception.EmployeeAlreadyAddedException;
import violet.neon.employee_registration.exception.EmployeeNotFoundException;
import violet.neon.employee_registration.exception.EmployeeStorageIsFullException;

import java.util.*;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final Map<String, Employee> employees;
    private final int MAX_EMPLOYEES = 3;

    public EmployeeServiceImpl(List<Employee> employees) {
        this.employees = new HashMap<>();
    }

    @Override
    public Employee addEmployee(String firstName, String lastName) {
        if (employees.size() == MAX_EMPLOYEES) {
            throw new EmployeeStorageIsFullException();
        }

        Employee employee = new Employee(firstName, lastName);

        if (employees.containsKey(employee.getFullName())) {
            throw new EmployeeAlreadyAddedException();
        }

        employees.put(employee.getFullName(), employee);

        return employee;
    }

    @Override
    public Employee findEmployee(String firstName, String lastName) {
        Employee employee = new Employee(firstName, lastName);

        if (!employees.containsKey(employee.getFullName())) {
            throw new EmployeeNotFoundException();
        }

        return employee;
    }

    @Override
    public Employee removeEmployee(String firstName, String lastName) {
        Employee employee = new Employee(firstName, lastName);

        if (!employees.containsKey(employee.getFullName())) {
            throw new EmployeeNotFoundException();
        }

        employees.remove(employee);

        return employee;
    }

    @Override
    public Collection<Employee> findAllEmployees() {
        return Collections.unmodifiableCollection(employees.values());
    }
}
