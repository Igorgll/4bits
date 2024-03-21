package com.bits.bits.service;

import com.bits.bits.model.AdminModel;
import com.bits.bits.repository.AdminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class AdminService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    private AdminRepository adminRepository;

    public Optional<AdminModel> userSignUp(AdminModel user) {
        Optional<AdminModel> findUser = adminRepository.findUserByEmail(user.getEmail());

        if (findUser.isPresent()) {
            LOGGER.info("User already exists");
            return Optional.empty();
        }

        BCryptPasswordEncoder criptografar = new BCryptPasswordEncoder();
        String senhaCriptografada = criptografar.encode(user.getPassword());
        user.setPassword(senhaCriptografada);

        user.setActive(true);
        LOGGER.info("User successfully registered");

        return Optional.of(adminRepository.save(user));
    }

    public Optional<AdminModel> changeIsUserActive(Long userId, boolean isActive) {
        Optional<AdminModel> optionalUser = adminRepository.findById(userId);

        if (optionalUser.isPresent()) {
            AdminModel user = optionalUser.get();
            user.setActive(isActive);
            LOGGER.info("User status successfully changed");
            return Optional.of(adminRepository.save(user));
        }
        return Optional.empty();
    }

    public AdminModel updateUser(long userId, AdminModel adminModel) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Optional<AdminModel> findUser = adminRepository.findById(userId);
        if (findUser.isPresent()) {
            AdminModel existingUser = findUser.get();

            existingUser.setEmail(adminModel.getEmail());
            existingUser.setName(adminModel.getName());
            existingUser.setCpf(adminModel.getCpf());
            existingUser.setPassword(encoder.encode(adminModel.getPassword()));
            existingUser.setGroup(adminModel.getGroup());
            existingUser.setActive(true);

            return adminRepository.save(existingUser);
        }
        return null;
    }

}
