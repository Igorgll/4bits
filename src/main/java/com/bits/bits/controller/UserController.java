package com.bits.bits.controller;

import com.bits.bits.dto.AddressDTO;
import com.bits.bits.dto.ClientDTO;
import com.bits.bits.dto.UserLoginDTO;
import com.bits.bits.model.UserModel;
import com.bits.bits.repository.UserRepository;
import com.bits.bits.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.h2.engine.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<UserModel> getAllUsers (){ return userRepository.findAll(); }

    @GetMapping("/email/{email}")
    public ResponseEntity<Optional<UserModel>> getUserByEmail(@Valid @PathVariable String email){
        Optional<UserModel> user = userRepository.findUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/updateUser/{userId}")
    public ResponseEntity<UserModel> updateUser(@PathVariable Long userId, @Valid @RequestBody ClientDTO user) {
        UserModel updateUser = userService.updateUser(user, userId).getBody();
        return ResponseEntity.ok(updateUser);
    }

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

        //até aqui os dados do json são passados
        userService.addDeliveryAddress(userId, addressDTO);
        return new ResponseEntity<>("Delivery address added successfully", HttpStatus.OK);
    }

    @PostMapping("/clientLogin")
    public ResponseEntity<String> authenticateClient(@RequestBody UserLoginDTO userLoginDTO, HttpServletRequest request, HttpServletResponse response) {
        userService.authenticateUser(userLoginDTO, request, response);
        return new ResponseEntity<>("User Login Successfully!", HttpStatus.OK);
    }

    @PostMapping("/clientLogout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession httpSession = request.getSession(false);
            if (httpSession != null) {
                httpSession.invalidate();
            }
            return new ResponseEntity<>("User logged out successfully!", HttpStatus.OK);
        }
    }

