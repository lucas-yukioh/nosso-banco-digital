package com.github.lucasyukio.nossobancodigital.mailer;

import org.springframework.mail.SimpleMailMessage;

public class UsuarioMail {
	
	public static SimpleMailMessage usuarioTokenMail(String clienteEmail, int token) {
		SimpleMailMessage emailMessage = new SimpleMailMessage();
		
		emailMessage.setFrom("email-veridico@banco-digital.com");
		emailMessage.setTo(clienteEmail);
		emailMessage.setSubject("Acesso o link para criar sua senha!");
		emailMessage.setText("Acesse o link [inserir-link] para criar sua senha e acessar sua conta! ");
		
		return emailMessage;
	}

}
