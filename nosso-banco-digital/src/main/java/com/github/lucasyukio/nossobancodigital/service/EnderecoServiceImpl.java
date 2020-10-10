package com.github.lucasyukio.nossobancodigital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.github.lucasyukio.nossobancodigital.dto.EnderecoDTO;
import com.github.lucasyukio.nossobancodigital.model.Cliente;
import com.github.lucasyukio.nossobancodigital.model.Endereco;
import com.github.lucasyukio.nossobancodigital.model.Proposta;
import com.github.lucasyukio.nossobancodigital.repository.EnderecoRepository;

@Service
public class EnderecoServiceImpl implements EnderecoService {

	@Autowired
	EnderecoRepository enderecoRepository;
	
	@Autowired
	PropostaService propostaService;
	
	@Autowired
	ClienteService clienteService;
	
	@Override
	public Endereco salvarEndereco(long propostaId, EnderecoDTO enderecoDTO) {
		Proposta proposta = propostaService.buscarPropostaPorId(propostaId);
		Cliente cliente = proposta.getCliente();
		
		if (proposta.getCliente() == null)
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Proposta não possui todos os dados do Cliente");
		else if (cliente.getEndereco() != null)
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Cliente já possui Endereço cadastrado");
		
		Endereco enderecoNovo = new Endereco();
		
		enderecoNovo.setCep(enderecoDTO.getCep());
		enderecoNovo.setRua(enderecoDTO.getRua());
		enderecoNovo.setBairro(enderecoDTO.getBairro());
		enderecoNovo.setComplemento(enderecoDTO.getComplemento());
		enderecoNovo.setCidade(enderecoDTO.getCidade());
		enderecoNovo.setEstado(enderecoDTO.getEstado());
		enderecoNovo.setCliente(cliente);
		
		enderecoRepository.save(enderecoNovo);
		
		clienteService.atualizarEnderecoCliente(cliente, enderecoNovo);
		
		return enderecoNovo;
	}

}
