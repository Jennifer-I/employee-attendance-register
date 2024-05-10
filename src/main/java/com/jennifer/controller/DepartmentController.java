package com.jennifer.controller;

import com.jennifer.dto.request.CreateDepartmentRequest;
import com.jennifer.dto.response.ApiResponse;
import com.jennifer.dto.response.DepartmentResponseDto;
import com.jennifer.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping("/createDepartment")
    public ResponseEntity<ApiResponse<DepartmentResponseDto>> createDepartment(@RequestBody CreateDepartmentRequest departmentRequest) {
        DepartmentResponseDto departmentResponseDto = departmentService.createDepartment(departmentRequest);
        return ResponseEntity.ok(new ApiResponse<>("Department created successfully", departmentResponseDto, HttpStatus.CREATED));

    }





}
