package com.jennifer.dto.response;

import com.jennifer.model.AttendanceRecord;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
public class AttendanceResponseDto {
    private Long EmployeeId;
    private LocalDate date;
    private LocalTime sigInTime;
    private LocalTime signOutTime;
    private boolean isAbsent;
    private boolean isOnLeave;
    private boolean isPresent;

    public AttendanceResponseDto(AttendanceRecord attendanceRecord){
        this.EmployeeId = attendanceRecord.getEmployee().getId();
        this.date= attendanceRecord.getDate();
        this.sigInTime = attendanceRecord.getSignInTime();
        this.signOutTime = attendanceRecord.getSignOutTime();
        this.isOnLeave = attendanceRecord.isOnLeave();
        this.isPresent = attendanceRecord.isPresent();
        this.isAbsent = attendanceRecord.isAbsent();

    }

}
