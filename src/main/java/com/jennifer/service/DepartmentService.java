package com.jennifer.service;

import com.jennifer.dto.request.CreateDepartmentRequest;
import com.jennifer.dto.response.DepartmentResponseDto;

public interface DepartmentService{


    DepartmentResponseDto createDepartment(CreateDepartmentRequest departmentRequestDto);



}
