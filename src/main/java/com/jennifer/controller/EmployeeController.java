package com.jennifer.controller;

import com.jennifer.dto.request.EmployeeRequestDto;
import com.jennifer.dto.request.LoginRequest;
import com.jennifer.dto.response.ApiResponse;
import com.jennifer.dto.response.EmployeeResponseDto;
import com.jennifer.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/createEmployee")
    public ResponseEntity<ApiResponse<EmployeeResponseDto>> createEmployee(@RequestBody EmployeeRequestDto employeeRequest) {
        EmployeeResponseDto responseDto = employeeService.createEmployee(employeeRequest);
        ApiResponse<EmployeeResponseDto> response = new ApiResponse<>(HttpStatus.OK.value(), "Employee created successfully", responseDto, HttpStatus.OK);
        return ResponseEntity.ok(response);

    }
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> loginEmployee(@RequestBody LoginRequest loginRequest){
        String response = employeeService.employeeLogin(loginRequest);
        ApiResponse<String> apiResponse = new ApiResponse<>(HttpStatus.OK.value(), "true", response, HttpStatus.OK);
        return ResponseEntity.ok(apiResponse);


    }

    @PutMapping("/updateEmployee")
    public ResponseEntity<ApiResponse<EmployeeResponseDto>> updateEmployee(@RequestBody EmployeeRequestDto employeeRequestDto){
        EmployeeResponseDto responseDto = employeeService.updateEmployee(employeeRequestDto);
        ApiResponse<EmployeeResponseDto> response = new ApiResponse<>(HttpStatus.OK.value(), "Employee updated successfully", responseDto, HttpStatus.OK);
        return ResponseEntity.ok(response);

    }

    @GetMapping("/getEmployeeById/{id}")
    public ResponseEntity<EmployeeResponseDto> getEmployeeById(@PathVariable Long id){
    return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @GetMapping("/getAllEmployees")
    public ResponseEntity<Page<EmployeeResponseDto>> getAllEmployees(Pageable pageable) {
        Page<EmployeeResponseDto> employeePage = employeeService.getAllEmployees(pageable);
        return ResponseEntity.ok(employeePage);
    }

    @GetMapping("/getEmployeesByDepartment")
    public ResponseEntity<Page<EmployeeResponseDto>> getAllEmployeesByDepartment(@RequestParam("departmentName") String departmentName, Pageable pageable) {
        Page<EmployeeResponseDto> employeePage = employeeService.getEmployeesByDepartment(departmentName, pageable);
        return ResponseEntity.ok(employeePage);
    }

    @GetMapping("/getOrganizationsEmployee/{organizationId}/employees")
    public ResponseEntity<Page<EmployeeResponseDto>> getAllEmployeesInAnOrganization(
            @PathVariable Long organizationId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<EmployeeResponseDto> employeePage = employeeService.getAllEmployeesInAnOrganization(organizationId, pageable);
        return ResponseEntity.ok(employeePage);
    }
}










