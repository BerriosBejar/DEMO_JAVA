package com.backend.api.demo.integracion.validation.password;

public class LengthRule implements PasswordRule {
	@Override
	public boolean isValid(String password, StringBuilder errorMessage) {
		if (password.length() >= 8 && password.length() <= 15) {
			return true;
		}
		errorMessage.append("Debe tener una longitud entre 8 y 15 caracteres. ");
		return false;
	}
}