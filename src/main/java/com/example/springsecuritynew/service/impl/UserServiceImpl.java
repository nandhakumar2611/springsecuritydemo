package com.example.springsecuritynew.service.impl;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.springsecuritynew.DTO.LoginResponse;
import com.example.springsecuritynew.DTO.RegistrationRequest;
import com.example.springsecuritynew.model.Role;
import com.example.springsecuritynew.model.User;
import com.example.springsecuritynew.repository.RoleRepository;
import com.example.springsecuritynew.repository.UserRepository;
import com.example.springsecuritynew.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public User registerUser(RegistrationRequest registrationRequest) {
		if (userRepository.existsByEmail(registrationRequest.getEmail())) {
			throw new IllegalStateException("Email Already persent");
		}
		User user = new User(registrationRequest.getName(), registrationRequest.getEmail(),
				passwordEncoder.encode(registrationRequest.getPassword()), registrationRequest.getPhoneNumber(),
				LocalDateTime.now(), null);

		Set<Role> roles = new HashSet<>();

		for (String role : registrationRequest.getRoles()) {
			Optional<Role> existingRole = roleRepository.findByRole(role);
			if (existingRole.isPresent()) {
				roles.add(existingRole.get());
			} else {
				Role defaultRole = roleRepository.findByRole("USER")
						.orElseThrow(() -> new IllegalStateException("Default role not found"));
				roles.add(defaultRole);
			}
		}

		user.setRoles(roles);
		userRepository.save(user);

		return user;
	}

	@Override
	public LoginResponse loginUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<User> findByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String resendLinkByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

}
