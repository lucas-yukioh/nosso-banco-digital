package com.github.lucasyukio.nossobancodigital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.lucasyukio.nossobancodigital.model.Cliente;
import com.github.lucasyukio.nossobancodigital.model.Usuario;
import com.github.lucasyukio.nossobancodigital.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	@Autowired
	UsuarioRepository usuarioRepository;

	@Override
	public Usuario criarUsuario(Cliente cliente) {
		Usuario usuario = new Usuario();
		
		usuario.setCliente(cliente);
		
		usuarioRepository.save(usuario);
		
		return usuario;
	}

}
