package com.github.lucasyukio.nossobancodigital.mailer;

import org.springframework.mail.SimpleMailMessage;

public class PropostaMail {
	
	public static SimpleMailMessage aceitarMail(boolean aceita, String clienteEmail) {
		SimpleMailMessage emailMessage = new SimpleMailMessage();
		
		emailMessage.setFrom("email-veridico@banco-digital.com");
		emailMessage.setTo(clienteEmail);
		
		if (aceita) {
			emailMessage.setSubject("Você aceitou a proposta!");
			emailMessage.setText("Agora não tem mais volta! :)");
		} else {
			emailMessage.setSubject("Por favor, aceite a proposta!");
			emailMessage.setText("Aceita ai, vai :(((. Você pode acessar sua proposta através desse link: [inserir-link-da-proposta]");
		}
		
		return emailMessage;
	}
	
}
