package com.bits.bits.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bits.bits.model.AdminModel;
import com.bits.bits.repository.AdminRepository;

@Service
public class AdminDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private AdminRepository adminRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		AdminModel admin = adminRepository.findAdminByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("Admin not found with email: " + email));
		return new AdminDetails(admin);
	}
}