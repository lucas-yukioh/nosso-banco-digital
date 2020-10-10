package com.github.lucasyukio.nossobancodigital.service;

import java.util.Optional;

import com.github.lucasyukio.nossobancodigital.dto.UsuarioDTO;
import com.github.lucasyukio.nossobancodigital.model.Cliente;
import com.github.lucasyukio.nossobancodigital.model.Usuario;

public interface UsuarioService {
	
	Usuario criarUsuario(UsuarioDTO usuarioDTO);
	
	Optional<Usuario> buscarUsuarioPorCliente(Cliente cliente);
	
}
