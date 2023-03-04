package com.management.employee.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SuccessResponse {
    private String message;
    private Object data;
}
