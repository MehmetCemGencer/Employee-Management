package com.management.employee.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String fName;
    private String lName;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private int phone;
    @Temporal(TemporalType.TIMESTAMP)
    private Date startedAt;
    private double salary;
    private Integer departmentId;
    private String title;
    private UUID managerId;
}
