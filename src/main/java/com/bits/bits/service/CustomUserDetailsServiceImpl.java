package com.bits.bits.service;

import com.bits.bits.security.AdminDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bits.bits.model.AdminModel;
import com.bits.bits.model.UserModel;
import com.bits.bits.repository.AdminRepository;
import com.bits.bits.repository.UserRepository;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AdminModel admin = adminRepository.findAdminByEmail(email).orElse(null);
        if (admin != null) {
            return new AdminDetails(admin);
        }

        UserModel user = userRepository.findUserByEmail(email).orElse(null);
        if (user != null) {
            return new com.bits.bits.security.UserDetails(user);
        }

        throw new UsernameNotFoundException("User not found with email: " + email);
    }
}
