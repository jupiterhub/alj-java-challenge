package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional(isolation = Isolation.REPEATABLE_READ)   // ensure that data is always up to date and no dirty read/writes
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> retrieveEmployees() {
        return retrieveEmployees(Pageable.unpaged()).getContent();
    }

    public Page<Employee> retrieveEmployees(Pageable pageable) {
        return  employeeRepository.findAll(pageable);
    }

    public Employee getEmployee(Long employeeId) {
        return employeeRepository.findById(employeeId).orElse(null);
    }


    public Employee saveEmployee(Employee employee){
        if (isEmployeeValid(employee))
            return null;

        employee.setCreatedDate(Instant.now().toEpochMilli());
        return employeeRepository.save(employee);
    }

    public boolean deleteEmployee(Long employeeId){
        if (employeeId == null) return false;

        Employee employee = getEmployee(employeeId);
        if (employee == null) {
            return false;
        }

        employeeRepository.deleteById(employeeId);
        return true;
    }

    public Employee updateEmployee(Employee employee) {
        if (isEmployeeValid(employee))
            return null;

        employee.setLastModified(Instant.now().toEpochMilli());
        return employeeRepository.save(employee);
    }

    private static boolean isEmployeeValid(Employee employee) {
        return employee == null || employee.getName() == null || employee.getName().trim().isEmpty();
    }
}