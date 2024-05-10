package com.jennifer.service;

import com.jennifer.dto.request.EmployeeRequestDto;
import com.jennifer.dto.request.LoginRequest;
import com.jennifer.dto.response.EmployeeResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



public interface EmployeeService {

    EmployeeResponseDto createEmployee(EmployeeRequestDto employeeRequestDto);

    String employeeLogin(LoginRequest loginRequest);

    EmployeeResponseDto updateEmployee(EmployeeRequestDto employeeRequestDto);

    EmployeeResponseDto getEmployeeById(Long id);


    Page<EmployeeResponseDto> getAllEmployees(Pageable pageable);

    Page<EmployeeResponseDto> getEmployeesByDepartment(String departmentName, Pageable pageable);

    Page<EmployeeResponseDto> getAllEmployeesInAnOrganization(Long organizationId, Pageable pageable);
}
