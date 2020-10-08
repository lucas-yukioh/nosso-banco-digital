package com.github.lucasyukio.nossobancodigital.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.github.lucasyukio.nossobancodigital.dto.ClienteDTO;
import com.github.lucasyukio.nossobancodigital.dto.EnderecoDTO;
import com.github.lucasyukio.nossobancodigital.model.Cliente;
import com.github.lucasyukio.nossobancodigital.model.DocumentoFoto;
import com.github.lucasyukio.nossobancodigital.model.Endereco;
import com.github.lucasyukio.nossobancodigital.model.Proposta;
import com.github.lucasyukio.nossobancodigital.service.ClienteService;
import com.github.lucasyukio.nossobancodigital.service.DocumentoFotoService;
import com.github.lucasyukio.nossobancodigital.service.EnderecoService;
import com.github.lucasyukio.nossobancodigital.service.PropostaService;

@RestController
@RequestMapping("/api")
public class ClienteController {
	
	@Autowired
	ClienteService clienteService;
	
	@Autowired 
	EnderecoService enderecoService;
	
	@Autowired
	DocumentoFotoService documentoFotoService;
	
	@Autowired
	PropostaService propostaService;
	
	@PostMapping("/cliente")
	public ResponseEntity<Cliente> salvarCliente(@RequestBody @Valid ClienteDTO cliente) {
		Cliente clienteNovo = clienteService.salvarCliente(cliente);
		
		String location = ServletUriComponentsBuilder
							.fromCurrentRequest()
							.path("/{id}/endereco")
							.buildAndExpand(clienteNovo.getId())
							.toUriString();
		
		return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, location).body(clienteNovo);
	}
	
	@PostMapping("/cliente/{id}/endereco")
	public ResponseEntity<Endereco> salvarEndereco(@RequestBody @Valid EnderecoDTO endereco, @PathVariable("id") long clienteId) {
		Endereco enderecoNovo = enderecoService.salvarEndereco(clienteId, endereco);
		
		String location = ServletUriComponentsBuilder
							.fromCurrentServletMapping()
							.path("/api/cliente/{id}/documento")
							.buildAndExpand(clienteId)
							.toUriString();
		
		return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, location).body(enderecoNovo);
	}
	
	@PostMapping("/cliente/{id}/documento")
	public ResponseEntity<DocumentoFoto> salvarDocumento(@RequestParam("documentoFrente") MultipartFile documentoFrente, 
														 @RequestParam("documentoVerso") MultipartFile documentoVerso, @PathVariable("id") long clienteId) {
		DocumentoFoto documentoFotoNovo = documentoFotoService.salvarDocumentoFoto(clienteId, documentoFrente, documentoVerso);
		
		String location = ServletUriComponentsBuilder
				.fromCurrentServletMapping()
				.path("/api/cliente/{id}/proposta")
				.buildAndExpand(clienteId)
				.toUriString();
		
		return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, location).body(documentoFotoNovo);
	}
	
	@GetMapping("/cliente/{id}/proposta")
	public Proposta exibirProposta(@PathVariable("id") long clienteId) {
		Cliente cliente = clienteService.buscarClientePorId(clienteId);
		Proposta proposta;
		
		if (cliente.getEndereco() == null || cliente.getDocumentoFoto() == null)
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Cliente não possui todos os dados necessários para proposta");
		
		if (cliente.getProposta() != null)
			proposta = cliente.getProposta();
		else
			proposta = propostaService.salvarProposta(clienteId);
		
		return proposta;
	}
	
	@PostMapping("/cliente/{id}/aceitar-proposta")
	public void aceitarProposta(@PathVariable("id") long clienteId) {
		Cliente cliente = clienteService.buscarClientePorId(clienteId);
		Proposta proposta = cliente.getProposta();
		
		propostaService.atualizarAceitarProposta(proposta, true);
	}
	
	@PostMapping("/cliente/{id}/liberar-proposta")
	public void liberarProposta(@PathVariable("id") long clienteId) {
		Cliente cliente = clienteService.buscarClientePorId(clienteId);
		Proposta proposta = cliente.getProposta();
		
		propostaService.atualizarLiberarProposta(proposta, true);
	}
	
	@PostMapping("/cliente/{id}/conta")
	public void criarConta(@PathVariable("id") long clienteId) {
		Cliente cliente = clienteService.buscarClientePorId(clienteId);
		Proposta proposta = cliente.getProposta();
		
		if (proposta.isAceita() && proposta.isLiberada()) {
			
		}
	}

}
