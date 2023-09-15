package com.example.springsecuritynew.security.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.springsecuritynew.model.User;

public class UserDetailsImpl implements UserDetails{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String name;
	
	private String email;
	
	private String password;
	
	private Long phoneNumber;
	
	private Boolean isEnabled = false;
	
	private Collection<? extends GrantedAuthority> authorities;
	
	public UserDetailsImpl(Long id, String name, String email, String password, Long phoneNumber, Boolean isEnabled,
			Collection<? extends GrantedAuthority> authorities) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.isEnabled = isEnabled;
		this.authorities = authorities;
	}

	public static UserDetailsImpl build(User user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getRole()))
				.collect(Collectors.toList());
		
		return new UserDetailsImpl(user.getId(),
									user.getName(), 
									user.getEmail(),
									user.getPassword(),
									user.getPhoneNumber(),
									user.getEnabled(), 
									authorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}
	
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Long getPhoneNumber() {
		return phoneNumber;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}

}
