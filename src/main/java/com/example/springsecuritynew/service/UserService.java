package com.example.springsecuritynew.service;

import java.util.Optional;

import com.example.springsecuritynew.DTO.LoginResponse;
import com.example.springsecuritynew.DTO.RegistrationRequest;
import com.example.springsecuritynew.model.User;

public interface UserService {

	User registerUser(RegistrationRequest registrationRequest);
	
	LoginResponse loginUser(User user);
	
	Optional<User> findByEmail(String email);
	
	String resendLinkByEmail(String email);
}
