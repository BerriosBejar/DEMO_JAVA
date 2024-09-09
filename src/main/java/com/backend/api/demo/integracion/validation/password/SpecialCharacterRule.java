package com.backend.api.demo.integracion.validation.password;

public class SpecialCharacterRule implements PasswordRule {

	private static final String SPECIAL_CHARACTERS = "!@#$%^&*()-_=+[]{}|;:',.<>?/";

	@Override
	public boolean isValid(String password, StringBuilder errorMessage) {
		if (password.chars().anyMatch(ch -> SPECIAL_CHARACTERS.indexOf(ch) >= 0)) {
			return true;
		}
		errorMessage.append("Debe contener al menos un carácter especial. ");
		return false;
	}
}