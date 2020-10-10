package com.github.lucasyukio.nossobancodigital.mailer;

import org.springframework.mail.SimpleMailMessage;

public class UsuarioMail {
	
	public static SimpleMailMessage usuarioTokenMail(String clienteEmail, int token) {
		SimpleMailMessage emailMessage = new SimpleMailMessage();
		
		emailMessage.setFrom("email-veridico@banco-digital.com");
		emailMessage.setTo(clienteEmail);
		emailMessage.setSubject("Token para criação da conta!");
		emailMessage.setText("Seu token é: " + token);
		
		return emailMessage;
	}
	
	public static SimpleMailMessage usuarioSenhaMail(String clienteEmail) {
		SimpleMailMessage emailMessage = new SimpleMailMessage();
		
		emailMessage.setFrom("email-veridico@banco-digital.com");
		emailMessage.setTo(clienteEmail);
		emailMessage.setSubject("Seu usuário foi criado!");
		emailMessage.setText("Parabéns! Agora você tem acesso a sua conta através do seu usuário e senha recém criados!");
		
		return emailMessage;
	}

}
