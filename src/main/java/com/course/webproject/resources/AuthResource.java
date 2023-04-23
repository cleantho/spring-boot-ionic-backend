package com.course.webproject.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.course.webproject.dto.EmailDTO;
import com.course.webproject.security.JwtUtil;
import com.course.webproject.security.UserSS;
import com.course.webproject.services.AuthService;
import com.course.webproject.services.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private AuthService authService;

	@PostMapping(value = "/refresh_token")
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UserSS user = UserService.authenticated();
		String token = jwtUtil.generateToken(user.getUsername());
		response.addHeader("Authorization", "Bearer " + token);
		response.addHeader("access-control-expose-headers", "Authorization");
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping(value = "/forgot")
	public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO objDto) {
		authService.sendNewPassword(objDto.getEmail());
		return ResponseEntity.noContent().build();
	}

}
