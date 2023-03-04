package com.management.employee.requests;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequest {
    @NotEmpty
    @Size(min = 2)
    private String fName;
    @NotEmpty
    @Size(min = 2)
    private String lName;
    @NotEmpty
    @Email
    private String email;
    private int phone;
    @PastOrPresent
    private Date startedAt;
    private double salary;
    private Integer departmentId;
    private String title;
    private UUID managerId;
}
