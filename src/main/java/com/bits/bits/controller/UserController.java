package com.bits.bits.controller;

import com.bits.bits.dto.UserDTO;
import com.bits.bits.model.UserModel;
import com.bits.bits.repository.UserRepository;
import com.bits.bits.service.UserService;
import com.bits.bits.utils.FourBitsUtils;
import jakarta.validation.Valid;
import org.h2.engine.User;
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
        List<UserModel> userModelList = userRepository.findAll();
        if (!userModelList.isEmpty()){
            return ResponseEntity.ok(userModelList);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/listUsersBasicInfo")
    public ResponseEntity <List<UserDTO>> getAllUsersBasicInfo() {
        List<UserModel> userList = userRepository.findAll();
        if (!userList.isEmpty()) {
            List<UserDTO> userDTOS = FourBitsUtils.convertModelToUserDTO(userList);
            return ResponseEntity.ok(userDTOS);
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/signup")
    public ResponseEntity<UserModel> createUser(@Valid @RequestBody UserModel user) {
        return userService.userSignUp(user)
                .map(resp -> ResponseEntity.status(HttpStatus.OK)
                .body(resp)).orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @PatchMapping("/isUserActive/{userId}/{isActive}")
    public ResponseEntity<UserModel> isUserActive (@PathVariable Long userId, @PathVariable boolean isActive) {
       return userService.changeIsUserActive(userId, isActive)
               .map(resp -> ResponseEntity.status(HttpStatus.OK)
               .body(resp)).orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<UserModel>> getUserByName(@PathVariable(value = "name") String name) {
        List<UserModel> users = userRepository.findByNameContainingIgnoreCase(name);
        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(users);
    }


}
