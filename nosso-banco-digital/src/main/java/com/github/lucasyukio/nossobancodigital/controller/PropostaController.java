package com.github.lucasyukio.nossobancodigital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.github.lucasyukio.nossobancodigital.model.Cliente;
import com.github.lucasyukio.nossobancodigital.model.Proposta;
import com.github.lucasyukio.nossobancodigital.service.ClienteService;
import com.github.lucasyukio.nossobancodigital.service.PropostaService;

@RestController
@RequestMapping("/proposta")
public class PropostaController {
	
	@Autowired
	ClienteService clienteService;
	
	@Autowired
	PropostaService propostaService;
	
	@Autowired
	JavaMailSender mailSender;
	
	@PostMapping
	public ResponseEntity<?> criarProposta(UriComponentsBuilder b) {
		Proposta propostaNova = propostaService.criarProposta();
		
		UriComponents uriComponents = b.path("/cadastro/{id}/cliente").buildAndExpand(propostaNova.getId());
		
		return ResponseEntity.created(uriComponents.toUri()).build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Proposta> exibirProposta(@PathVariable("id") long propostaId) {
		Proposta proposta = propostaService.buscarPropostaPorId(propostaId);
		
		if (propostaIncompleta(proposta))
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Proposta não possui todos os dados do Cliente");
		
		return ResponseEntity.status(HttpStatus.FOUND).body(proposta);
	}
	
	@PostMapping("/{id}/aceitar-proposta")
	public ResponseEntity<String> aceitarProposta(@RequestParam("aceita") boolean aceita, @PathVariable("id") long propostaId) {
		Proposta proposta = propostaService.buscarPropostaPorId(propostaId);
		
		if (propostaIncompleta(proposta))
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Proposta não possui todos os dados do Cliente");
		
		Cliente cliente = proposta.getCliente();
		String resposta = "";
		
		SimpleMailMessage emailMessage = new SimpleMailMessage();
		emailMessage.setFrom("email-veridico@banco-digital.com");
		emailMessage.setTo(cliente.getEmail());
		
		if (aceita) {
			propostaService.atualizarAceitarProposta(proposta, true);
			
			emailMessage.setSubject("Você aceitou a proposta!");
			emailMessage.setText("Agora não tem mais volta! :)");
			
			resposta = "Muito obrigado por aceitar a proposta! Sua conta será criada assim que seus documentos forem aprovados!";
		}
		else {
			emailMessage.setSubject("Por favor, aceite a proposta!");
			emailMessage.setText("Aceita ai, vai :(((. Você pode acessar sua proposta através desse link: [inserir-link-da-proposta]");
		}
		
		mailSender.send(emailMessage);
		
		return ResponseEntity.ok(resposta);
	}
	
	@PostMapping("/{id}/liberar-proposta")
	public ResponseEntity<?> liberarProposta(@PathVariable("id") long propostaId) {
		Proposta proposta = propostaService.buscarPropostaPorId(propostaId);
		
		if (propostaIncompleta(proposta))
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Proposta não possui todos os dados do Cliente");
		
		propostaService.atualizarLiberarProposta(proposta, true);
		
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/{id}/emails")
	public ResponseEntity<String> buscarEmails(@PathVariable("id") long propostaId) {
		Proposta proposta = propostaService.buscarPropostaPorId(propostaId);
		
		if (propostaIncompleta(proposta))
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Proposta não possui todos os dados do Cliente");
		
		String uri = "https://api.smtpbucket.com/emails?sender=email-veridico@banco-digital.com&recipient=" + proposta.getCliente().getEmail();
		
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(uri, String.class);
		
		return ResponseEntity.status(HttpStatus.FOUND).body(result);
	}
	
	private boolean propostaIncompleta(Proposta proposta) {
		return proposta.getCliente() == null || proposta.getCliente().getEndereco() == null || proposta.getCliente().getDocumentoFoto() == null;
	}

}
