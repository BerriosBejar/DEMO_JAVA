package com.backend.api.demo.integracion.service.user;

import java.util.Optional;

import com.backend.api.demo.integracion.controller.dto.RegisterRequest;
import com.backend.api.demo.integracion.entity.User;


public interface UserService {

	public abstract User create(RegisterRequest request);
	public abstract Optional<User> findOneByEmail(String email);

}
