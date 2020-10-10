package com.github.lucasyukio.nossobancodigital.service;

import com.github.lucasyukio.nossobancodigital.dto.UsuarioDTO;
import com.github.lucasyukio.nossobancodigital.model.Usuario;

public interface UsuarioService {
	
	Usuario criarUsuario(UsuarioDTO usuarioDTO);
	
}
