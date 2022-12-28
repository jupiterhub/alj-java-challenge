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

    public List<Employee> retrieveEmployees();

    public Page<Employee> retrieveEmployees(Pageable pageable); // ideally create a wrapper for `Page` interface, but for simplicity just use it.

    public Employee getEmployee(Long employeeId);

    public Employee saveEmployee(Employee employee);

    public boolean deleteEmployee(Long employeeId);

    public Employee updateEmployee(Employee employee);
}