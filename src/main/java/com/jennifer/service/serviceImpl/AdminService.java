package com.jennifer.service.serviceImpl;

import com.jennifer.dto.request.CreateAdminRequest;
import com.jennifer.exception.UserAlreadyExistException;
import com.jennifer.exception.UserNotFoundException;
import com.jennifer.model.Admin;
import com.jennifer.repository.AdminRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;


    public String registerAdmin(CreateAdminRequest request) throws UserNotFoundException {
        Optional<Admin> admin = adminRepository.findByEmail(request.getEmail());
        if (admin.isPresent()) {
            throw new UserAlreadyExistException("User with email " + request.getEmail() + " already exists");

        }
        Admin newAdmin = new Admin();
        newAdmin.setEmail(request.getEmail());
        newAdmin.setPassword(request.getPassword());
        newAdmin.setUsername(request.getUsername());
        adminRepository.save(newAdmin);
        return "Admin registered successfully";
    }

    public String loginAdmin(String email, String password) {
        Optional<Admin> admin = adminRepository.findByEmail(email);
        if (admin.isEmpty()) {
            throw new UserNotFoundException("User with email " + email + " not found");
        }
        if (!admin.get().getPassword().equals(password)) {
            throw new UserNotFoundException("Invalid password");
        }
        return "Login successful";
    }

}
