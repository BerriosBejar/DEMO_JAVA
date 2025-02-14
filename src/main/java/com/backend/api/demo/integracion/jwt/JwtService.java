package com.backend.api.demo.integracion.jwt;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	@Value("${jwt.secret}")
	private String secretKey;

	@Value("${jwt.expiration}")
	private long jwtExpiration;

	public String generateToken(UserDetails user, Map<String, Object> extraClaims) {
	    String jwt = Jwts
	            .builder()
	            .setClaims(extraClaims)
	            .setSubject(user.getUsername())
	            .setIssuedAt(new Date(System.currentTimeMillis()))
	            .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration)) //30 minutos en milisegundos
	            .signWith(generateKey(), SignatureAlgorithm.HS256)
	            .compact();
	    
	    return jwt;
	}
	
	private Key generateKey() {
		byte[] passwordDecoded = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(passwordDecoded);		
	}

	public String extractUsername(String jwt) {	
		return extractAllClaims(jwt).getSubject();
	}

	public Claims extractAllClaims(String jwt) {
		return Jwts.parserBuilder().setSigningKey(generateKey()).build()
			.parseClaimsJws(jwt).getBody(); 		
	}
	
	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	public Date getExpiration(String token) {
		return getClaim(token, Claims::getExpiration);
	}

	public boolean isTokenExpired(String token) {
		return getExpiration(token).before(new Date());
	}
	
}
