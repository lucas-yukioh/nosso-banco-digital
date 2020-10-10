package com.github.lucasyukio.nossobancodigital.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.lucasyukio.nossobancodigital.model.Conta;

public interface ContaRepository extends JpaRepository<Conta, Long> {
	
	Optional<Conta> findByAgenciaAndConta(int agencia, int conta);
	
}
