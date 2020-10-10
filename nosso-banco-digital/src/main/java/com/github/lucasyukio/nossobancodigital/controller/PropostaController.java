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
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.github.lucasyukio.nossobancodigital.mailer.ContaMail;
import com.github.lucasyukio.nossobancodigital.mailer.PropostaMail;
import com.github.lucasyukio.nossobancodigital.message.ResponseMessage;
import com.github.lucasyukio.nossobancodigital.model.Cliente;
import com.github.lucasyukio.nossobancodigital.model.Conta;
import com.github.lucasyukio.nossobancodigital.model.Proposta;
import com.github.lucasyukio.nossobancodigital.service.ClienteService;
import com.github.lucasyukio.nossobancodigital.service.ContaService;
import com.github.lucasyukio.nossobancodigital.service.PropostaService;

@RestController
@RequestMapping("/proposta")
public class PropostaController {
	
	@Autowired
	ClienteService clienteService;
	
	@Autowired
	PropostaService propostaService;
	
	@Autowired
	ContaService contaService;
	
	@Autowired
	JavaMailSender mailSender;
	
	@PostMapping
	public ResponseEntity<ResponseMessage> criarProposta(UriComponentsBuilder b) {
		Proposta propostaNova = propostaService.criarProposta();
		
		ResponseMessage response = new ResponseMessage();
		response.setMessage("Proposta criada com sucesso");
		
		UriComponents uriComponents = b.path("/cadastro/{id}/cliente").buildAndExpand(propostaNova.getId());
		
		return ResponseEntity.created(uriComponents.toUri()).body(response);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Proposta> exibirProposta(@PathVariable("id") long propostaId) {
		Proposta proposta = propostaService.buscarPropostaCompletaPorId(propostaId);
		
		return ResponseEntity.status(HttpStatus.FOUND).body(proposta);
	}
	
	@PostMapping("/{id}/aceitar-proposta")
	public ResponseEntity<ResponseMessage> aceitarProposta(@RequestParam("aceita") boolean aceita, @PathVariable("id") long propostaId, UriComponentsBuilder b) {
		Proposta proposta = propostaService.buscarPropostaCompletaNAceitaPorId(propostaId);
		Cliente cliente = proposta.getCliente();
		
		SimpleMailMessage emailMessage = PropostaMail.aceitarMail(aceita, cliente.getEmail());
		ResponseMessage response = new ResponseMessage();
		
		if (aceita) {
			propostaService.atualizarAceitarProposta(proposta, true);
			
			response.setMessage("Muito obrigado por aceitar a proposta! Sua conta será criada assim que seus documentos forem aprovados!");
		}
		else
			response.setMessage("Poxa, que pena, pensa mais um pouquinho e tenta de novo :(");
		
		mailSender.send(emailMessage);
		
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/{id}/liberar-proposta")
	public ResponseEntity<ResponseMessage> liberarProposta(@PathVariable("id") long propostaId, UriComponentsBuilder b) {
		Proposta proposta = propostaService.buscarPropostaCompletaNLiberadaPorId(propostaId);
		propostaService.atualizarLiberarProposta(proposta, true);
		
		ResponseMessage response = new ResponseMessage();
		response.setMessage("Proposta liberada com sucesso");
		
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/{id}/criar-conta")
	public ResponseEntity<Conta> criarConta(@PathVariable("id") long propostaId) {
		Proposta proposta = propostaService.buscarPropostaCompletaPorId(propostaId);
		Conta conta = contaService.criarConta(propostaId);
		
		SimpleMailMessage emailMessage = ContaMail.dadosContaMail(conta, proposta.getCliente().getEmail());
		mailSender.send(emailMessage);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(conta);
	}
	
	@GetMapping("/{id}/emails")
	public ResponseEntity<Object> buscarEmails(@PathVariable("id") long propostaId) {
		Proposta proposta = propostaService.buscarPropostaCompletaPorId(propostaId);
		
		String uri = "https://api.smtpbucket.com/emails?sender=email-veridico@banco-digital.com&recipient=" + proposta.getCliente().getEmail();
		
		RestTemplate restTemplate = new RestTemplate();
		Object result = restTemplate.getForObject(uri, Object.class);
		
		return ResponseEntity.status(HttpStatus.FOUND).body(result);
	}

}
