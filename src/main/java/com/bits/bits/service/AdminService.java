package com.bits.bits.service;

import com.bits.bits.exceptions.CannotAccessException;
import com.bits.bits.exceptions.UserNotFoundException;
import com.bits.bits.model.AdminModel;
import com.bits.bits.repository.AdminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    private AdminRepository adminRepository;

    public Optional<AdminModel> adminSignUp(AdminModel admin) {
        Optional<AdminModel> findAdmin = adminRepository.findAdminByEmail(admin.getEmail());

        if (findAdmin.isPresent()) {
            LOGGER.info("Admin already exists");
            throw new CannotAccessException();
        }

        BCryptPasswordEncoder criptografar = new BCryptPasswordEncoder();
        String senhaCriptografada = criptografar.encode(admin.getPassword());
        admin.setPassword(senhaCriptografada);

        admin.setActive(true);
        LOGGER.info("Admin successfully registered");

        return Optional.of(adminRepository.save(admin));
    }

    public ResponseEntity<AdminModel> changeIsAdminActive(Long adminId, boolean isActive) {
        Optional<AdminModel> findAdmin = adminRepository.findById(adminId);

        if (findAdmin.isPresent()) {
            AdminModel user = findAdmin.get();
            user.setActive(isActive);
            LOGGER.info("Admin status successfully changed");
            return ResponseEntity.ok(adminRepository.save(user));
        } else {
            LOGGER.error("Admin not found with ID: " + adminId);
            throw new UserNotFoundException();
        }
    }

    public ResponseEntity<AdminModel> updateAdmin(Long adminId, AdminModel adminModel) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Optional<AdminModel> findAdmin = adminRepository.findById(adminId);
        if (findAdmin.isPresent()) {
            AdminModel existingAdmin = findAdmin.get();

            existingAdmin.setEmail(adminModel.getEmail());
            existingAdmin.setName(adminModel.getName());
            existingAdmin.setCpf(adminModel.getCpf());
            existingAdmin.setPassword(encoder.encode(adminModel.getPassword()));
            existingAdmin.setGroup(adminModel.getGroup());
            existingAdmin.setActive(true);

            return ResponseEntity.ok(adminRepository.save(existingAdmin));
        }
        return ResponseEntity.badRequest().build();
    }
}
