package com.github.lucasyukio.nossobancodigital.service;

import java.util.Calendar;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.github.lucasyukio.nossobancodigital.model.Cliente;
import com.github.lucasyukio.nossobancodigital.model.Token;
import com.github.lucasyukio.nossobancodigital.repository.TokenRepository;

@Service
public class TokenServiceImpl implements TokenService {
	
	@Autowired
	TokenRepository tokenRepository;
	
	@Autowired
	ClienteService clienteService;
	
	@Autowired
	UsuarioService usuarioService;

	@Override
	public Token gerarToken(String cpf, String email) {
		Cliente cliente = clienteService.buscarClientePorCpfEEmail(cpf, email);
		
		if (cliente.getProposta().getConta() == null)
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Proposta associada a esse Cliente não possui Conta cadastrada");
		else if (usuarioService.buscarUsuarioPorCliente(cliente).isPresent())
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Cliente já possui Usuário cadastrado");
		
		if (tokenRepository.findByCliente(cliente).isPresent())
			tokenRepository.delete(tokenRepository.findByCliente(cliente).get());
			
		Token tokenObj = new Token();
		
		int token = 100000 + new Random().nextInt(900000);
		
		while(tokenRepository.findByToken(token).isPresent())
			token = 100000 + new Random().nextInt(900000);
		
		tokenObj.setToken(token);
		tokenObj.setCliente(cliente);
		
		tokenRepository.save(tokenObj);
		
		return tokenObj;
	}
	
	@Override
	public Token buscarTokenPorToken(int token) {
		Optional<Token> tokenOpt = tokenRepository.findByToken(token);
		
		return tokenOpt.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Token não encontrado"));
	}
	
	@Override
	public Token buscarTokenPorCliente(Cliente cliente) {
		Optional<Token> tokenOpt = tokenRepository.findByCliente(cliente);
		
		return tokenOpt.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Token não encontrado"));
	}
	
	@Override
	public Token atualizarUsadoToken(Token token, boolean usado) {
		token.setUsado(usado);
		
		tokenRepository.save(token);
		
		return token;
	}
	
	@Override
	public boolean validarToken(Token token) {
		boolean valido = true;
		Calendar cal = Calendar.getInstance();
		
		if ((token.getDataExpiracao().getTime() - cal.getTime().getTime()) <= 0)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token expirado");
		else if (token.isUsado())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token já foi utilizado");
		
		return valido;
	}

}
