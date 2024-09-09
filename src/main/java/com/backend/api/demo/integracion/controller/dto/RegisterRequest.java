package com.backend.api.demo.integracion.controller.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.backend.api.demo.integracion.validation.password.PasswordValidator;

public class RegisterRequest implements Serializable {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7160713609744494972L;

	@NotEmpty(message = "no debe estar vacío")
    private String name;
    
    @Pattern(
        regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
        message = "no tiene el formato electrónico válido"
    )
    @NotEmpty(message = "no debe estar vacío")
    private String email;
    
//    @Pattern(
//            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+[\\]{};':\"\\\\|,.<>\\/?~`-])[^\\s]{8,15}$",
//            message = "no tiene el formato electrónico válido"
//        ) 
    @PasswordValidator(message = "La contraseña no cumple con los requisitos.")
    @NotEmpty(message = "no debe estar vacío")
    private String password;
    
    @NotEmpty(message = "no debe estar vacío")
    private List<PhoneDTO> phones;

    // Getters y Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<PhoneDTO> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneDTO> phones) {
        this.phones = phones;
    }

    public static class PhoneDTO {

        @NotEmpty(message = "El número no debe estar vacío")
        private String number;

        @NotEmpty(message = "El código de ciudad no debe estar vacío")
        private String citycode;

        @NotEmpty(message = "El código de país no debe estar vacío")
        private String contrycode;

        // Getters y Setters

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getCitycode() {
            return citycode;
        }

        public void setCitycode(String citycode) {
            this.citycode = citycode;
        }

        public String getContrycode() {
            return contrycode;
        }

        public void setContrycode(String contrycode) {
            this.contrycode = contrycode;
        }
    }
    
		
}