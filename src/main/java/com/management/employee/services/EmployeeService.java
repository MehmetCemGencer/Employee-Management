package com.management.employee.services;

import com.management.employee.entities.Employee;
import com.management.employee.repositories.EmployeeRepository;
import com.management.employee.requests.EmployeeRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EmployeeService {
    private EmployeeRepository employeeRepository;

    public Optional<Employee> findById(UUID id) {
        return employeeRepository.findById(id);
    }

    public Optional<Employee> findByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }

    public Optional<Employee> findByPhone(int phone) { return employeeRepository.findByPhone(phone); }

    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    public Employee create(EmployeeRequest request) {
        Employee employee = Employee.builder()
                .fName(request.getFName())
                .lName(request.getLName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .startedAt(request.getStartedAt())
                .salary(request.getSalary())
                .departmentId(request.getDepartmentId())
                .title(request.getTitle())
                .managerId(request.getManagerId())
                .build();

        employeeRepository.save(employee);

        return employee;
    }

    public Employee update(Employee employee, EmployeeRequest newFields) {
        employee.setFName(newFields.getFName());
        employee.setLName(newFields.getLName());
        employee.setEmail(newFields.getEmail());
        employee.setPhone(newFields.getPhone());
        employee.setStartedAt(newFields.getStartedAt());
        employee.setSalary(newFields.getSalary());
        employee.setDepartmentId(newFields.getDepartmentId());
        employee.setTitle(newFields.getTitle());
        employee.setManagerId(newFields.getManagerId());

        employeeRepository.save(employee);

        return employee;

    }
}
