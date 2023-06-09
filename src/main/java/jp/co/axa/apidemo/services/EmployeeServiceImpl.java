package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.exceptions.NotFound;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Cacheable(value = "employee", key = "#employeeId")
    public Employee getEmployee(Long employeeId) {
        Optional<Employee> optEmp = employeeRepository.findById(employeeId);

        if (!optEmp.isPresent()) {
            throw new NotFound("Employee not found");
        } else {
            return optEmp.get();
        }
    }

    public Employee saveEmployee(Employee employee) {
        Employee savedEmployee = employeeRepository.save(employee);
        return updateEmployeeCache(savedEmployee);
    }

    @CacheEvict(value = "employee", key = "#employeeId")
    public void deleteEmployee(Long employeeId) {
        boolean exists = employeeRepository.existsById(employeeId);

        if (!exists) {
            throw new NotFound("Employee not found");
        } else {
            employeeRepository.deleteById(employeeId);
        }
    }

    @CachePut(value = "employee", key = "#employeeId")
    public Employee updateEmployee(Long employeeId, Employee employee) {
        boolean exists = employeeRepository.existsById(employeeId);

        if (!exists) {
            throw new NotFound("Employee not found");
        } else {
            return employeeRepository.save(employee);
        }
    }

    @CachePut(value = "employee", key = "#employee.id")
    private Employee updateEmployeeCache(Employee employee) {
        return employee;
    }
}