package com.github.lucasyukio.nossobancodigital.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.github.lucasyukio.nossobancodigital.dto.ClienteDTO;
import com.github.lucasyukio.nossobancodigital.model.Cliente;
import com.github.lucasyukio.nossobancodigital.model.Documento;
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
	public Cliente salvarCliente(long propostaId, ClienteDTO clienteDTO) {
		Cliente clienteNovo = new Cliente();
		Proposta proposta = propostaService.buscarPropostaPorId(propostaId);
		
		if (proposta.getCliente() != null)
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Proposta já possui Cliente cadastrado");
		else if (clienteRepository.findByCpf(clienteDTO.getCpf()).isPresent())
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
	public Cliente atualizarDocumentoCliente(Cliente cliente, Documento documento) {
		cliente.setDocumento(documento);
		
		clienteRepository.save(cliente);
		
		return cliente;
	}
	
	@Override
	public Cliente buscarClientePorId(long id) {
		Optional<Cliente> clienteOpt = clienteRepository.findById(id);
		
		return clienteOpt.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
	}
	
	@Override
	public Cliente buscarClientePorCpfEEmail(String cpf, String email) {
		Optional<Cliente> clienteOpt = clienteRepository.findByCpfAndEmail(cpf, email);
		
		return clienteOpt.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
	}

}
