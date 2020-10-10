package com.github.lucasyukio.nossobancodigital.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.lucasyukio.nossobancodigital.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	
	Optional<Cliente> findByEmail(String email);
	
	Optional<Cliente> findByCpf(String cpf);
	
	Optional<Cliente> findByCpfAndEmail(String cpf, String email);

}
