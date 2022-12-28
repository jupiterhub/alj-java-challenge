package jp.co.axa.apidemo.controllers;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.exception.EmployeeNotFoundException;
import jp.co.axa.apidemo.exception.NameRequiredException;
import jp.co.axa.apidemo.services.EmployeeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class EmployeeControllerTest {

    @Mock
    EmployeeService employeeService;

    @InjectMocks
    EmployeeController employeeController;

    @Test
    void allEmployeesWhenNoPagingData() {
        // Given
        // no paging AND no size information

        // When
        employeeController.getEmployees(1, null);

        // Then
        Mockito.verify(employeeService).retrieveEmployees(Pageable.unpaged());
    }
    @Test
    void employeesPagedWhenPagedRequest() {
        // Given
        // no paging AND no size information

        // When
        employeeController.getEmployees(1, 5);

        // Then
        Mockito.verify(employeeService).retrieveEmployees(Mockito.any());
    }

    @Test
    void throwExceptionIfNameIsNotValid() {
        // Given
        Mockito.when(employeeService.saveEmployee(Mockito.any())).thenReturn(null);

        NameRequiredException exception = assertThrows(NameRequiredException.class, () -> {
            // When
            employeeController.saveEmployee(Employee.builder().build());
        });

        Assertions.assertNotNull(exception);
    }

    @Test
    void employeeNotFoundWhenDeletingInvalidEmployee() {
        // Given
        Mockito.when(employeeService.deleteEmployee(Mockito.any())).thenReturn(false);

        EmployeeNotFoundException exception = assertThrows(EmployeeNotFoundException.class, () -> {
            // When
            employeeController.deleteEmployee(99l);
        });

        // Then
        Assertions.assertNotNull(exception);
    }

    @Test
    void employeeNotFoundWhenUpdatingInvalidEmployee() {
        // Given
        Mockito.when(employeeService.getEmployee(Mockito.any())).thenReturn(null);

        EmployeeNotFoundException exception = assertThrows(EmployeeNotFoundException.class, () -> {
            // When
            employeeController.updateEmployee(Employee.builder().build(), 99l);
        });

        // Then
        Assertions.assertNotNull(exception);
    }
}