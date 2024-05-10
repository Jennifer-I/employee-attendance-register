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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
    @Mock
    OrganizationRepository organizationRepository;

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


    @Test
    void getAllEmployees_shouldReturnAllEmployees() {

        List<Employee> expectedEmployees = List.of(
                new Employee(1L, "jennifer", "Ije", "john.doe@example.com", "+234899999999", EmployeeType.MEDICAL, Gender.MALE, new Department()));

        Page<Employee> expectedPage = new PageImpl<>(expectedEmployees);

        when(employeeRepository.findAll(any(Pageable.class))).thenReturn(expectedPage);


        Page<EmployeeResponseDto> result = employeeService.getAllEmployees(Pageable.unpaged());


        assertNotNull(result);
        assertEquals(expectedPage.getTotalElements(), result.getTotalElements());
        assertEquals(expectedPage.getSize(), result.getSize());
        List<EmployeeResponseDto> resultList = result.getContent();

        assertEquals(1, resultList.size());
        EmployeeResponseDto firstResponseDto = resultList.get(0);
        assertEquals("jennifer", firstResponseDto.getFirstName());
        assertEquals("Ije", firstResponseDto.getLastName());
        assertEquals("+234899999999", firstResponseDto.getPhoneNumber());
        assertEquals("MEDICAL", firstResponseDto.getEmployeeType());
        assertEquals("john.doe@example.com", firstResponseDto.getEmail());
        assertEquals("MALE", firstResponseDto.getGender());
    }



    @Test
    void getEmployeesByDepartment_shouldReturnEmployeesByDepartment() {

        Department department = new Department();
        department.setDepartmentName("Engineering");
        String departmentName = department.getDepartmentName();


        List<Employee> expectedEmployees = Collections.singletonList(
                new Employee(1L, "jennifer", "Ije", "jennifer@example.com", "+234899999999", EmployeeType.MEDICAL, Gender.FEMALE, new Department(departmentName)));

        Page<Employee> expectedPage = new PageImpl<>(expectedEmployees);

        when(employeeRepository.findEmployeeByDepartmentDepartmentName(eq(departmentName), any(Pageable.class)))
                .thenReturn(expectedPage);

        Page<EmployeeResponseDto> result = employeeService.getEmployeesByDepartment(departmentName, Pageable.unpaged());

        assertNotNull(result);
        assertEquals(expectedPage.getTotalElements(), result.getTotalElements());
        assertEquals(expectedPage.getSize(), result.getSize());
        List<EmployeeResponseDto> resultList = result.getContent();

        assertEquals(1, resultList.size());
        EmployeeResponseDto firstResponseDto = resultList.get(0);

        assertEquals("jennifer", firstResponseDto.getFirstName());
        assertEquals("jennifer@example.com", firstResponseDto.getEmail());
        assertEquals("Ije", firstResponseDto.getLastName());
        assertEquals("MEDICAL", firstResponseDto.getEmployeeType());
        assertEquals("+234899999999", firstResponseDto.getPhoneNumber());
        assertEquals("FEMALE", firstResponseDto.getGender());
        assertEquals("Engineering", department.getDepartmentName());

        expectedEmployees = Collections.emptyList();
        expectedPage = new PageImpl<>(expectedEmployees);
        when(employeeRepository.findEmployeeByDepartmentDepartmentName(eq(departmentName), any(Pageable.class)))
                .thenReturn(expectedPage);

        result = employeeService.getEmployeesByDepartment(departmentName, Pageable.unpaged());

        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
        assertEquals(0, result.getSize());
        resultList = result.getContent();
        assertEquals(0, resultList.size());


        log.info("Test for Exception");
        when(employeeRepository.findEmployeeByDepartmentDepartmentName(eq(departmentName), any(Pageable.class)))
                .thenThrow(new RuntimeException("Database error"));
        when(employeeRepository.findEmployeeByDepartmentDepartmentName(eq(departmentName), any(Pageable.class)))
                .thenThrow(new RuntimeException("Database error"));

        try {
            employeeService.getEmployeesByDepartment(departmentName, Pageable.unpaged());
            fail("Expected an exception");
        } catch (CustomException ex) {
            assertEquals("An error occurred while fetching employees by department", ex.getMessage());
        }
    }

    @Test
    void getAllEmployeesInAnOrganization_shouldReturnEmployeesInOrganization() {

        Long organizationId = 1L;
        Organization expectedOrganization = new Organization();

        List<Department> expectedDepartments = Collections.singletonList(new Department("Paramedics"));
        List<Employee> expectedEmployees = Collections.singletonList(
                new Employee(1L, "jennifer", "Ije",  "jennifer@example.com", "+234899999999", EmployeeType.MEDICAL, Gender.FEMALE, new Department()));

        when(organizationRepository.findById(eq(organizationId))).thenReturn(Optional.of(expectedOrganization));
        when(departmentRepository.findAll()).thenReturn(expectedDepartments);
        when(employeeRepository. findEmployeeByDepartmentOrganization(eq(expectedOrganization))).thenReturn((Employee) expectedEmployees);
        Pageable pageable = Pageable.unpaged();


        Page<EmployeeResponseDto> result = employeeService.getAllEmployeesInAnOrganization(organizationId, pageable);

        log.info("asserting the result");
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getSize());
        List<EmployeeResponseDto> resultList = result.getContent();

        assertEquals(1, resultList.size());
        EmployeeResponseDto firstResponseDto = resultList.get(0);

        assertEquals("jennifer", firstResponseDto.getFirstName());
        assertEquals("jennifer@example.com", firstResponseDto.getEmail());
        assertEquals("Ije", firstResponseDto.getLastName());
        assertEquals("MEDICAL", firstResponseDto.getEmployeeType());
        assertEquals("+234899999999", firstResponseDto.getPhoneNumber());
        assertEquals("FEMALE", firstResponseDto.getGender());

        assertEquals(expectedDepartments.get(0).getDepartmentName(), firstResponseDto.getDepartmentName());

        log.info("Test for Empty Lists");
        expectedEmployees = Collections.emptyList();
        when(employeeRepository. findEmployeeByDepartmentOrganization(eq(expectedOrganization))).thenReturn((Employee) expectedEmployees);

        result = employeeService.getAllEmployeesInAnOrganization(organizationId, pageable);

        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
        assertEquals(0, result.getSize());
        resultList = result.getContent();
        assertEquals(0, resultList.size());
    }

}