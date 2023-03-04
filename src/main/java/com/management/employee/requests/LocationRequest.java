package com.management.employee.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocationRequest {
    @NotEmpty
    @Size(min = 2)
    private String name;
    @NotEmpty
    @Size(min = 2)
    private String address;
    @NotEmpty
    @Size(min=2)
    private String zipCode;
    @NotEmpty
    @Size(min = 2)
    private String city;
    @NotEmpty
    @Size(min = 2)
    private String country;
}
