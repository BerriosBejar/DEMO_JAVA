package com.backend.api.demo.integracion.validation.password;

public class LowerCaseRule implements PasswordRule {
	@Override
	public boolean isValid(String password, StringBuilder errorMessage) {
		if (password.chars().anyMatch(Character::isLowerCase)) {
			return true;
		}
		errorMessage.append("Debe contener al menos una letra minúscula. ");
		return false;
	}
}