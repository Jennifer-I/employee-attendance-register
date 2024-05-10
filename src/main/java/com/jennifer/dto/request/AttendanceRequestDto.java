package com.jennifer.dto.request;

import com.jennifer.enums.AttendanceType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class AttendanceRequestDto {

    @NotNull(message = "Employee id is required")
    private Long employeeId;
    private AttendanceType attendanceType;
    private LocalDate startDate;
    private LocalDate endDate;

}
