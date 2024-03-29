package com.bits.bits.controller;

import com.bits.bits.dto.UserDTO;
import com.bits.bits.exceptions.UserNotFoundException;
import com.bits.bits.model.AdminModel;
import com.bits.bits.repository.AdminRepository;
import com.bits.bits.service.AdminService;
import com.bits.bits.utils.FourBitsUtils;
import jakarta.validation.Valid;
import org.hibernate.boot.model.internal.CopyIdentifierComponentSecondPass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
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
        List<AdminModel> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/listUsersBasicInfo")
    public ResponseEntity<List<UserDTO>> getAllUsersBasicInfo() {
        List<UserDTO> userList = userService.findAllUsersBasicInfo();
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<AdminModel>> getUserByName(@PathVariable(value = "name") String name) {
        List<AdminModel> users = userService.findUserByName(name);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Optional<AdminModel>> getUserById(@PathVariable long userId){
        Optional<AdminModel> user = userService.findUserById(userId);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/signup")
    public ResponseEntity<AdminModel> createAdmin(@Valid @RequestBody AdminModel user) {
        return userService.userSignUp(user)
                .map(resp -> ResponseEntity.status(HttpStatus.OK)
                        .body(resp)).orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @PutMapping("/updateUser/{userId}")
    public ResponseEntity<AdminModel> updateUser(@PathVariable long userId, @Valid @RequestBody AdminModel user) {
        AdminModel updateUser = userService.updateUser(userId, user);
        if(updateUser != null) {
            return ResponseEntity.ok(updateUser);
        }
        return ResponseEntity.badRequest().build();
    }

    @PatchMapping("/isUserActive/{userId}/{isActive}")
    public ResponseEntity<AdminModel> isUserActive(@PathVariable Long userId, @PathVariable boolean isActive) {
        return userService.changeIsUserActive(userId, isActive)
                .map(resp -> ResponseEntity.status(HttpStatus.OK)
                        .body(resp)).orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody UserDTO userDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword()));
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
            return new ResponseEntity<>("User Login Successfully!.", HttpStatus.OK);
        
    }
}
