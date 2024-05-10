package com.jennifer.service;

import com.jennifer.dto.request.AttendanceRequestDto;
import com.jennifer.dto.response.AttendanceResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface AttendanceRecordService {
    String registerAttendance(AttendanceRequestDto requestDto);



    Page<AttendanceResponseDto> getAttendanceRecordsForEmployeeByDateRange(Long employeeId, LocalDate startDate, LocalDate endDate, Pageable pageable);
}
