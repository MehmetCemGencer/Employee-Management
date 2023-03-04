package com.management.employee.repositories;

import com.management.employee.entities.Employee;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    Optional<Employee> findByEmail(String email);

    Optional<Employee> findByPhone(int phone);

    @Override
    @NonNull
    @Query(value = "SELECT * FROM employee;", nativeQuery = true, countQuery = "0")
    List<Employee> findAll();
}
