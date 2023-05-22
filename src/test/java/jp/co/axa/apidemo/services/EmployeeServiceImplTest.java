package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.exceptions.NotFound;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceImplTest {

    @Mock
    EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeServiceImpl target;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        target = new EmployeeServiceImpl(employeeRepository);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void getAllEmployees() {
        // create Mock data
        List<Employee> employees = createEmployDataList();

        // mock
        when(employeeRepository.findAll()).thenReturn(employees);

        // run
        List<Employee> actual = target.getAllEmployees();

        // verify
        verify(employeeRepository, times(1)).findAll();

        assertEquals(employees, actual);
    }

    @Test
    public void getAllEmployees_EmptyList() {
        // create Mock data
        List<Employee> employees = new ArrayList<>();

        // mock
        when(employeeRepository.findAll()).thenReturn(employees);

        // run
        List<Employee> actual = target.getAllEmployees();

        // verify
        verify(employeeRepository, times(1)).findAll();

        assertTrue(actual.isEmpty());
    }

    @Test
    public void getEmployee() {
        // create Mock data
        Employee employee = createEmployeeData();

        // mock
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        // run
        Employee actual = target.getEmployee(1L);

        // verify
        verify(employeeRepository, times(1)).findById(1L);

        assertEquals(employee, actual);
    }

    @Test(expected = NotFound.class)
    public void getEmployee_NotFound() {
        // mock
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        // run & verify
        target.getEmployee(1L);
    }

    @Test
    public void saveEmployee() {
        // create Mock data
        Employee employee = createEmployeeData();

        // mock
        when(employeeRepository.save(employee)).thenReturn(employee);

        // run
        Employee actual = target.saveEmployee(employee);

        // verify
        verify(employeeRepository, times(1)).save(employee);

        assertEquals(employee, actual);
    }

    @Test
    public void deleteEmployee() {
        // mock
        when(employeeRepository.existsById(1L)).thenReturn(true);
        doNothing().when(employeeRepository).deleteById(1L);

        // run
        target.deleteEmployee(1L);

        // verify
        verify(employeeRepository, times(1)).existsById(1L);
        verify(employeeRepository, times(1)).deleteById(1L);
    }

    @Test(expected = NotFound.class)
    public void deleteEmployee_NotFound() {
        // mock
        when(employeeRepository.existsById(1L)).thenReturn(false);

        // run & verify
        target.deleteEmployee(1L);
    }

    @Test
    public void updateEmployee() {
        // create Mock data
        Employee employee = createEmployeeData();

        // mock
        when(employeeRepository.existsById(1L)).thenReturn(true);
        when(employeeRepository.save(employee)).thenReturn(employee);

        // run
        Employee actual = target.updateEmployee(1L, employee);

        // verify
        verify(employeeRepository, times(1)).existsById(1L);
        verify(employeeRepository, times(1)).save(employee);

        assertEquals(employee, actual);
    }

    @Test(expected = NotFound.class)
    public void updateEmployee_NotFound() {
        // create Mock data
        Employee employee = createEmployeeData();

        // mock
        when(employeeRepository.existsById(1L)).thenReturn(false);

        // run & verify
        target.updateEmployee(1L, employee);
    }


    private Employee createEmployeeData() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("Test Employee");
        employee.setSalary(1000);
        employee.setDepartment("Test Department");

        return employee;
    }

    private List<Employee> createEmployDataList() {
        List<Employee> employees = new ArrayList<>();
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("Test Employee");
        employee.setSalary(1000);
        employee.setDepartment("Test Department");
        employees.add(employee);

        employee = new Employee();
        employee.setId(2L);
        employee.setName("Test Employee 2");
        employee.setSalary(2000);
        employee.setDepartment("Test Department 2");
        employees.add(employee);
        return employees;
    }
}