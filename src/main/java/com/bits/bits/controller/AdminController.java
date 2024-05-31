package com.bits.bits.controller;

import com.bits.bits.dto.AdminLoginDTO;
import com.bits.bits.exceptions.UserNotFoundException;
import com.bits.bits.model.AdminModel;
import com.bits.bits.repository.AdminRepository;
import com.bits.bits.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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

@RestController
@RequestMapping("/api/v1/admins")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdminController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AdminService adminService;

    @GetMapping
    public List<AdminModel> getAllAdmins() { return adminRepository.findAll(); }

    @GetMapping("/name/{name}")
    public List<AdminModel> getAdminByName(@PathVariable(value = "name") String name) {
        List<AdminModel> findAdmin = adminRepository.findByNameContainingIgnoreCase(name);
        if(findAdmin.isEmpty()) {
            throw new UserNotFoundException();
        }
        return findAdmin;
    }

    @GetMapping("/{adminId}")
    public ResponseEntity<AdminModel> getAdminById(@PathVariable Long adminId){
        return adminRepository.findById(adminId)
                .map(resp -> ResponseEntity.status(HttpStatus.OK)
                        .body(resp)).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/signup")
    public ResponseEntity<AdminModel> createAdmin(@Valid @RequestBody AdminModel admin) {
        return adminService.adminSignUp(admin)
                .map(resp -> ResponseEntity.status(HttpStatus.OK)
                        .body(resp)).orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @PutMapping("/updateAdmin/{adminId}")
    public ResponseEntity<AdminModel> updateAdmin(@PathVariable Long adminId, @Valid @RequestBody AdminModel admin) {
        AdminModel updateUser = adminService.updateAdmin(adminId, admin).getBody();
        return ResponseEntity.ok(updateUser);
    }

    @PatchMapping("/isAdminActive/{adminId}/{isActive}")
    public ResponseEntity<AdminModel> isUserActive(@PathVariable Long adminId, @RequestParam boolean isActive) {
        AdminModel updateUserStatus = adminService.changeIsAdminActive(adminId, isActive).getBody();
        return ResponseEntity.ok(updateUserStatus);
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticateAdmin(@RequestBody AdminLoginDTO adminLoginDTO, HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(adminLoginDTO.getEmail(), adminLoginDTO.getPassword()));
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("admin", adminLoginDTO.getEmail());
        response.setHeader("X-Auth-Token", httpSession.getId());

        return new ResponseEntity<>("Admin Login Successfully!.", HttpStatus.OK);
    }
}
