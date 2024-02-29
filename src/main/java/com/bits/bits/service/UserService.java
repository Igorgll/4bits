package com.bits.bits.service;

import com.bits.bits.model.UserModel;
import com.bits.bits.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    public Optional<UserModel> userSignOn(UserModel user) {

        Optional<UserModel> findUser = userRepository.findUserByEmail(user.getEmail());

        if (findUser.isPresent()) {
            LOGGER.info("User already exists");
            return Optional.empty();
        }

        user.setActive(true);
        LOGGER.info("User successfully signed");

        return Optional.of(userRepository.save(user));
    }
}
