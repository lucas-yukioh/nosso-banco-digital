package com.github.lucasyukio.nossobancodigital.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.github.lucasyukio.nossobancodigital.model.Cliente;
import com.github.lucasyukio.nossobancodigital.model.Conta;
import com.github.lucasyukio.nossobancodigital.repository.ContaRepository;

@Service
public class ContaServiceImpl implements ContaService {
	
	@Autowired
	ContaRepository contaRepository;

	@Autowired
	ClienteService clienteService;
	
	@Override
	public Conta criarConta(long clienteId) {
		Cliente cliente = clienteService.buscarClientePorId(clienteId);
		
		if (cliente.getConta() != null)
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Cliente já possui conta");
		
		Conta conta = new Conta();
		
		Random random = new Random();
		
		conta.setAgencia(1000 + random.nextInt(9000));
		conta.setAgencia(10000000 + random.nextInt(90000000));
		conta.setCodBanco(123);
		conta.setSaldo(0.0);
		conta.setCliente(cliente);
		
		contaRepository.save(conta);
		
		clienteService.atualizarContaCliente(cliente, conta);
		
		return null;
	}

}
