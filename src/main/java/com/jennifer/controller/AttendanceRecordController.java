package com.jennifer.controller;

import com.jennifer.dto.request.AttendanceRequestDto;
import com.jennifer.dto.response.ApiResponse;
import com.jennifer.dto.response.AttendanceResponseDto;
import com.jennifer.dto.response.EmployeeResponseDto;
import com.jennifer.service.AttendanceRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/attendance")
public class AttendanceRecordController {
    private final AttendanceRecordService attendanceService;

    @PostMapping("/registerAttendance")
    public ResponseEntity<ApiResponse<String>> registerAttendance(@RequestBody AttendanceRequestDto requestDto) {
        String response = attendanceService.registerAttendance(requestDto);
        return ResponseEntity.ok(new ApiResponse<>("success", response, HttpStatus.OK));
    }
    @GetMapping("/getAttendanceForEmployeeByDateRange")
    public ResponseEntity<ApiResponse<Page<AttendanceResponseDto>>> getAttendanceForEmployeeByDateRange(@RequestParam Long employeeId, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate, Pageable pageable){
        Page<AttendanceResponseDto> employeePage = attendanceService.getAttendanceRecordsForEmployeeByDateRange(employeeId,startDate,endDate, pageable);
        return ResponseEntity.ok(new ApiResponse<>("success",employeePage, HttpStatus.OK));
    }
    }






