package com.application.piplinecicd.service;

import com.application.piplinecicd.model.Employee;
import com.application.piplinecicd.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        employee = new Employee(1L, "John", "Doe", "john.doe@example.com", "Developer");
    }

    @Test
    void testGetAllEmployees() {
        when(employeeRepository.findAll()).thenReturn(List.of(employee));
        List<Employee> employees = employeeService.getAllEmployees();
        assertEquals(1, employees.size());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void testGetEmployeeById() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        Optional<Employee> foundEmployee = employeeService.getEmployeeById(1L);
        assertTrue(foundEmployee.isPresent());
        assertEquals(employee.getFirstName(), foundEmployee.get().getFirstName());
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateEmployee() {
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        Employee savedEmployee = employeeService.createEmployee(employee);
        assertNotNull(savedEmployee);
        assertEquals("John", savedEmployee.getFirstName());
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void testUpdateEmployee() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        Employee updatedEmployee = employeeService.updateEmployee(1L, employee);
        assertNotNull(updatedEmployee);
        assertEquals("John", updatedEmployee.getFirstName());
        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void testDeleteEmployee() {
        // Mock the findById method to return the employee
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        // Do nothing when the deleteById method is called
        doNothing().when(employeeRepository).deleteById(1L);

        // Call the deleteEmployee method
        employeeService.deleteEmployee(1L);

        // Verify that the methods findById and deleteById were called exactly once
        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).deleteById(1L);
    }
}
