package com.backend.api.demo.integracion.validation.password;

public interface PasswordRule {
    boolean isValid(String password, StringBuilder errorMessage);
}
