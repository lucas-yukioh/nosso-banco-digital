package com.github.lucasyukio.nossobancodigital.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.lucasyukio.nossobancodigital.model.Transferencia;

public interface TransferenciaRepository extends JpaRepository<Transferencia, Long> {
	
	Optional<Transferencia> findByCodUnicoAndBancoOrigem(int codUnico, int bancoOrigem);

}
