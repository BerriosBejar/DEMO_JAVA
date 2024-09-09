package com.backend.api.demo.integracion.validation.password;

public class NoSpaceRule implements PasswordRule {
	@Override
	public boolean isValid(String password, StringBuilder errorMessage) {
		if (!password.contains(" ")) {
			return true;
		}
		errorMessage.append("No debe contener espacios en blanco. ");
		return false;
	}
}