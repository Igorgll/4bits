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

    public Optional<AdminModel> updateUser(AdminModel user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (adminRepository.findById(user.getAdminId()).isPresent()) {
            Optional<AdminModel> findUser = adminRepository.findUserByEmail(user.getName());
            if (findUser.isPresent()) {
                if (findUser.get().getAdminId() != user.getAdminId())
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

            }
            user.setPassword(encoder.encode(user.getPassword()));
            return Optional.of(adminRepository.save(user));
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }


}
