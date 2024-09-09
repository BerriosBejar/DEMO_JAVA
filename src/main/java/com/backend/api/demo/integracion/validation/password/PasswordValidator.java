package com.backend.api.demo.integracion.validation.password;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = PasswordValidatorImpl.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordValidator {

	String message() default "La contraseña no cumple con los requisitos.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
