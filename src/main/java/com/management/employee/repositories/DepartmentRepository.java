package com.management.employee.repositories;

import com.management.employee.entities.Department;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    Optional<Department> findByName(String name);

    @Override
    @NonNull
    @Query(value = "SELECT * FROM department;", nativeQuery = true, countQuery = "0")
    List<Department> findAll();

    @Query(
            value = "SELECT e.department_id, d.name, AVG(e.salary) FROM employee e " +
                    "JOIN department d ON d.id = e.department_id " +
                    "GROUP BY e.department_id, d.name;",
            nativeQuery = true, countQuery = "0")
    List<Object[]> findAverageSalaryByDepartment();
}
