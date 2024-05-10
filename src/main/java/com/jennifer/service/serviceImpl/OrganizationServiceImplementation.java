package com.jennifer.service.serviceImpl;

import com.jennifer.dto.request.OrganizationRequestDto;
import com.jennifer.dto.response.OrganizationResponseDto;
import com.jennifer.exception.CustomException;
import com.jennifer.model.Organization;
import com.jennifer.repository.OrganizationRepository;
import com.jennifer.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrganizationServiceImplementation implements OrganizationService {
    private final OrganizationRepository organizationRepository;



    @Override
    public OrganizationResponseDto createOrganization(OrganizationRequestDto organizationRequestDto) throws CustomException {
        try {


            log.info("checking if organization name already exist");
            Optional<Organization> existingOrganization = organizationRepository.findByOrganizationName(organizationRequestDto.getOrganizationName());
            if (existingOrganization.isPresent()) {throw new CustomException("Organization with the name '" + organizationRequestDto.getOrganizationName() + "' already exists");
            }

            Organization organization = Organization
                    .builder()
                    .organizationName(organizationRequestDto.getOrganizationName())
                    .organizationAddress(organizationRequestDto.getOrganizationAddress())
                    .build();
            organizationRepository.save(organization);

            OrganizationResponseDto responseDto = new OrganizationResponseDto();
            responseDto.setOrganizationName(organization.getOrganizationName());
            responseDto.setOrganizationAddress(organization.getOrganizationAddress());
            return responseDto;
        } catch (CustomException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to create organization", ex);
            throw new CustomException("Failed to create organization due to database error", ex);
        }
    }


}