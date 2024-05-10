package com.jennifer.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String departmentName;

    @OneToMany(mappedBy = "department")
    private List<Employee> employee;
    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;


    public Department(String departmentName) {
        this.departmentName = departmentName;
    }
}
