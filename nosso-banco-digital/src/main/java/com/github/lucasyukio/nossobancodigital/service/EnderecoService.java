package com.github.lucasyukio.nossobancodigital.service;

import com.github.lucasyukio.nossobancodigital.dto.EnderecoDTO;
import com.github.lucasyukio.nossobancodigital.model.Endereco;

public interface EnderecoService {
	
	Endereco salvarEndereco(long clienteId, EnderecoDTO endereco);

}
