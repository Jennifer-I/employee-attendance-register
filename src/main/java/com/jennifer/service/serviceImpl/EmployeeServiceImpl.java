package com.jennifer.service.serviceImpl;

import com.jennifer.dto.request.EmployeeRequestDto;
import com.jennifer.dto.request.LoginRequest;
import com.jennifer.dto.response.EmployeeResponseDto;
import com.jennifer.exception.CustomException;
import com.jennifer.exception.UserAlreadyExistException;
import com.jennifer.exception.UserNotFoundException;
import com.jennifer.model.Department;
import com.jennifer.model.Employee;
import com.jennifer.model.Organization;
import com.jennifer.repository.DepartmentRepository;
import com.jennifer.repository.EmployeeRepository;
import com.jennifer.repository.OrganizationRepository;
import com.jennifer.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final OrganizationRepository organizationRepository;
    private final DepartmentRepository departmentRepository;

    @Override
    public EmployeeResponseDto createEmployee(EmployeeRequestDto employeeRequestDto) {
        try {
            var existingEmployee = employeeRepository.findByEmail(employeeRequestDto.getEmail());
            if (existingEmployee.isPresent()) {
                throw new UserAlreadyExistException("User with email " + employeeRequestDto.getEmail() + " already exists");
            }

            Department department = departmentRepository.findById(employeeRequestDto.getDepartmentId())
                    .orElseThrow(() -> new CustomException("Department with ID " + employeeRequestDto.getDepartmentId() + " not found"));

            Employee newEmployee = Employee.builder()
                    .department(department)
                    .email(employeeRequestDto.getEmail())
                    .employeeType(employeeRequestDto.getEmployeeType())
                    .firstName(employeeRequestDto.getFirstName())
                    .lastName(employeeRequestDto.getLastName())
                    .password(employeeRequestDto.getPassword())
                    .gender(employeeRequestDto.getGender())
                    .phoneNumber(employeeRequestDto.getPhoneNumber())
                    .build();
            Employee savedEmployee = employeeRepository.save(newEmployee);

            log.info("creating employee response dto");
            return new EmployeeResponseDto(savedEmployee);
        } catch (DataAccessException e) {
            throw new ServiceException("An error occurred while creating the employee", e);
        }
    }

    @Override
    public String employeeLogin(LoginRequest loginRequest) {
        Optional<Employee> employee = employeeRepository.findByEmail(loginRequest.getEmail());
        if (employee.isEmpty()) {
            throw new UserNotFoundException("User with email " + loginRequest.getEmail() + " not found");
        }
        if (!employee.get().getPassword().equals(loginRequest.getPassword())) {
            throw new UserNotFoundException("Invalid password");
        }
        return "Login successful";
    }


    @Override
    public EmployeeResponseDto updateEmployee(EmployeeRequestDto employeeRequestDto) {
        String email = employeeRequestDto.getEmail();
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        var optionalEmployee = employeeRepository.findByEmail(email);
        if (optionalEmployee.isPresent()) {
            Employee existingEmployee = optionalEmployee.get();
            existingEmployee.setDepartment(departmentRepository.findById(employeeRequestDto.getDepartmentId())
                    .orElseThrow(() -> new CustomException("Department with ID " + employeeRequestDto.getDepartmentId() + " not found")));
            existingEmployee.setEmployeeType(employeeRequestDto.getEmployeeType());
            existingEmployee.setFirstName(employeeRequestDto.getFirstName());
            existingEmployee.setLastName(employeeRequestDto.getLastName());
            existingEmployee.setGender(employeeRequestDto.getGender());
            existingEmployee.setPhoneNumber(employeeRequestDto.getPhoneNumber());

            Employee updatedEmployee = employeeRepository.save(existingEmployee);


            log.info("creating employee response dto");
            return new EmployeeResponseDto(updatedEmployee);
        } else {
            throw new UserNotFoundException("Employee with email " + email + " not found");
        }
    }


    @Override
    public EmployeeResponseDto getEmployeeById(Long id) {
        var optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            log.info("creating employee response dto");
            return new EmployeeResponseDto(employee);
        } else {
            throw new UserNotFoundException("Employee with ID " + id + " not found");
        }
    }


    @Override
    public Page<EmployeeResponseDto> getAllEmployees(Pageable pageable) {
        try {
            return employeeRepository.findAll(pageable)
                    .map(EmployeeResponseDto::new);
        } catch (Exception ex) {
            throw new CustomException("An error occurred while fetching employees", ex);
        }
    }

    @Override
    public Page<EmployeeResponseDto> getEmployeesByDepartment(String departmentName, Pageable pageable) {
        try {
            return employeeRepository.findEmployeeByDepartmentDepartmentName(departmentName, pageable)
                    .map(EmployeeResponseDto::new);
        } catch (Exception ex) {
            throw new CustomException("An error occurred while fetching employees by department", ex);
        }
    }


    @Override
    public Page<EmployeeResponseDto> getAllEmployeesInAnOrganization(Long organizationId, Pageable pageable) {
        var organizationOptional = organizationRepository.findById(organizationId);
        if (organizationOptional.isEmpty()) {
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }

        Organization organization = organizationOptional.get();
        List<EmployeeResponseDto> employeeResponse = new ArrayList<>();

        log.info("Iterating through each department in the organization");
        for (Department department : organization.getDepartment()) {

            log.info("looping through employees in department: {}", department.getDepartmentName());
            for (Employee employee : department.getEmployee()) {
                employeeResponse.add(new EmployeeResponseDto(employee));
            }
        }

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), employeeResponse.size());
        Page<EmployeeResponseDto> employeePage;
        employeePage = new PageImpl<>(employeeResponse.subList(start, end), pageable, employeeResponse.size());

        return employeePage;
    }


}


