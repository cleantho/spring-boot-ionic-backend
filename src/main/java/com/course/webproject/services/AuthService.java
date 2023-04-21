package com.course.webproject.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.course.webproject.domain.Cliente;
import com.course.webproject.repositories.ClienteRepository;
import com.course.webproject.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EmailService emailService;

	private Random random = new Random();

	public void sendNewPassword(String email) {
		Cliente cliente = clienteRepository.findByEmail(email);
		if (cliente == null) {
			throw new ObjectNotFoundException("Email não encontrado!");
		}
		String newPassword = newPassword();
		cliente.setSenha(encoder.encode(newPassword));

		clienteRepository.save(cliente);
		emailService.sendNewPasswordEmail(cliente, newPassword);
	}

	private String newPassword() {
		char[] vet = new char[10];
		for (int i = 0; i < 10; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		int opt = random.nextInt(3);
		switch (opt) {
		case 0: // gera um digito
			return (char) (random.nextInt(10) + 48);
		case 1: // gera letra maiuscula
			return (char) (random.nextInt(26) + 65);
		default: // gera letra minuscula
			return (char) (random.nextInt(26) + 97);
		}
	}

}
