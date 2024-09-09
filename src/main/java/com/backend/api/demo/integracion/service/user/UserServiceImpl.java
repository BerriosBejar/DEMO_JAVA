package com.backend.api.demo.integracion.service.user;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.api.demo.integracion.controller.dto.RegisterRequest;
import com.backend.api.demo.integracion.entity.Phone;
import com.backend.api.demo.integracion.entity.Role;
import com.backend.api.demo.integracion.entity.User;
import com.backend.api.demo.integracion.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public User create(RegisterRequest request) {
		User user = new User();
		user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreated(new Date());
        user.setModified(new Date());
        user.setLastLogin(new Date());
        user.setActive(user.isEnabled());
		user.setRole(Role.USER);
				
		List<Phone> phones = request.getPhones().stream().map(dto -> {
		    Phone phone = new Phone();
		    phone.setNumber(dto.getNumber());
		    phone.setCitycode(dto.getCitycode());
		    phone.setContrycode(dto.getContrycode());
		    phone.setUser(user); // Associate the phone with the user
		    return phone;
		}).collect(Collectors.toList());
        
		 user.setPhones(phones);
			 
		 return userRepository.save(user);
	}
	
	@Override
	public Optional<User> findOneByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	

	
}
