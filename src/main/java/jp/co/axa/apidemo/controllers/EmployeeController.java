package jp.co.axa.apidemo.controllers;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.exception.EmployeeNotFoundException;
import jp.co.axa.apidemo.exception.NameRequiredException;
import jp.co.axa.apidemo.services.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public Page<Employee> getEmployees(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
        if (page != null && size != null) {
            return employeeService.retrieveEmployees(PageRequest.of(page, size));
        } else {
            return employeeService.retrieveEmployees(Pageable.unpaged());
        }
    }

    @GetMapping("/employees/{employeeId}")
    public Employee getEmployee(@PathVariable(name="employeeId")Long employeeId) {
        return employeeService.getEmployee(employeeId);
    }

    @PostMapping("/employees")
    public Employee saveEmployee(Employee employee){
        Employee saved = employeeService.saveEmployee(employee);
        if (saved == null) {
            throw new NameRequiredException();
        }
        return saved;
    }

    @DeleteMapping("/employees/{employeeId}")
    public void deleteEmployee(@PathVariable(name="employeeId")Long employeeId){
        boolean success = employeeService.deleteEmployee(employeeId);
        if (!success) {
            throw new EmployeeNotFoundException();
        }
        log.info("Employee Deleted Successfully");
    }

    @PutMapping("/employees/{employeeId}")
    public void updateEmployee(@RequestBody Employee employee,
                               @PathVariable(name="employeeId")Long employeeId){
        Employee emp = employeeService.getEmployee(employeeId);

        if(emp == null){
            throw new EmployeeNotFoundException();
        }

        employeeService.updateEmployee(employee);
    }

}
