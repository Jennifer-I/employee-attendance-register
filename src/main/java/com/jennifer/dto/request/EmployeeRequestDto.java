package com.jennifer.dto.request;

import com.jennifer.enums.EmployeeType;
import com.jennifer.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@RequiredArgsConstructor
@Getter
@Setter
@Builder
public class EmployeeRequestDto {

    @NotNull(message = "First name is required")
    private String firstName;

    @NotNull(message = "Last name is required")
    private String lastName;

    @NotNull(message = "Email is required")
    @Email(regexp = ".+@.+", message = "Email address must contain '@'")
    private String email;

    @Pattern(regexp = "^\\+(?:[0-9] ?){6,14}[0-9]$", message = "Invalid phone number format")
    private String phoneNumber;

    @NotNull(message = "Employee type is required")
    private EmployeeType employeeType;



    @NotNull(message = "Department id is required")
    private Long departmentId;

    @NotNull
    private Gender gender;

    @NotNull(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one special character and one digit,")
    private String password;




    public EmployeeRequestDto( String firstName, String lastName, String email, String phoneNumber, EmployeeType employeeType,   Long departmentId,Gender gender,String password) {
    }
}
