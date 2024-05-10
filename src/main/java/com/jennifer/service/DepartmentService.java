package com.jennifer.service;

import com.jennifer.dto.request.CreateDepartmentRequest;
import com.jennifer.dto.response.DepartmentResponseDto;
import com.jennifer.model.Department;

import java.util.List;

public interface DepartmentService{


    DepartmentResponseDto createDepartment(CreateDepartmentRequest departmentRequestDto);

  }
