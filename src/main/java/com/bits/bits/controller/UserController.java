package com.bits.bits.controller;

import com.bits.bits.model.UserModel;
import com.bits.bits.repository.UserRepository;
import com.bits.bits.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/users")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity <List<UserModel>> getAllUsers(){
        return ResponseEntity.ok(userRepository.findAll());
    }

    @PostMapping("/signin")
    public ResponseEntity<UserModel> createUser(@Valid @RequestBody UserModel user) {

        return userService.userSignOn(user)
                .map(resp -> ResponseEntity.status(HttpStatus.OK)
                .body(resp)).orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }
}
