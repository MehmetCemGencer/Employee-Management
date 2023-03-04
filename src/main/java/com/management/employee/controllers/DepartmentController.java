package com.management.employee.controllers;

import com.management.employee.entities.Department;
import com.management.employee.entities.Employee;
import com.management.employee.entities.Location;
import com.management.employee.requests.DepartmentRequest;
import com.management.employee.responses.ErrorResponse;
import com.management.employee.responses.SuccessResponse;
import com.management.employee.services.DepartmentService;
import com.management.employee.services.EmployeeService;
import com.management.employee.services.LocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/department")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;
    private final LocationService locationService;
    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Department> departments = departmentService.getAll();

        SuccessResponse response = SuccessResponse.builder().data(departments).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDepartmentById(
            @PathVariable int id
    ) {
        Optional<Department> department = departmentService.findById(id);

        if (department.isEmpty()) {
            ErrorResponse response = ErrorResponse.builder().message("Department Not Found").build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        SuccessResponse response = SuccessResponse.builder().data(department.get()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/salary")
    public ResponseEntity<?> getAverageSalaryByDepartment() {
        List<Object> averageSalaries = departmentService.getAverageSalaryByDepartment();

        SuccessResponse response = SuccessResponse.builder().data(averageSalaries).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(
            @Valid @RequestBody DepartmentRequest request
            ) {
        Optional<Department> department = departmentService.findByName(request.getName());

        if (department.isPresent()) {
            ErrorResponse response = ErrorResponse.builder().message("Department Already Exists").build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (request.getLocationId() != null) {
            Optional<Location> location = locationService.findById(request.getLocationId());
            if (location.isEmpty()) {
                ErrorResponse response = ErrorResponse.builder().message("Location Does Not Exists").build();
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        }

        if (request.getManagerId() != null) {
            Optional<Employee> manager = employeeService.findById(request.getManagerId());
            if (manager.isEmpty()) {
                ErrorResponse response = ErrorResponse.builder().message("Manager Does Not Exists").build();
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        }

        Department newDepartment = departmentService.create(request);

        SuccessResponse response = SuccessResponse.builder()
                .data(newDepartment).message("Department Created").build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable int id,
            @Valid @RequestBody DepartmentRequest request
    ) {
        Optional<Department> department = departmentService.findById(id);

        if (department.isEmpty()) {
            ErrorResponse response = ErrorResponse.builder().message("Department Not Found").build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        if (request.getLocationId() != null) {
            Optional<Location> location = locationService.findById(request.getLocationId());
            if (location.isEmpty()) {
                ErrorResponse response = ErrorResponse.builder().message("Location Does Not Exists").build();
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        }

        if (request.getManagerId() != null) {
            Optional<Employee> manager = employeeService.findById(request.getManagerId());
            if (manager.isEmpty()) {
                ErrorResponse response = ErrorResponse.builder().message("Manager Does Not Exists").build();
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        }

        Department updatedDepartment = departmentService.update(department.get(), request);

        SuccessResponse response = SuccessResponse.builder()
                .data(updatedDepartment).message("Department Updated").build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
