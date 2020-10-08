package com.github.lucasyukio.nossobancodigital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.lucasyukio.nossobancodigital.model.Cliente;
import com.github.lucasyukio.nossobancodigital.model.Proposta;
import com.github.lucasyukio.nossobancodigital.repository.PropostaRepository;

@Service
public class PropostaServiceImpl implements PropostaService {
	
	@Autowired
	PropostaRepository propostaRepository;
	
	@Autowired
	ClienteService clienteService;

	@Override
	public Proposta salvarProposta(Long clienteId) {
		Cliente cliente = clienteService.buscarClientePorId(clienteId);
		Proposta propostaNova = new Proposta();
		
		propostaNova.setAceita(false);
		propostaNova.setLiberada(false);
		propostaNova.setCliente(cliente);
		
		propostaRepository.save(propostaNova);
		
		clienteService.atualizarPropostaCliente(cliente, propostaNova);
		
		return propostaNova;
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

}
