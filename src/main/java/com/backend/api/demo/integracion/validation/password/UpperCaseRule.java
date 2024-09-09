package com.backend.api.demo.integracion.validation.password;

public class UpperCaseRule implements PasswordRule {
	@Override
	public boolean isValid(String password, StringBuilder errorMessage) {
		if (password.chars().anyMatch(Character::isUpperCase)) {
			return true;
		}
		errorMessage.append("Debe contener al menos una letra mayúscula. ");
		return false;
	}
}