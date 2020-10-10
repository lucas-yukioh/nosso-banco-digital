package com.github.lucasyukio.nossobancodigital.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.lucasyukio.nossobancodigital.model.Cliente;
import com.github.lucasyukio.nossobancodigital.model.Token;

public interface TokenRepository extends JpaRepository<Token, Long> {
	
	Optional<Token> findByToken(int token);
	
	Optional<Token> findByCliente(Cliente cliente);

}
