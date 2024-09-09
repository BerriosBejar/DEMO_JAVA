package com.backend.api.demo.integracion.controller.dto;

import java.util.UUID;

public class LoginResponse {

	private UUID id;
    private String email;
    private String token;
    
    public LoginResponse() {
	}
    
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}