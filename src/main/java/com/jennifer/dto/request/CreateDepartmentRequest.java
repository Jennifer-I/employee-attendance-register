package com.jennifer.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateDepartmentRequest {
    @NotNull(message = "Department name is required")
    private String departmentName;

    @NotNull(message = "Organization ID is required")
    private Long organizationId;

}
