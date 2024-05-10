package com.jennifer.service;

import com.jennifer.dto.request.OrganizationRequestDto;
import com.jennifer.dto.response.OrganizationResponseDto;
import com.jennifer.exception.CustomException;

public interface OrganizationService {
    OrganizationResponseDto createOrganization(OrganizationRequestDto organizationRequestDto) throws CustomException;


}
