package com.course.webproject.services;

import org.springframework.mail.SimpleMailMessage;

import com.course.webproject.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido obj);

	void sendEmail(SimpleMailMessage msg);
}
