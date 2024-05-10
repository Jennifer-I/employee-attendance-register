package com.jennifer.service.serviceImpl;

import com.jennifer.dto.request.CreateDepartmentRequest;
import com.jennifer.dto.response.DepartmentResponseDto;
import com.jennifer.exception.CustomException;
import com.jennifer.model.Department;
import com.jennifer.model.Organization;
import com.jennifer.repository.DepartmentRepository;
import com.jennifer.repository.OrganizationRepository;
import com.jennifer.service.DepartmentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final OrganizationRepository organizationRepository;


    @Override
    public DepartmentResponseDto createDepartment(CreateDepartmentRequest departmentRequestDto) throws CustomException {
        try {

            log.info("checking if department with same name already exist");
            Optional<Department> existingDepartment = departmentRepository.findByDepartmentName(departmentRequestDto.getDepartmentName());
            if (existingDepartment.isPresent()) {
                throw new CustomException("Department with the name '" + departmentRequestDto.getDepartmentName() + "' already exists");
            }

            log.info("retrieving organization");
            Organization organization = organizationRepository.findById(departmentRequestDto.getOrganizationId())
                    .orElseThrow(() -> new CustomException("Organization not found"));

            Department department = Department.builder()
                    .departmentName(departmentRequestDto.getDepartmentName())
                    .organization(organization)
                    .build();
            departmentRepository.save(department);

            DepartmentResponseDto responseDto = new DepartmentResponseDto();
            responseDto.setDepartmentName(department.getDepartmentName());
            responseDto.setOrganizationId(String.valueOf(department.getOrganization().getId()));
            return responseDto;


        } catch (Exception ex) {
            log.error("Failed to create department", ex);
            throw new CustomException("Failed to create department due to database error", ex);
        }
    }






}
