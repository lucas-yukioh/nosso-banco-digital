package com.github.lucasyukio.nossobancodigital.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.github.lucasyukio.nossobancodigital.dto.ClienteDTO;
import com.github.lucasyukio.nossobancodigital.model.Cliente;
import com.github.lucasyukio.nossobancodigital.model.Conta;
import com.github.lucasyukio.nossobancodigital.model.DocumentoFoto;
import com.github.lucasyukio.nossobancodigital.model.Endereco;
import com.github.lucasyukio.nossobancodigital.model.Proposta;
import com.github.lucasyukio.nossobancodigital.repository.ClienteRepository;

@Service
public class ClienteServiceImpl implements ClienteService {
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	PropostaService propostaService;
	
	@Override
	public Cliente salvarCliente(ClienteDTO clienteDTO, long propostaId) {
		Cliente clienteNovo = new Cliente();
		Proposta proposta = propostaService.buscarPropostaPorId(propostaId);
		
		if (clienteRepository.findByCpf(clienteDTO.getCpf()).isPresent())
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "CPF já cadastrado no sistema");
		else if (clienteRepository.findByEmail(clienteDTO.getEmail()).isPresent())
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Email já cadastrado no sistema");
		
		clienteNovo.setNome(clienteDTO.getNome());
		clienteNovo.setSobrenome(clienteDTO.getSobrenome());
		clienteNovo.setEmail(clienteDTO.getEmail());
		clienteNovo.setCpf(clienteDTO.getCpf());
		clienteNovo.setDataNascimento(clienteDTO.getDataNascimento());
		clienteNovo.setProposta(proposta);
		
		clienteRepository.save(clienteNovo);
		
		propostaService.atualizarClienteProposta(proposta, clienteNovo);
		
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
	public Cliente atualizarContaCliente(Cliente cliente, Conta conta) {
		cliente.setConta(conta);
		
		clienteRepository.save(cliente);
		
		return cliente;
	}
	
	@Override
	public Cliente buscarClientePorId(long id) {
		Optional<Cliente> clienteOpt = clienteRepository.findById(id);
		
		return clienteOpt.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
	}

}
