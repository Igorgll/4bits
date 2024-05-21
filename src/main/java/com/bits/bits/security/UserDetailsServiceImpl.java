package com.bits.bits.security;

import java.util.Optional;

import com.bits.bits.model.UserModel;
import com.bits.bits.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.bits.bits.model.AdminModel;
import com.bits.bits.repository.AdminRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private UserRepository userRepository;

	public UserDetailsServiceImpl(UserRepository userRepository, AdminRepository adminRepository) {
		this.userRepository = userRepository;
		this.adminRepository = adminRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Optional<UserModel> client = userRepository.findUserByEmail(userName);
		if (client != null) {
			return new ClientDetails(client.get());
		}

		Optional<AdminModel> admin = adminRepository.findUserByEmail(userName);

		if (admin != null) {
			return new AdminDetails(admin.get());
		}

		throw new UsernameNotFoundException("User not found");
			
	}


}