package com.github.lucasyukio.nossobancodigital.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.lucasyukio.nossobancodigital.dto.UsuarioDTO;
import com.github.lucasyukio.nossobancodigital.mailer.UsuarioMail;
import com.github.lucasyukio.nossobancodigital.message.ResponseMessage;
import com.github.lucasyukio.nossobancodigital.model.Token;
import com.github.lucasyukio.nossobancodigital.service.ClienteService;
import com.github.lucasyukio.nossobancodigital.service.PropostaService;
import com.github.lucasyukio.nossobancodigital.service.TokenService;
import com.github.lucasyukio.nossobancodigital.service.UsuarioService;

@RestController
@RequestMapping("/login")
public class UsuarioController {
	
	@Autowired
	ClienteService clienteService;
	
	@Autowired
	PropostaService propostaService;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	TokenService tokenService;
	
	@Autowired
	JavaMailSender mailSender;
	
	@PostMapping("/primeiro-acesso")
	public ResponseEntity<ResponseMessage> primeiroAcesso(@RequestParam("cpf") String cpf, @RequestParam("email") String email) {
		Token token = tokenService.gerarToken(cpf, email);
		
		SimpleMailMessage mailMessage = UsuarioMail.usuarioTokenMail(email, token.getToken());
		mailSender.send(mailMessage);
		
		ResponseMessage response = new ResponseMessage();
		response.setMessage("Token gerado com sucesso");
		
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/criar-usuario")
	public ResponseEntity<ResponseMessage> criarUsuario(@RequestBody @Valid UsuarioDTO usuarioDTO) {
		usuarioService.criarUsuario(usuarioDTO);
		
		SimpleMailMessage mailMessage = UsuarioMail.usuarioSenhaMail(usuarioDTO.getEmail());
		mailSender.send(mailMessage);
		
		ResponseMessage response = new ResponseMessage();
		response.setMessage("Usuário criado com sucesso!");
		
		return ResponseEntity.ok(response);
	}

}
