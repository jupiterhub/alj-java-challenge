package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)   // ensure that db is clear for each test method. improve by doing on class level
class EmployeeServiceImplIntegrationTest {

    @Autowired
    EmployeeService employeeService;

    @Test
    void failOperationIfEmployeeDoesNotExist() {
        // Given
        Long employeeId = -1l;    // does not exist

        // When
        boolean operationResult = employeeService.deleteEmployee(employeeId);

        // Then
        assertFalse(operationResult);
    }

    @Test
    void saveEmployee() {
        // Given
        Employee toSave = Employee.builder().name("test-1").salary(100).department("it").build();

        // When
        Employee saved = employeeService.saveEmployee(toSave);

        // Then
        assertNotNull(toSave.getId());
        assertEquals("test-1", saved.getName());
        assertEquals(100, saved.getSalary());
        assertEquals("it", saved.getDepartment());
    }

    @Test
    void nameIsRequired() {
        // Given
        Employee toSave = Employee.builder().name("   ").salary(100).department("it").build();

        // When
        Employee saved = employeeService.saveEmployee(toSave);

        // Then
        assertNull(saved);  // nothing is saved
    }

    @Test
    void findEmployee() {
        // Given
        Employee toSave = Employee.builder().name("test-1").salary(100).department("it").build();
        Employee saved = employeeService.saveEmployee(toSave);

        // When
        Employee retrieved = employeeService.getEmployee(saved.getId());

        // Then
        assertNotNull(retrieved);
        assertNotNull(retrieved.getId());
        assertEquals("test-1", saved.getName());
        assertEquals(100, saved.getSalary());
        assertEquals("it", saved.getDepartment());
    }


    @Test
    void updateEmployee() {
        // Given
        Employee toSave = Employee.builder().name("test-1").salary(100).department("it").build();
        Employee saved = employeeService.saveEmployee(toSave);
        Employee retrieved = employeeService.getEmployee(saved.getId());

        // When
        retrieved.setName("new-name");
        Employee newName = employeeService.updateEmployee(retrieved);
        Employee updated = employeeService.getEmployee(newName.getId());

        // Then
        assertNotNull(updated);
        assertEquals(updated.getId(), saved.getId());
        assertEquals("new-name", updated.getName());
    }

    @Test
    void findEmployeeByPage() {
        // Given
        IntStream.rangeClosed(1,10)
                .forEach(i -> employeeService.saveEmployee(
                        Employee.builder().name("paged-" + i).salary(100 + i).department("it").build()));

        Pageable pageable = Pageable.ofSize(5);

        // When
        Page<Employee> employees = employeeService.retrieveEmployees(pageable);

        // Then
        assertTrue( employees.getTotalElements() >= 10); // other test data might be present, this can be configured to delete per test but at the cost-of-performance.
        assertTrue( employees.getTotalPages() >= 2);
        assertEquals(5, employees.getSize());

        employees.get().anyMatch(i -> i.getName().equals("paged-1"));   // 1-8, just test 1 value
        employees.get().noneMatch(i -> i.getName().equals("paged-10"));  // 9-10 not present, just test 1 value
    }
}