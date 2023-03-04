package com.management.employee.requests;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class DepartmentRequest {
    @NotEmpty
    private String name;
    private UUID managerId;
    private Integer locationId;
}
