package com.github.lucasyukio.nossobancodigital.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.github.lucasyukio.nossobancodigital.model.Cliente;
import com.github.lucasyukio.nossobancodigital.model.Conta;
import com.github.lucasyukio.nossobancodigital.model.Proposta;
import com.github.lucasyukio.nossobancodigital.repository.PropostaRepository;

@Service
public class PropostaServiceImpl implements PropostaService {
	
	@Autowired
	PropostaRepository propostaRepository;
	
	@Autowired
	ClienteService clienteService;

	@Override
	public Proposta criarProposta() {
		Proposta propostaNova = new Proposta();
		
		propostaNova.setAceita(false);
		propostaNova.setLiberada(false);
		
		propostaRepository.save(propostaNova);
		
		return propostaNova;
	}
	
	@Override
	public Proposta buscarPropostaPorId(long id) {
		Optional<Proposta> propostaOpt = propostaRepository.findById(id);
		
		return propostaOpt.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Proposta não encontrada"));
	}
	
	@Override
	public Proposta atualizarClienteProposta(Proposta proposta, Cliente cliente) {
		proposta.setCliente(cliente);
		
		propostaRepository.save(proposta);
		
		return proposta;
	}

	@Override
	public Proposta atualizarAceitarProposta(Proposta proposta, boolean aceita) {
		proposta.setAceita(aceita);
		
		propostaRepository.save(proposta);
		
		return proposta;
	}

	@Override
	public Proposta atualizarLiberarProposta(Proposta proposta, boolean liberada) {
		proposta.setLiberada(liberada);
		
		propostaRepository.save(proposta);
		
		return proposta;
	}
	
	@Override
	public Proposta atualizarContaProposta(Proposta proposta, Conta conta) {
		proposta.setConta(conta);
		
		propostaRepository.save(proposta);
		
		return proposta;
	}

}
