package com.github.lucasyukio.nossobancodigital.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.lucasyukio.nossobancodigital.model.Documento;

public interface DocumentoRepository extends JpaRepository<Documento, Long> {
	
}
