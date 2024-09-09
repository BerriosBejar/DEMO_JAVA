package com.backend.api.demo.integracion.validation.constants;
public final class PasswordConstants {

    private PasswordConstants() {
    }

    public static final String REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%?&])[A-Za-z\\d$@$!%?&]{8,15}$";
}