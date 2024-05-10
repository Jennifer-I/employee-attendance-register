package com.jennifer.dto.response;

import com.jennifer.enums.Gender;
import com.jennifer.model.Employee;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeResponseDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String employeeType;
    private String departmentName;
    private String gender;

    public EmployeeResponseDto(Employee employee) {
        this.firstName = employee.getFirstName();
        this.lastName = employee.getLastName();
        this.email = employee.getEmail();
        this.phoneNumber = employee.getPhoneNumber();
        this.gender = employee.getGender() != null ? employee.getGender().toString() : "Unknown";
        this.employeeType = employee.getEmployeeType() != null ? employee.getEmployeeType().toString() : "Unknown";
        this.departmentName = employee.getDepartment() != null ? employee.getDepartment().getDepartmentName() : "Unknown";;
    }


}


