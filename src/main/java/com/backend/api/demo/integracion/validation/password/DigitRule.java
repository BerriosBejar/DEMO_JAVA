package com.backend.api.demo.integracion.validation.password;

public class DigitRule implements PasswordRule {
	@Override
	public boolean isValid(String password, StringBuilder errorMessage) {
		if (password.chars().anyMatch(Character::isDigit)) {
			return true;
		}
		errorMessage.append("Debe contener al menos un dígito. ");
		return false;
	}
}