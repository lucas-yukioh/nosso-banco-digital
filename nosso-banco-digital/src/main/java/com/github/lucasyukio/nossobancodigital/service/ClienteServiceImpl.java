package com.github.lucasyukio.nossobancodigital.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.github.lucasyukio.nossobancodigital.dto.ClienteDTO;
import com.github.lucasyukio.nossobancodigital.model.Cliente;
import com.github.lucasyukio.nossobancodigital.model.DocumentoFoto;
import com.github.lucasyukio.nossobancodigital.model.Endereco;
import com.github.lucasyukio.nossobancodigital.model.Proposta;
import com.github.lucasyukio.nossobancodigital.repository.ClienteRepository;

@Service
public class ClienteServiceImpl implements ClienteService {
	
	@Autowired
	ClienteRepository clienteRepository;

	@Override
	public Cliente salvarCliente(ClienteDTO cliente) {
		Cliente clienteNovo = new Cliente();
		
		if (clienteRepository.findByCpf(cliente.getCpf()).isPresent())
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "CPF já cadastrado no sistema");
		else if (clienteRepository.findByEmail(cliente.getEmail()).isPresent())
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Email já cadastrado no sistema");
		
		clienteNovo.setNome(cliente.getNome());
		clienteNovo.setSobrenome(cliente.getSobrenome());
		clienteNovo.setEmail(cliente.getEmail());
		clienteNovo.setCpf(cliente.getCpf());
		clienteNovo.setDataNascimento(cliente.getDataNascimento());
		
		clienteRepository.save(clienteNovo);
		
		return clienteNovo;
	}
	
	@Override
	public Cliente atualizarEnderecoCliente(Cliente cliente, Endereco endereco) {
		cliente.setEndereco(endereco);
		
		clienteRepository.save(cliente);
		
		return cliente;
	}
	
	@Override
	public Cliente atualizarDocumentoFotoCliente(Cliente cliente, DocumentoFoto documentoFoto) {
		cliente.setDocumentoFoto(documentoFoto);
		
		clienteRepository.save(cliente);
		
		return cliente;
	}
	
	@Override
	public Cliente atualizarPropostaCliente(Cliente cliente, Proposta proposta) {
		cliente.setProposta(proposta);
		
		clienteRepository.save(cliente);
		
		return cliente;
	}
	
	@Override
	public Cliente buscarClientePorId(Long id) {
		Optional<Cliente> clienteOpt = clienteRepository.findById(id);
		
		if (clienteOpt.isPresent())
			return clienteOpt.get();
		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado");
	}

}
