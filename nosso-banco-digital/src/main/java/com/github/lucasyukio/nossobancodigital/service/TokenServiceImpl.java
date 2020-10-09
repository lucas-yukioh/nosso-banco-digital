package com.github.lucasyukio.nossobancodigital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.lucasyukio.nossobancodigital.model.Token;
import com.github.lucasyukio.nossobancodigital.model.Usuario;
import com.github.lucasyukio.nossobancodigital.repository.TokenRepository;

@Service
public class TokenServiceImpl implements TokenService {
	
	@Autowired
	TokenRepository tokenRepository;

	@Override
	public Token criarToken(Usuario usuario) {
		Token token = new Token();
		
		token.setUsuario(usuario);
		
		tokenRepository.save(token);
		
		return token;
	}

}
