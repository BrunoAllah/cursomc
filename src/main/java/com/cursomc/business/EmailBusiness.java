package com.cursomc.business;

import org.springframework.mail.SimpleMailMessage;

import com.cursomc.domain.Pedido;

public interface EmailBusiness {

	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
	
}
