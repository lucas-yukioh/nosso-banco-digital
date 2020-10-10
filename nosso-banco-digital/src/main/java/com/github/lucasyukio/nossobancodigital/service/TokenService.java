package com.github.lucasyukio.nossobancodigital.service;

import com.github.lucasyukio.nossobancodigital.model.Cliente;
import com.github.lucasyukio.nossobancodigital.model.Token;

public interface TokenService {
	
	Token gerarToken(String cpf, String email);
	
	Token buscarTokenValido(int token);
	
	Token buscarTokenPorToken(int token);
	
	Token buscarTokenPorCliente(Cliente cliente);
	
	Token atualizarUsadoToken(Token token, boolean usado);

}
