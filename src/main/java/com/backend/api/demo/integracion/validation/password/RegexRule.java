package com.backend.api.demo.integracion.validation.password;

import com.backend.api.demo.integracion.validation.constants.PasswordConstants;

public class RegexRule implements PasswordRule {

	private final String regex;
	
    public RegexRule() {
        this.regex = PasswordConstants.REGEX;
    }

    @Override
    public boolean isValid(String password, StringBuilder errorMessage) {
        if (password.matches(regex)) {
            return true;
        }
        errorMessage.append("no cumple con los requisitos de la expresión regular. ");
        return false;
    }
	
	
}