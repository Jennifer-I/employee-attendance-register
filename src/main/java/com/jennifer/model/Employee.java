package com.jennifer.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.jennifer.enums.EmployeeType;
import com.jennifer.enums.Gender;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "employees")
@Entity
public class Employee {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private String phoneNumber;

        private String password;

        @Enumerated(EnumType.STRING)
        private EmployeeType employeeType;

        @Enumerated(EnumType.STRING)
        private Gender gender;

        @ManyToOne
        @JoinColumn(name = "department_id")
        private Department department;


        public Employee(String firstName, String lastName, String email, String phoneNumber, String password,
                        EmployeeType employeeType,Long id) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.phoneNumber = phoneNumber;
            this.password = password;
            this.employeeType = employeeType;
        }

        public Employee(long l, String firstName, String lastName , String mail,String password , EmployeeType employeeType, Gender gender, Department department) {
        }
}
