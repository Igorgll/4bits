package com.bits.bits.service;

import com.bits.bits.model.UserModel;
import com.bits.bits.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    public Optional<UserModel> userSignUp(UserModel user) {

        Optional<UserModel> findUser = userRepository.findUserByEmail(user.getEmail());

        if (findUser.isPresent()) {
            LOGGER.info("User already exists");
            return Optional.empty();
        }
        
        BCryptPasswordEncoder criptografar = new BCryptPasswordEncoder();
        String senhaCriptografada = criptografar.encode(user.getPassword());
        user.setPassword(senhaCriptografada);
        
        user.setActive(true);
        LOGGER.info("User successfully registered");

        return Optional.of(userRepository.save(user));
    }

    public Optional<UserModel> changeIsUserActive(Long userId, boolean isActive){
        Optional<UserModel> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            UserModel user = optionalUser.get();
            user.setActive(isActive);
            LOGGER.info("User status successfully changed");
            return Optional.of(userRepository.save(user));
        }
        return Optional.empty();
    }


}
