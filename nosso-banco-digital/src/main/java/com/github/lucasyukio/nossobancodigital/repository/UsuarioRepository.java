package com.github.lucasyukio.nossobancodigital.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.lucasyukio.nossobancodigital.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
