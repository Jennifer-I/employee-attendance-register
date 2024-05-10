package com.jennifer.controller;

import com.jennifer.dto.request.OrganizationRequestDto;
import com.jennifer.dto.response.ApiResponse;
import com.jennifer.dto.response.OrganizationResponseDto;
import com.jennifer.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/organization")
public class OrganizationController {

    private final OrganizationService organizationService;

    @PostMapping("/createOrganization")
    public ResponseEntity<ApiResponse<OrganizationResponseDto>> createOrganization(@RequestBody OrganizationRequestDto organizationRequestDto) {
        OrganizationResponseDto responseDto = organizationService.createOrganization(organizationRequestDto);
        ApiResponse<OrganizationResponseDto> response = new ApiResponse<>(HttpStatus.OK.value(), "Organization created successfully", responseDto, HttpStatus.OK);
        return ResponseEntity.ok(response);
    }


}
