package com.backend.api.demo.integracion.service.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.backend.api.demo.integracion.controller.dto.LoginRequest;
import com.backend.api.demo.integracion.controller.dto.LoginResponse;
import com.backend.api.demo.integracion.controller.dto.RegisterRequest;
import com.backend.api.demo.integracion.controller.dto.RegisterResponse;
import com.backend.api.demo.integracion.entity.User;
import com.backend.api.demo.integracion.jwt.JwtService;
import com.backend.api.demo.integracion.service.user.UserService;

@Service
public class AuthService {
	
	@Autowired
	private UserService userService;

	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	public LoginResponse login(LoginRequest request) {
		//Verificar si las credenciales son correctas
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		} catch (BadCredentialsException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Credenciales incorrectas");
		}
		
		User username = userService.findOneByEmail(request.getEmail()).orElseThrow();
		String token = jwtService.generateToken(username, generateExtraClaims(username));
		
		LoginResponse loginResponse = new LoginResponse();
		loginResponse.setId(username.getId());
		loginResponse.setEmail(username.getUsername());
		loginResponse.setToken(token);
		
		return loginResponse;
	};
	
	public RegisterResponse register(RegisterRequest request) {
		// Verificar si el correo electrónico ya está registrado
        if (userService.findOneByEmail(request.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El correo ya está registrado");
        }
		
		 User savedUser = userService.create(request);
		 
		 //GENERAR TOKEN
		 String token = jwtService.generateToken(savedUser, generateExtraClaims(savedUser));
		 		 
		 RegisterResponse registerResponse = new RegisterResponse();
		 registerResponse.setId(savedUser.getId());
		 registerResponse.setCreated(savedUser.getCreated());
		 registerResponse.setModified(savedUser.getModified());
		 registerResponse.setLastLogin(savedUser.getLastLogin());
		 registerResponse.setToken(token);
		 registerResponse.setActive(savedUser.isEnabled());
        
        return registerResponse;
	}
	
	private Map<String, Object> generateExtraClaims(User user) {
		Map<String, Object> extraClaims = new HashMap<>();
		extraClaims.put("name", user.getName());
		extraClaims.put("role", user.getRole());
		extraClaims.put("authorities", user.getAuthorities());

		return extraClaims;
	}

}
