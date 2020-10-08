package com.github.lucasyukio.nossobancodigital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
	public Proposta criarProposta(long clienteId) {
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
		Cliente cliente = proposta.getCliente();
		
		if (cliente.getEndereco() == null || cliente.getDocumentoFoto() == null)
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Cliente não possui todos os dados para validar a proposta");
		else if (proposta.isAceita())
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Proposta já foi aceita");
		
		proposta.setAceita(aceita);
		
		propostaRepository.save(proposta);
		
		return proposta;
	}

	@Override
	public Proposta atualizarLiberarProposta(Proposta proposta, boolean liberada) {
		if (proposta.isLiberada())
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Proposta já foi liberada");
		
		proposta.setLiberada(liberada);
		
		propostaRepository.save(proposta);
		
		return proposta;
	}

}
