package com.management.employee.controllers;

import com.management.employee.entities.Department;
import com.management.employee.entities.Employee;
import com.management.employee.requests.EmployeeRequest;
import com.management.employee.responses.ErrorResponse;
import com.management.employee.responses.SuccessResponse;
import com.management.employee.services.DepartmentService;
import com.management.employee.services.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Employee> employees = employeeService.getAll();

        SuccessResponse response = SuccessResponse.builder().data(employees).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @PathVariable UUID id
    ) {
        Optional<Employee> employee = employeeService.findById(id);

        if (employee.isEmpty()) {
            ErrorResponse response = ErrorResponse.builder().message("Employee Not Found").build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        SuccessResponse response = SuccessResponse.builder().data(employee.get()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(
            @Valid @RequestBody EmployeeRequest request
            ) {
        Optional<Employee> employee = employeeService.findByEmail(request.getEmail());
        Optional<Employee> employeeByPhone  = employeeService.findByPhone(request.getPhone());

        if (employee.isPresent() || employeeByPhone.isPresent()) {
            ErrorResponse response = ErrorResponse.builder().message("Employee Already Exists").build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (request.getDepartmentId() != null) {
            Optional<Department> department = departmentService.findById(request.getDepartmentId());
            if (department.isEmpty()) {
                ErrorResponse response = ErrorResponse.builder().message("Department Does Not Exist").build();
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        }

        if (request.getManagerId() != null) {
            Optional<Employee> manager = employeeService.findById(request.getManagerId());
            if (manager.isEmpty()) {
                ErrorResponse response = ErrorResponse.builder().message("Manager Does Not Exist").build();
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        }

        Employee newEmployee = employeeService.create(request);

        SuccessResponse response = SuccessResponse.builder()
                .data(newEmployee).message("Employee Created").build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable UUID id,
            @Valid @RequestBody EmployeeRequest request
    ) {
        Optional<Employee> employee = employeeService.findById(id);

        if (employee.isEmpty()) {
            ErrorResponse response = ErrorResponse.builder().message("Employee Not Found").build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Optional<Employee> ePhone = employeeService.findByPhone(request.getPhone());
        Optional<Employee> eEmail = employeeService.findByEmail(request.getEmail());

        if (eEmail.isPresent() && eEmail.get().getEmail() != employee.get().getEmail()) {
            ErrorResponse response = ErrorResponse.builder().message("Email Belongs To Someone Else").build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (ePhone.isPresent() && ePhone.get().getPhone() != employee.get().getPhone()) {
            ErrorResponse response = ErrorResponse.builder().message("Phone Number Belongs To Someone Else").build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (request.getDepartmentId() != null) {
            Optional<Department> department = departmentService.findById(request.getDepartmentId());
            if (department.isEmpty()) {
                ErrorResponse response = ErrorResponse.builder().message("Department Does Not Exist").build();
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        }

        if (request.getManagerId() != null) {
            Optional<Employee> manager = employeeService.findById(request.getManagerId());
            if (manager.isEmpty()) {
                ErrorResponse response = ErrorResponse.builder().message("Manager Does Not Exist").build();
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        }

        Employee updatedEmployee = employeeService.update(employee.get(), request);

        SuccessResponse response = SuccessResponse.builder()
                .data(updatedEmployee).message("Employee Updated").build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
