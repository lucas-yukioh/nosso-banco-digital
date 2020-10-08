package com.github.lucasyukio.nossobancodigital.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

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
@RequestMapping("/cadastro")
public class CadastroController {
	
	@Autowired
	ClienteService clienteService;
	
	@Autowired
	PropostaService propostaService;
	
	@Autowired 
	EnderecoService enderecoService;
	
	@Autowired
	DocumentoFotoService documentoFotoService;
	
	@PostMapping("/{id}/cliente")
	public ResponseEntity<Cliente> salvarCliente(@RequestBody @Valid ClienteDTO clienteDTO, @PathVariable("id") long propostaId, UriComponentsBuilder b) {
		Cliente clienteNovo = clienteService.salvarCliente(clienteDTO, propostaId);
		
		UriComponents uriComponents = b.path("/cadastro/{id}/endereco").buildAndExpand(propostaId);
		
		return ResponseEntity.created(uriComponents.toUri()).body(clienteNovo);
	}
	
	@PostMapping("/{id}/endereco")
	public ResponseEntity<Endereco> salvarEndereco(@RequestBody @Valid EnderecoDTO enderecoDTO, @PathVariable("id") long propostaId, UriComponentsBuilder b) {
		Proposta proposta = propostaService.buscarPropostaPorId(propostaId);
		Cliente cliente = proposta.getCliente();
		Endereco enderecoNovo = enderecoService.salvarEndereco(cliente.getId(), enderecoDTO);
		
		UriComponents uriComponents = b.path("/cadastro/{id}/documento").buildAndExpand(propostaId);
		
		return ResponseEntity.created(uriComponents.toUri()).body(enderecoNovo);
	}
	
	@PostMapping("/{id}/documento")
	public ResponseEntity<DocumentoFoto> salvarDocumento(@RequestParam("documentoFrente") MultipartFile documentoFrente, 
														 @RequestParam("documentoVerso") MultipartFile documentoVerso, 
														 @PathVariable("id") long propostaId, UriComponentsBuilder b) {
		Proposta proposta = propostaService.buscarPropostaPorId(propostaId);
		Cliente cliente = proposta.getCliente();
		DocumentoFoto documentoFotoNovo = documentoFotoService.salvarDocumentoFoto(cliente.getId(), documentoFrente, documentoVerso);
		
		UriComponents uriComponents = b.path("/proposta/{id}").buildAndExpand(propostaId);
		
		return ResponseEntity.created(uriComponents.toUri()).body(documentoFotoNovo);
	}

}
