package com.backend.api.demo.integracion.test.password;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.validation.ConstraintValidatorContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.backend.api.demo.integracion.validation.password.PasswordValidatorImpl;

public class PasswordValidatorImplTest {

	@InjectMocks
    private PasswordValidatorImpl passwordValidator;

    @Mock
    private ConstraintValidatorContext context;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Order(1)
    @Test
    public void whenPasswordIsValid_thenValidationSucceeds() {
    	passwordValidator.initialize(null); 
    	
        String validPassword = "Valid123!";

        boolean result = passwordValidator.isValid(validPassword, null); // No necesitamos el contexto de validación para este test

        assertTrue(result, "La contraseña debería ser válida");
    }

    @Order(2)
    @Test
    public void whenPasswordIsTooShort_thenValidationFails() {
    	passwordValidator.initialize(null); 
        String shortPassword = "Sh1!";

        // Mock para ConstraintViolationBuilder
        ConstraintValidatorContext.ConstraintViolationBuilder violationBuilder = mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(violationBuilder);
        when(violationBuilder.addConstraintViolation()).thenReturn(context);

        boolean result = passwordValidator.isValid(shortPassword, context);

        assertFalse(result, "La validación debería fallar con una contraseña demasiado corta");
    }
    
    @Order(3)
    @Test
    public void whenPasswordHasNoSpecialCharacter_thenValidationFails() {
    	passwordValidator.initialize(null); 
    	
        // Contraseña que no contiene caracteres especiales
        String noSpecialCharPassword = "Valid123";

        // Mock para ConstraintViolationBuilder
        ConstraintValidatorContext.ConstraintViolationBuilder violationBuilder = mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(violationBuilder);
        when(violationBuilder.addConstraintViolation()).thenReturn(context);

        boolean result = passwordValidator.isValid(noSpecialCharPassword, context);

        assertFalse(result, "La validación debería fallar con una contraseña sin caracteres especiales");
    }

    @Order(4)
    @Test
    public void whenPasswordHasNoUpperCase_thenValidationFails() {
    	passwordValidator.initialize(null); 
    	
        // Contraseña que no contiene letras mayúsculas
        String noUpperCasePassword = "valid123!";

        // Mock para ConstraintViolationBuilder
        ConstraintValidatorContext.ConstraintViolationBuilder violationBuilder = mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(violationBuilder);
        when(violationBuilder.addConstraintViolation()).thenReturn(context);

        boolean result = passwordValidator.isValid(noUpperCasePassword, context);

        assertFalse(result, "La validación debería fallar con una contraseña sin mayúsculas");
    }

    @Order(5)
    @Test
    public void whenPasswordHasNoLowerCase_thenValidationFails() {
    	passwordValidator.initialize(null); 
    	
        // Contraseña que no contiene letras minúsculas
        String noLowerCasePassword = "VALID123!";

        // Mock para ConstraintViolationBuilder
        ConstraintValidatorContext.ConstraintViolationBuilder violationBuilder = mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(violationBuilder);
        when(violationBuilder.addConstraintViolation()).thenReturn(context);

        boolean result = passwordValidator.isValid(noLowerCasePassword, context);

        assertFalse(result, "La validación debería fallar con una contraseña sin minúsculas");
    }

    @Order(6)
    @Test
    public void whenPasswordHasNoDigit_thenValidationFails() {
    	passwordValidator.initialize(null); 
    	
        // Contraseña que no contiene dígitos
        String noDigitPassword = "Valid!!!";

        // Mock para ConstraintViolationBuilder
        ConstraintValidatorContext.ConstraintViolationBuilder violationBuilder = mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(violationBuilder);
        when(violationBuilder.addConstraintViolation()).thenReturn(context);

        boolean result = passwordValidator.isValid(noDigitPassword, context);

        assertFalse(result, "La validación debería fallar con una contraseña sin dígitos");
    }

    @Order(7)
    @Test
    public void whenPasswordContainsSpace_thenValidationFails() {
    	passwordValidator.initialize(null); 
    	
        // Contraseña que contiene espacios
        String spacePassword = "Valid 123!";

        // Mock para ConstraintViolationBuilder
        ConstraintValidatorContext.ConstraintViolationBuilder violationBuilder = mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(violationBuilder);
        when(violationBuilder.addConstraintViolation()).thenReturn(context);

        boolean result = passwordValidator.isValid(spacePassword, context);

        assertFalse(result, "La validación debería fallar con una contraseña que contiene espacios");
    }

    @Order(8)
    @Test
    public void whenPasswordIsNull_thenValidationFails() {
    	passwordValidator.initialize(null); 
    	
        // Contraseña nula
        boolean result = passwordValidator.isValid(null, context);

        assertFalse(result, "La validación debería fallar con una contraseña nula");
    }
}