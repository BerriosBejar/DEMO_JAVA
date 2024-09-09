package com.backend.api.demo.integracion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.reactive.server.WebTestClient;
//import org.springframework.test.web.servlet.MockMvc;

import com.backend.api.demo.integracion.controller.dto.LoginRequest;
import com.backend.api.demo.integracion.controller.dto.LoginResponse;
import com.backend.api.demo.integracion.controller.dto.RegisterRequest;
import com.backend.api.demo.integracion.controller.dto.RegisterResponse;
import com.backend.api.demo.integracion.entity.Role;
import com.backend.api.demo.integracion.entity.User;
import com.backend.api.demo.integracion.jwt.JwtService;
//import com.backend.api.demo.integracion.service.auth.AuthService;
import com.backend.api.demo.integracion.service.user.UserService;
import com.backend.api.demo.integracion.controller.dto.RegisterRequest.PhoneDTO;
//import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;

//@SpringBootTest
//@AutoConfigureMockMvc
//@TestMethodOrder(OrderAnnotation.class)
@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ProyectoDemoIntegracionApplicationTests {
	
	@Autowired
	private WebTestClient cliente;

	@Autowired
	private UserService userService;

//	 @Autowired
//	 private MockMvc mockMvc;
//
//	 @Autowired
//	 private ObjectMapper objectMapper;

	@Autowired
    private JwtService jwtService;

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    private User createNewUser(String email, String password, Role role) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
        return user;
    }

    private RegisterRequest createNewRegisterRequest(String name, String email, String password) {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setName(name);
        registerRequest.setEmail(email);
        registerRequest.setPassword(password);

        List<PhoneDTO> phones = new ArrayList<>();
        PhoneDTO phone = new PhoneDTO();
        phone.setNumber("1234567");
        phone.setCitycode("1");
        phone.setContrycode("57");
        phones.add(phone);

        registerRequest.setPhones(phones);
        return registerRequest;
    }

    @Order(1)
    @Test
    void registerTest() {
        RegisterRequest registerRequest = createNewRegisterRequest("Juan Rodriguez", "juan@rodriguez.org", "Hunter2@");

        cliente.post().uri("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(registerRequest)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(RegisterResponse.class);
    }

    @Order(2)
    @Test
    void loginTest() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("juan@rodriguez.org");
        loginRequest.setPassword("Hunter2@");

        cliente.post().uri("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(loginRequest)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(LoginResponse.class);
    }

    @Order(3)
    @Test
    void findOneByEmailTest() {
        Optional<User> userOptional = userService.findOneByEmail("juan@rodriguez.org");

        assertTrue(userOptional.isPresent());

        User user = userOptional.get();
        assertNotNull(user);
        assertTrue(user.getEmail().equals("juan@rodriguez.org"));
    }

    @Order(4)
    @Test
    void createUserTest() {
        RegisterRequest registerRequest = createNewRegisterRequest("Juan Rodriguez", "juan2@rodriguez.org", "Hunter2@");

        User createdUser = userService.create(registerRequest);

        assertNotNull(createdUser);
        assertNotNull(createdUser.getId());
    }

    @Order(5)
    @Test
    void generateTokenTest() {
        User user = createNewUser("juan@rodriguez.org", "Hunter2@", Role.USER);

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("claimKey", "claimValue");

        String token = jwtService.generateToken(user, extraClaims);

        assertNotNull(token, "El token generado no debe ser nulo");

        Claims claims = jwtService.extractAllClaims(token);
        assertTrue(claims.get("claimKey").equals("claimValue"), "El token no contiene los claims esperados");

        long expirationTime = jwtService.getExpiration(token).getTime();
        long currentTime = System.currentTimeMillis();
        assertTrue(expirationTime > currentTime, "El token debería no estar expirado");
    }

    @Order(6)
    @Test
    void extractUsernameTest() {
        User user = createNewUser("juan@rodriguez.org", "Hunter2@", Role.USER);

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("claimKey", "claimValue");

        String token = jwtService.generateToken(user, extraClaims);

        String username = jwtService.extractUsername(token);

        assertEquals(user.getUsername(), username, "El username extraído del token debe coincidir");
    }

    @Order(7)
    @Test
    void isTokenValidTest() {
        User user = createNewUser("juan@rodriguez.org", "Hunter2@", Role.USER);

        Map<String, Object> extraClaims = new HashMap<>();

        String token = jwtService.generateToken(user, extraClaims);

        boolean isValid = jwtService.isTokenValid(token, user);

        assertTrue(isValid, "El token debería ser válido para el usuario dado");
    }

		@Order(8)
		@Test
		void getExpirationTest() {
			User user = createNewUser("juan@rodriguez.org", "Hunter2@", Role.USER);

			Map<String, Object> extraClaims = new HashMap<>();

			String token = jwtService.generateToken(user, extraClaims);

			// Act
			Date expirationDate = jwtService.getExpiration(token);

			// Assert
			assertNotNull(expirationDate, "La fecha de expiración no debe ser nula");
			assertTrue(expirationDate.after(new Date()), "La fecha de expiración debe estar en el futuro");
		}
	 
		@Order(9)
		@Test
		void isTokenExpiredTest() {
			User user = createNewUser("juan@rodriguez.org", "Hunter2@", Role.USER);

			Map<String, Object> extraClaims = new HashMap<>();

			// Arrange
			String token = jwtService.generateToken(user, extraClaims);

			// Act
			boolean isExpired = jwtService.isTokenExpired(token);

			// Assert
			assertFalse(isExpired, "El token no debería estar expirado inmediatamente después de ser generado");
		}
		
		@Order(10)
		@Test
		void extractAllClaimsTest() {
		    User user = createNewUser("juan@rodriguez.org", "Hunter2@", Role.USER);

		    Map<String, Object> extraClaims = new HashMap<>();
		    extraClaims.put("name", user.getName());
		    extraClaims.put("role", Role.USER);
		    extraClaims.put("authorities", user.getAuthorities());

		    String token = jwtService.generateToken((UserDetails)user, extraClaims);

		    Claims claims = jwtService.extractAllClaims(token);

		    assertNotNull(claims, "Las claims no deben ser nulas");
		    assertEquals(user.getName(), claims.get("name"), "Las claims deben contener el nombre correcto");
		    assertEquals(Role.USER.name(), claims.get("role"), "Las claims deben contener el rol correcto");
		}

		@Order(11)
		@Test
		void getClaimTest() {
		    User user = createNewUser("juan@rodriguez.org", "Hunter2@", Role.USER);

		    Map<String, Object> extraClaims = new HashMap<>();
		    extraClaims.put("name", user.getName());
		    extraClaims.put("role", Role.USER);
		    extraClaims.put("authorities", user.getAuthorities());

		    String token = jwtService.generateToken((UserDetails)user, extraClaims);

		    String nameClaim = jwtService.getClaim(token, claims -> claims.get("name", String.class));
		    String roleClaim = jwtService.getClaim(token, claims -> claims.get("role", String.class));

		    assertEquals(user.getName(), nameClaim, "El valor de la claim 'name' debe ser el nombre del usuario");
		    assertEquals(Role.USER.name(), roleClaim, "El valor de la claim 'role' debe ser 'USER'");
		}
		


	 
	 //TEST MOCKITO
//	 @Order(1)
//	    @Test
//	    void registerTestMock() throws Exception {
//	        RegisterRequest registerRequest = new RegisterRequest();
//	        registerRequest.setName("Juan Rodriguez");
//	        registerRequest.setEmail("juan@rodriguez.org");
//	        registerRequest.setPassword("Hunter2@");
//
//	        List<PhoneDTO> phones = new ArrayList<>();
//	        PhoneDTO phone = new PhoneDTO();
//	        phone.setNumber("1234567");
//	        phone.setCitycode("1");
//	        phone.setContrycode("57");
//	        phones.add(phone);
//	        registerRequest.setPhones(phones);
//
//	        mockMvc.perform(post("/auth/register")
//	            .contentType(MediaType.APPLICATION_JSON)
//	            .content(objectMapper.writeValueAsString(registerRequest)))
//	            .andExpect(status().isOk())
//	            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
//	    }
//
//	    @Order(2)
//	    @Test
//	    void loginTestMock() throws Exception {
//	        LoginRequest loginRequest = new LoginRequest();
//	        loginRequest.setEmail("juan@rodriguez.org");
//	        loginRequest.setPassword("Hunter2@");
//
//	        mockMvc.perform(post("/auth/login")
//	            .contentType(MediaType.APPLICATION_JSON)
//	            .content(objectMapper.writeValueAsString(loginRequest)))
//	            .andExpect(status().isOk())
//	            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
//	    }
	

}
