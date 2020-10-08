package com.github.lucasyukio.nossobancodigital.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.lucasyukio.nossobancodigital.model.Conta;

public interface ContaRepository extends JpaRepository<Conta, Long> {
	
}
