package com.bits.bits.controller;

import com.bits.bits.dto.UserDTO;
import com.bits.bits.dto.UserUpdateRequestDTO;
import com.bits.bits.model.AdminModel;
import com.bits.bits.repository.AdminRepository;
import com.bits.bits.service.AdminService;
import com.bits.bits.utils.FourBitsUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/users")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdminController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AdminRepository userRepository;

    @Autowired
    private AdminService userService;

    @GetMapping
    public ResponseEntity<List<AdminModel>> getAllUsers() {
        List<AdminModel> userModelList = userRepository.findAll();
        if (!userModelList.isEmpty()) {
            return ResponseEntity.ok(userModelList);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/listUsersBasicInfo")
    public ResponseEntity<List<UserDTO>> getAllUsersBasicInfo() {
        List<AdminModel> userList = userRepository.findAll();
        if (!userList.isEmpty()) {
            List<UserDTO> userDTOS = FourBitsUtils.convertModelToUserDTO(userList);
            return ResponseEntity.ok(userDTOS);
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/signup")
    public ResponseEntity<AdminModel> createAdmin(@Valid @RequestBody AdminModel user) {
        return userService.userSignUp(user)
                .map(resp -> ResponseEntity.status(HttpStatus.OK)
                        .body(resp)).orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @PutMapping("/updateUser/{userId}")
    public ResponseEntity<AdminModel> updateUser(@PathVariable Long userId, @Valid @RequestBody UserUpdateRequestDTO user) {
        // não deixar alterar o email
        return userService.updateUser(userId, user)
                .map(resp -> ResponseEntity.status(HttpStatus.OK)
                        .body(resp)).orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @PatchMapping("/isUserActive/{userId}/{isActive}")
    public ResponseEntity<AdminModel> isUserActive(@PathVariable Long userId, @PathVariable boolean isActive) {
        return userService.changeIsUserActive(userId, isActive)
                .map(resp -> ResponseEntity.status(HttpStatus.OK)
                        .body(resp)).orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<AdminModel>> getUserByName(@PathVariable(value = "name") String name) {
        List<AdminModel> users = userRepository.findByNameContainingIgnoreCase(name);
        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(users);
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody UserDTO userDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword()));
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
            return new ResponseEntity<>("User Login Successfully!.", HttpStatus.OK);
        
    }
}
