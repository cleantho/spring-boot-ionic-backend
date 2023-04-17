package com.course.webproject.security;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;

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

	public boolean tokenValido(String token) {
		Map<String, Claim> claims = getClaims(token);
		if (claims != null) {
			String username = claims.get("sub").asString();
			Date expirationDate = claims.get("exp").asDate();
			Date now = new Date();
			if(username != null && expirationDate != null && now.before(expirationDate)) {
				return true;
			}
		}
		return false;
	}

	public String getUsername(String token) {
		String user = JWT.require(Algorithm.HMAC256(secret.getBytes())).build()
				.verify(token).getSubject();
		return user;
	}

	private Map<String, Claim> getClaims(String token) {
		try {
			return JWT.require(Algorithm.HMAC256(secret.getBytes())).build()
					.verify(token).getClaims();
		} catch (Exception e) {
			return null;
		}
	}
}
