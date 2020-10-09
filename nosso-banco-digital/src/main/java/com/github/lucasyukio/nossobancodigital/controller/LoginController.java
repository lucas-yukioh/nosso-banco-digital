package com.github.lucasyukio.nossobancodigital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.lucasyukio.nossobancodigital.mailer.UsuarioMail;
import com.github.lucasyukio.nossobancodigital.message.ResponseMessage;
import com.github.lucasyukio.nossobancodigital.model.Cliente;
import com.github.lucasyukio.nossobancodigital.model.Token;
import com.github.lucasyukio.nossobancodigital.model.Usuario;
import com.github.lucasyukio.nossobancodigital.service.ClienteService;
import com.github.lucasyukio.nossobancodigital.service.TokenService;
import com.github.lucasyukio.nossobancodigital.service.UsuarioService;

@RestController
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	ClienteService clienteService;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	TokenService tokenService;
	
	@Autowired
	JavaMailSender mailSender;
	
	@PostMapping("/gerar-token")
	public ResponseEntity<ResponseMessage> gerarToken(@RequestParam("cpf") String cpf, @RequestParam("email") String email) {
		Cliente cliente = clienteService.buscarClientePorCpfEEmail(cpf, email);
		ResponseMessage response = new ResponseMessage();
		Usuario usuario = usuarioService.criarUsuario(cliente);
		
		Token token = tokenService.criarToken(usuario);
		
		SimpleMailMessage mailMessage = UsuarioMail.usuarioTokenMail(cliente.getEmail(), token.getToken());
		mailSender.send(mailMessage);
		
		response.setMessage("Token gerado com sucesso");
		
		return ResponseEntity.ok(response);
	}

}
