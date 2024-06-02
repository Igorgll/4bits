package com.bits.bits.service;

import com.bits.bits.model.EstoquistaModel;
import com.bits.bits.repository.EstoquistaRepository;
import com.bits.bits.security.AdminDetails;
import com.bits.bits.security.EstoquistaDetails;
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

    @Autowired
    private EstoquistaRepository estoquistaRepository;

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

        EstoquistaModel estoquista = estoquistaRepository.findEstoquistaByEmail(email).orElse(null);
        if (estoquista != null) {
            return new EstoquistaDetails(estoquista);
        }

        throw new UsernameNotFoundException("User not found with email: " + email);
    }
}
