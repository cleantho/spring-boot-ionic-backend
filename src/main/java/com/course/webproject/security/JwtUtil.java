package com.course.webproject.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private Long expiration;

	public String generateToken(String username) {
		return JWT.create()
				.withIssuer("app")
				.withSubject(username)
				.withExpiresAt((new Date(System.currentTimeMillis() + expiration)))				
				.sign(Algorithm.HMAC256(secret.getBytes()));
	}
}
