package com.github.lucasyukio.nossobancodigital.service;

import com.github.lucasyukio.nossobancodigital.model.Proposta;

public interface PropostaService {
	
	Proposta criarProposta(long clienteId);
	
	Proposta atualizarAceitarProposta(Proposta proposta, boolean aceita);
	
	Proposta atualizarLiberarProposta(Proposta proposta, boolean liberada);

}
