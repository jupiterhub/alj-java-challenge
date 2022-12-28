package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * TODOs: please see readme.
 *
 * Note: use a DTO to transfer and convert Employee data
 */
public interface EmployeeService {

    /**
     * @Deprecated use {@link #retrieveEmployees(Pageable)}.
     */
    public List<Employee> retrieveEmployees();

    public Page<Employee> retrieveEmployees(Pageable pageable); // create a simpler wrapper for `Page` interface, but for simplicity of this test, let's just use it.

    public Employee getEmployee(Long employeeId);

    public Employee saveEmployee(Employee employee);

    public boolean deleteEmployee(Long employeeId);

    public Employee updateEmployee(Employee employee);
}