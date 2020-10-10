package com.github.lucasyukio.nossobancodigital.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.github.lucasyukio.nossobancodigital.dto.ClienteDTO;
import com.github.lucasyukio.nossobancodigital.dto.EnderecoDTO;
import com.github.lucasyukio.nossobancodigital.message.ResponseMessage;
import com.github.lucasyukio.nossobancodigital.model.Cliente;
import com.github.lucasyukio.nossobancodigital.model.Documento;
import com.github.lucasyukio.nossobancodigital.model.Endereco;
import com.github.lucasyukio.nossobancodigital.service.ClienteService;
import com.github.lucasyukio.nossobancodigital.service.DocumentoService;
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
	DocumentoService documentoFotoService;
	
	@PostMapping("/{id}/cliente")
	public ResponseEntity<Cliente> salvarCliente(@RequestBody @Valid ClienteDTO clienteDTO, @PathVariable("id") long propostaId, UriComponentsBuilder b) {
		Cliente clienteNovo = clienteService.salvarCliente(propostaId, clienteDTO);
		
		UriComponents uriComponents = b.path("/cadastro/{id}/endereco").buildAndExpand(propostaId);
		
		return ResponseEntity.created(uriComponents.toUri()).body(clienteNovo);
	}
	
	@PostMapping("/{id}/endereco")
	public ResponseEntity<Endereco> salvarEndereco(@RequestBody @Valid EnderecoDTO enderecoDTO, @PathVariable("id") long propostaId, UriComponentsBuilder b) {
		Endereco enderecoNovo = enderecoService.salvarEndereco(propostaId, enderecoDTO);
		
		UriComponents uriComponents = b.path("/cadastro/{id}/documento").buildAndExpand(propostaId);
		
		return ResponseEntity.created(uriComponents.toUri()).body(enderecoNovo);
	}
	
	@PostMapping("/{id}/documento")
	public ResponseEntity<Documento> salvarDocumento(@RequestParam("documento") MultipartFile documentoFile, 
												     @PathVariable("id") long propostaId, UriComponentsBuilder b) {
		Documento documentoFotoNovo = documentoFotoService.salvarDocumentoFoto(propostaId, documentoFile);
		
		if (!liberarDocumentoSistemaExterno(propostaId)) {
			try {
				Thread.sleep(5000);
				liberarDocumentoSistemaExterno(propostaId);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		
		UriComponents uriComponents = b.path("/proposta/{id}").buildAndExpand(propostaId);
		
		return ResponseEntity.created(uriComponents.toUri()).body(documentoFotoNovo);
	}
	
	private boolean liberarDocumentoSistemaExterno(long propostaId) {
		boolean liberado = false;
		
		String uri = "http://localhost:8080/proposta/" + propostaId + "/liberar-proposta";
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<ResponseMessage> result = restTemplate.postForEntity(uri, null, ResponseMessage.class);
		
		if (result.getStatusCode() == HttpStatus.OK)
			liberado = true;
		
		return liberado;
	}

}
