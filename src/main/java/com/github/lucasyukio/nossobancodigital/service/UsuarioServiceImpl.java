package com.github.lucasyukio.nossobancodigital.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.github.lucasyukio.nossobancodigital.dto.UsuarioDTO;
import com.github.lucasyukio.nossobancodigital.model.Cliente;
import com.github.lucasyukio.nossobancodigital.model.Token;
import com.github.lucasyukio.nossobancodigital.model.Usuario;
import com.github.lucasyukio.nossobancodigital.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	ClienteService clienteService;
	
	@Autowired
	TokenService tokenService;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public Usuario criarUsuario(UsuarioDTO usuarioDTO) {
		Cliente cliente = clienteService.buscarClientePorCpfEEmail(usuarioDTO.getCpf(), usuarioDTO.getEmail());
		
		if (buscarUsuarioPorCliente(cliente).isPresent())
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Cliente já possui Usuário cadastrado");
		
		Token token = tokenService.buscarTokenPorCliente(cliente);
		tokenService.validarToken(token);
		
		if (token.getToken() != usuarioDTO.getToken())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token inválido");
		
		Usuario usuario = new Usuario();
		
		usuario.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
		usuario.setCliente(cliente);
		
		usuarioRepository.save(usuario);
		
		tokenService.atualizarUsadoToken(token, true);
		
		return usuario;
	}
	
	@Override
	public Optional<Usuario> buscarUsuarioPorCliente(Cliente cliente) {
		Optional<Usuario> usuarioOpt = usuarioRepository.findByCliente(cliente);
		
		return usuarioOpt;
	}

}
