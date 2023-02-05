package edu.bu.cs633.minimalsearchengine.services;

import edu.bu.cs633.minimalsearchengine.exceptions.customExceptions.NotFoundException;
import edu.bu.cs633.minimalsearchengine.exceptions.customExceptions.PasswordDidNotMatchException;
import edu.bu.cs633.minimalsearchengine.exceptions.customExceptions.UserNameNotFoundException;
import edu.bu.cs633.minimalsearchengine.models.LoginDTO;
import edu.bu.cs633.minimalsearchengine.models.dao.Admin;
import edu.bu.cs633.minimalsearchengine.repositories.AdminRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(final AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public Admin getAdminByUsername(final LoginDTO loginDTO) {

        Admin admin = adminRepository.findByUsernameIgnoreCase(loginDTO.getUsername());

        if (admin == null) {
            throw new UserNameNotFoundException("username: " + loginDTO.getUsername() + " does not exists.");
        }

        if (!admin.getPassword().trim().equals(loginDTO.getPassword().trim())) {
            throw new PasswordDidNotMatchException("password did not match.");
        }

        return admin;
    }

    public Set<Admin> getAllAdminIndexingHistories(final String adminUsername) {

        Admin admin = adminRepository.findByUsernameIgnoreCase(adminUsername);

        if (admin == null || !admin.getUsername().trim().equalsIgnoreCase(adminUsername.trim())) {
            throw new UserNameNotFoundException("Given admin username is not found in the database.");
        }

        Set<Admin> adminsHistories = adminRepository
                .findAll()
                .stream()
                .filter(Objects::nonNull)
                .filter(adminWOIndexing -> adminWOIndexing.getIndexingHistories() != null)
                .filter(adminWOIndexing -> adminWOIndexing.getIndexingHistories().size() != 0)
                .collect(Collectors.toSet());

        if (adminsHistories.size() == 0) {
            throw new NotFoundException("No indexing histories found.");
        }

        return adminsHistories;
    }


}
