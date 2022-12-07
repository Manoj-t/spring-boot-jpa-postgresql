package com.manoj.jpa.postgresql.controller;

import com.manoj.jpa.postgresql.model.Employee;
import com.manoj.jpa.postgresql.repository.EmployeeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {
    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/employees")
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(
           @PathVariable(name = "id") Long employeeId) throws Exception {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new Exception("Employee Not found for this id ::" + employeeId));

        return ResponseEntity.ok(employee);
    }

    @PostMapping("/employees")
    public Employee createEmployee(@Validated @RequestBody Employee employee){
        return employeeRepository.save(employee);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable(value = "id") Long employeeId,
            @Validated @RequestBody Employee employeeDetails) throws Exception {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new Exception("Employee Not found for this id ::" + employeeId));

        employee.setEmailId(employeeDetails.getEmailId());
        employee.setLastName(employeeDetails.getLastName());
        employee.setFirstName(employeeDetails.getFirstName());

        final Employee updatedEmployee = employeeRepository.save(employee);

        return ResponseEntity.ok(updatedEmployee);
    }
}
