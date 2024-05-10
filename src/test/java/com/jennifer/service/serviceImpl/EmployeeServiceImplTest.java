package com.jennifer.service.serviceImpl;

import com.jennifer.dto.request.EmployeeRequestDto;
import com.jennifer.dto.response.EmployeeResponseDto;
import com.jennifer.enums.EmployeeType;
import com.jennifer.enums.Gender;
import com.jennifer.exception.CustomException;
import com.jennifer.model.Department;
import com.jennifer.model.Employee;
import com.jennifer.model.Organization;
import com.jennifer.repository.DepartmentRepository;
import com.jennifer.repository.EmployeeRepository;
import com.jennifer.repository.OrganizationRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@Slf4j
@SpringBootTest
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentRepository departmentRepository;


    @InjectMocks
    private EmployeeServiceImpl employeeService;


    @Test
    void createEmployee_shouldCreateNewEmployee() {

        String firstName = "David";
        String lastName = "Isaac";
        String email = "davidisaac@example.com";
        String phoneNumber = "+1234567890";
        EmployeeType employeeType = EmployeeType.MEDICAL;
        Long departmentId = 1L;
        Gender gender = Gender.MALE;

        EmployeeRequestDto requestDto = new EmployeeRequestDto(
                firstName, lastName, email, phoneNumber, employeeType, departmentId, gender, "Password123#"
        );

        Department mockDepartment = Mockito.mock(Department.class);
        Mockito.when(mockDepartment.getDepartmentName()).thenReturn("Example Department");

        when(departmentRepository.findById(any())).thenReturn(Optional.of(mockDepartment));

        Employee mockEmployee = Mockito.mock(Employee.class);
        Mockito.when(mockEmployee.getGender()).thenReturn(gender);
        Mockito.when(mockEmployee.getEmployeeType()).thenReturn(employeeType);
        Mockito.when(mockEmployee.getDepartment()).thenReturn(mockDepartment);
        Mockito.when(mockEmployee.getFirstName()).thenReturn(firstName);
        Mockito.when(mockEmployee.getLastName()).thenReturn(lastName);
        Mockito.when(mockEmployee.getPhoneNumber()).thenReturn(phoneNumber);
        Mockito.when(mockEmployee.getEmail()).thenReturn(email);
        when(employeeRepository.save(any())).thenReturn(mockEmployee);


        EmployeeResponseDto result;
        result = employeeService.createEmployee(requestDto);


        assertNotNull(result);
        assertEquals(firstName, result.getFirstName());
        assertEquals(lastName, result.getLastName());
        assertEquals(email, result.getEmail());
        assertEquals(phoneNumber, result.getPhoneNumber());
        assertEquals(employeeType.toString(), result.getEmployeeType());
        assertEquals(gender.toString(), result.getGender());
    }




    @Test
    void getEmployeeById_shouldReturnEmployeeIfExists() {

        Long employeeId = 1L;
        Employee expectedEmployee = new Employee();
        expectedEmployee.setFirstName("John Doe");
        expectedEmployee.setEmail("john.doe@example.com");
        expectedEmployee.setDepartment(new Department());
        expectedEmployee.setGender(Gender.MALE);
        expectedEmployee.setLastName("Doe");
        expectedEmployee.setPhoneNumber("+1234567890");
        expectedEmployee.setEmployeeType(EmployeeType.MEDICAL);


        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(expectedEmployee));


        EmployeeResponseDto result = employeeService.getEmployeeById(employeeId);


        assertNotNull(result);
        assertEquals(expectedEmployee.getFirstName(), result.getFirstName());
        assertEquals(expectedEmployee.getEmail(), result.getEmail());
        assertEquals(expectedEmployee.getEmployeeType().toString(), result.getEmployeeType());
        assertEquals(expectedEmployee.getGender().toString(), result.getGender());
        assertEquals(expectedEmployee.getLastName(), result.getLastName());
        assertEquals(expectedEmployee.getPhoneNumber(), result.getPhoneNumber());
    }




}