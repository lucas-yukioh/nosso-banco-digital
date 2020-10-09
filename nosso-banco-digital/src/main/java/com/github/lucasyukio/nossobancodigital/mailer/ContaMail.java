package com.github.lucasyukio.nossobancodigital.mailer;

import org.springframework.mail.SimpleMailMessage;

import com.github.lucasyukio.nossobancodigital.model.Conta;

public class ContaMail {
	
	public static SimpleMailMessage dadosContaMail(Conta conta, String clienteEmail) {
		SimpleMailMessage emailMessage = new SimpleMailMessage();
		
		String dadosConta = "Agência: " + conta.getAgencia() + ", Conta: " + conta.getConta() + ", Banco: " + conta.getCodBanco();
		
		emailMessage.setFrom("email-veridico@banco-digital.com");
		emailMessage.setTo(clienteEmail);
		emailMessage.setSubject("Sua conta foi criada com sucesso!");
		emailMessage.setText("Segue os dados da sua conta. " + dadosConta);
		
		return emailMessage;
	}

}
