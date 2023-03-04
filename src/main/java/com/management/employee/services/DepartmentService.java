package com.management.employee.services;

import com.management.employee.entities.Department;
import com.management.employee.repositories.DepartmentRepository;
import com.management.employee.requests.DepartmentRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public Optional<Department> findById(int id) {
        return departmentRepository.findById(id);
    }

    public Optional<Department> findByName(String name) {
        return departmentRepository.findByName(name);
    }

    public List<Department> getAll() {
        return departmentRepository.findAll();
    }

    public List<Object> getAverageSalaryByDepartment() {
        List<Object[]> list = departmentRepository.findAverageSalaryByDepartment();
        List<Object> res = new ArrayList<>(list.size());

        for (Object[] obj: list) {
            Map<String, Object> map = new HashMap<>();
            map.put("id",obj[0]);
            map.put("name", obj[1]);
            map.put("average", obj[2]);
            res.add(map);
        }

        return res;
    }

    public Department create(DepartmentRequest request) {
        Department newDepartment = Department.builder()
                .name(request.getName()).managerId(request.getManagerId()).locationId(request.getLocationId()).build();

        departmentRepository.save(newDepartment);

        return newDepartment;
    }

    public Department update(Department department, DepartmentRequest newFields) {
        department.setName(newFields.getName());
        department.setLocationId(newFields.getLocationId());
        department.setManagerId(newFields.getManagerId());

        departmentRepository.save(department);

        return department;
    }
}
