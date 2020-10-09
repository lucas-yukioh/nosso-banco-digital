package com.github.lucasyukio.nossobancodigital.service;

import com.github.lucasyukio.nossobancodigital.model.Token;
import com.github.lucasyukio.nossobancodigital.model.Usuario;

public interface TokenService {
	
	Token criarToken(Usuario usuario);

}
