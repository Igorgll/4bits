package com.bits.bits.controller;

import com.bits.bits.dto.AddressDTO;
import com.bits.bits.dto.ClientDTO;
import com.bits.bits.model.UserModel;
import com.bits.bits.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.Authenticator;

@RestController
@RequestMapping("api/v1/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @PostMapping("/createUser")
    public ResponseEntity<UserModel> createUser(@Valid @RequestBody UserModel user) {
        return userService.userSignUp(user)
                .map(resp -> ResponseEntity.status(HttpStatus.OK)
                        .body(resp)).orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @PostMapping("/{userId}/deliveryAddress")
    public ResponseEntity<String> addDeliveryAddress(
            @PathVariable("userId") Long userId,
            @RequestBody AddressDTO addressDTO) {

        userService.addDeliveryAddress(userId, addressDTO.getCep());
        return new ResponseEntity<>("Delivery address added successfully", HttpStatus.OK);
    }

    @PostMapping("/clientLogin")
    public ResponseEntity<String> authenticateClient(@RequestBody ClientDTO clientDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(clientDTO.getEmail(), clientDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User Login Successfully!", HttpStatus.OK);
        }
    }
