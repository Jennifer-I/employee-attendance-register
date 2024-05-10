package com.jennifer.model;

import com.jennifer.enums.AttendanceType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "attendance_records")
@Entity
public class AttendanceRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private LocalDate date;

    private LocalTime signInTime;

    private LocalTime signOutTime;


    private boolean isPresent;
    private boolean isAbsent;
    private boolean isOnLeave;


    @Enumerated(EnumType.STRING)
    private AttendanceType attendanceType;
}
