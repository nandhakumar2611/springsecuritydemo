package com.example.springsecuritynew.DTO;

import java.util.List;

public class LoginResponse {
	
	private String token;
	private Object data;
	private List<String> roles;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	
}
