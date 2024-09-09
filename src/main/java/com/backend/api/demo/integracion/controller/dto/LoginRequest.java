package com.backend.api.demo.integracion.controller.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

public class LoginRequest implements Serializable {	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4982464477969711033L;

	@NotEmpty(message = "no debe estar vacío")
	String email;

	@NotEmpty(message = "no debe estar vacío")
	String password;
	
	public LoginRequest(String email, String password) {
		this.email = email;
		this.password = password;
	}
	
	public LoginRequest() {
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
		
}