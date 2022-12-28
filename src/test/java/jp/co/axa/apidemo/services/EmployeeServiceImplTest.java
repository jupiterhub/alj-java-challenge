package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class EmployeeServiceImplTest {

    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    EmployeeServiceImpl employeeService;

    @Test
    void handleNullIdOnDelete() {
        // Given
        Mockito.doThrow(new IllegalArgumentException()).when(employeeRepository).deleteById(null);

        // When
        boolean result = employeeService.deleteEmployee(null);

        // Then
        Assertions.assertFalse(result);
    }

    @Test
    void skipDbCallIfEmployeeNameIsEmpty() {
        // Given
        Employee toSave = Employee.builder().name("   ").salary(100).department("it").build();

        // When
        employeeService.saveEmployee(toSave);

        // Then
        Mockito.verifyNoInteractions(employeeRepository);
    }

    @Test
    void returnNullIfEmployeeDoesNotExist() {
        // Given
        Mockito.when(employeeRepository.findById(-1l)).thenReturn(Optional.empty());

        // When
        Employee employee = employeeService.getEmployee(-1L);

        // Then
        Assertions.assertNull(employee);
    }
}
