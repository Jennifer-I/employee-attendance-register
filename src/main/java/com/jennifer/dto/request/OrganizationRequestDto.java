package com.jennifer.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganizationRequestDto {
    @NotNull(message = "Organization name is required")
    private String organizationName;
    @NotNull(message = "Organization address is required")
    private String organizationAddress;
}
