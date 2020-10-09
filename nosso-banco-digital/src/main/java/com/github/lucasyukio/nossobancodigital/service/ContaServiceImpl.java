package com.github.lucasyukio.nossobancodigital.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		Proposta proposta = propostaService.buscarPropostaPorId(propostaId);
		
		Conta conta = new Conta();
		Random random = new Random();
		
		int numAgencia = 1000 + random.nextInt(9000);
		int numConta = 10000000 + random.nextInt(90000000);
		int codBanco = 123;
		
		conta.setAgencia(numAgencia);
		conta.setConta(numConta);
		conta.setCodBanco(codBanco);
		conta.setSaldo(0.0);
		conta.setProposta(proposta);
		
		contaRepository.save(conta);
		
		propostaService.atualizarContaProposta(proposta, conta);
		
		return conta;
	}

}
