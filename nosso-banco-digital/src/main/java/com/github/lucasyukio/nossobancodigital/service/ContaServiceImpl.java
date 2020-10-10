package com.github.lucasyukio.nossobancodigital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.github.lucasyukio.nossobancodigital.model.Conta;
import com.github.lucasyukio.nossobancodigital.model.Proposta;
import com.github.lucasyukio.nossobancodigital.repository.ContaRepository;

@Service
public class ContaServiceImpl implements ContaService {
	
	@Autowired
	ContaRepository contaRepository;
	
	@Autowired
	PropostaService propostaService;
	
	@Override
	public Conta criarConta(long propostaId) {
		Proposta proposta = propostaService.buscarPropostaCompletaAceitaLiberadaPorId(propostaId);
		
		if (proposta.getConta() != null)
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Proposta já possui Conta cadastrada");
		
		Conta conta = new Conta();
		
		conta.setProposta(proposta);
		
		contaRepository.save(conta);
		
		propostaService.atualizarContaProposta(proposta, conta);
		
		return conta;
	}

}
