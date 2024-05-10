package com.jennifer.repository;

import com.jennifer.model.Employee;
import com.jennifer.model.Organization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);
    Page<Employee> findEmployeeByDepartmentDepartmentName(String departmentName, Pageable pageable);
    Employee findEmployeeByDepartmentOrganization(Organization organization);

}
