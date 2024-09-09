package com.backend.api.demo.integracion.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.backend.api.demo.integracion.jwt.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Autowired
	private AuthenticationProvider authProvider;
	
	@Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	        return http
	                .authorizeHttpRequests(authRequest -> 
	                authRequest
	                	.antMatchers("/swagger-ui.html", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
	                    .antMatchers(HttpMethod.POST,"/auth/login").permitAll()
	                    .antMatchers(HttpMethod.POST,"/auth/register").permitAll()
	                    .anyRequest().authenticated())
	                .csrf(csrf -> 
	                    csrf
	                    .disable())
	                .sessionManagement(sessionManager -> 
	                					sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	                .authenticationProvider(authProvider)
	                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
	                .build();
	   }
	
}
