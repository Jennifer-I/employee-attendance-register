package com.jennifer.service.serviceImpl;

import com.jennifer.dto.request.AttendanceRequestDto;
import com.jennifer.dto.response.AttendanceResponseDto;
import com.jennifer.enums.AttendanceType;
import com.jennifer.exception.CustomException;
import com.jennifer.exception.UserNotFoundException;
import com.jennifer.model.AttendanceRecord;
import com.jennifer.model.Employee;
import com.jennifer.repository.AttendanceRepository;
import com.jennifer.repository.EmployeeRepository;
import com.jennifer.service.AttendanceRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AttendanceRecordServiceImplementation implements AttendanceRecordService {
    private final EmployeeRepository employeeRepository;
    private final AttendanceRepository attendanceRepository;
    private final ModelMapper modelMapper;




    private Employee getEmployeeById(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new UserNotFoundException("Employee not found"));
    }

    private List<AttendanceRecord> getAttendanceRecordsForToday(Employee employee) {
        return attendanceRepository.findByEmployeeAndDate(employee, LocalDate.now());
    }


    @Override
    public String registerAttendance(AttendanceRequestDto requestDto) {
        try {
            Employee employee = getEmployeeById(requestDto.getEmployeeId());

            log.info("Retrieving attendance records for the day");
            List<AttendanceRecord> existingRecords = getAttendanceRecordsForToday(employee);


            String error = checkAttendanceConstraints(requestDto, existingRecords);
            if (error != null) {
                return error;
            }

            log.info("Creating attendance record");
            AttendanceRecord attendanceRecord = createAttendanceRecord(requestDto, employee);
            attendanceRepository.save(attendanceRecord);

            return "Attendance registered successfully";
        } catch (UserNotFoundException ex) {

            return "Failed to register attendance: " + ex.getMessage();
        } catch (Exception ex) {
            return "Failed to register attendance due to an unexpected error";
        }
    }







    private String checkAttendanceConstraints(AttendanceRequestDto requestDto, List<AttendanceRecord> existingRecords) {
        AttendanceType attendanceType = requestDto.getAttendanceType();

        log.info("Checking for sign-in records for the day");
        boolean hasSignInRecord = existingRecords.stream().anyMatch(record -> record.getSignInTime() != null);

        log.info("Checking for sign-out records for the day");
        boolean hasSignOutRecord = existingRecords.stream().anyMatch(record -> record.getSignOutTime() != null);

        log.info("Checking for absence or leave records for the day");
        boolean hasAbsenceRecord = existingRecords.stream().anyMatch(AttendanceRecord::isAbsent);
        boolean hasLeaveRecord = existingRecords.stream().anyMatch(AttendanceRecord::isOnLeave);

        log.info("Checking constraints checks based on attendance type");
        switch (attendanceType) {
            case SIGN_IN:
                if (hasSignInRecord) {
                    return "Sign-in already recorded for the day";
                }
                if (hasAbsenceRecord || hasLeaveRecord) {
                    return "Cannot sign in on a day with recorded absence or leave";
                }
                break;
            case SIGN_OUT:
                if (!hasSignInRecord) {
                    return "Cannot sign out without signing in first";
                }
                if (hasSignOutRecord) {
                    return "Sign-out already recorded for the day";
                }
                if (hasAbsenceRecord || hasLeaveRecord) {
                    return "Cannot sign out on a day with recorded absence or leave";
                }
                break;
            case ABSENT:
            case SICK_LEAVE:
                if (hasSignInRecord || hasSignOutRecord) {
                    return "Cannot record absence or leave on a day with signed attendance";
                }
                break;
            default:
                break;
        }
        return null;
    }


    private AttendanceRecord createAttendanceRecord(AttendanceRequestDto requestDto, Employee employee) {
        AttendanceRecord attendanceRecord = new AttendanceRecord();
        attendanceRecord.setEmployee(employee);
        attendanceRecord.setDate(LocalDate.now());
        attendanceRecord.setAttendanceType(requestDto.getAttendanceType());

        log.info("Setting attendance details based on attendance type");
        switch (requestDto.getAttendanceType()) {
            case SIGN_IN:
                attendanceRecord.setSignInTime(LocalTime.now());
                attendanceRecord.setPresent(true);
                break;
            case SIGN_OUT:
                attendanceRecord.setSignOutTime(LocalTime.now());
                attendanceRecord.setPresent(true);
                break;
            case ABSENT:
                attendanceRecord.setAbsent(true);
                break;
            case SICK_LEAVE:
                attendanceRecord.setOnLeave(true);
                break;
            default:
                break;
        }

        return attendanceRecord;
    }



    @Override
    public Page<AttendanceResponseDto> getAttendanceRecordsForEmployeeByDateRange(Long employeeId, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        try {
            Employee employee = getEmployeeById(employeeId);

            log.info("Retrieving attendance records for the employee within the specified date range");
            Page<AttendanceRecord> attendanceRecordsPage = attendanceRepository.findByEmployeeAndDateBetween(employee, startDate, endDate, pageable);

            log.info("Mapping attendance records to DTOs");
            return attendanceRecordsPage.map(AttendanceResponseDto::new);
        } catch (UserNotFoundException ex) {
            throw new CustomException("Failed to fetch attendance records: " + ex.getMessage());
        } catch (Exception ex) {
            throw new CustomException("Failed to fetch attendance records due to an unexpected error", ex);
        }
    }


    }








