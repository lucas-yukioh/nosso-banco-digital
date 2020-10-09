package com.github.lucasyukio.nossobancodigital.service;

import com.github.lucasyukio.nossobancodigital.model.Cliente;
import com.github.lucasyukio.nossobancodigital.model.Conta;
import com.github.lucasyukio.nossobancodigital.model.Proposta;

public interface PropostaService {
	
	Proposta criarProposta();
	
	Proposta buscarPropostaPorId(long id);
	
	Proposta atualizarClienteProposta(Proposta proposta, Cliente cliente);
	
	Proposta atualizarAceitarProposta(Proposta proposta, boolean aceita);
	
	Proposta atualizarLiberarProposta(Proposta proposta, boolean liberada);
	
	Proposta atualizarContaProposta(Proposta proposta, Conta conta);

}
