package com.github.lucasyukio.nossobancodigital.service;

import com.github.lucasyukio.nossobancodigital.model.Conta;

public interface ContaService {

	Conta criarConta(long propostaId);
	
	Conta buscarContaPorAgenciaEConta(int agencia, int conta);
	
	void atualizarSaldoConta(Conta conta, double valor);
	
}
