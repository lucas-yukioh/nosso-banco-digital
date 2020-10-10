package com.github.lucasyukio.nossobancodigital.service;

import com.github.lucasyukio.nossobancodigital.dto.ClienteDTO;
import com.github.lucasyukio.nossobancodigital.model.Cliente;
import com.github.lucasyukio.nossobancodigital.model.Documento;
import com.github.lucasyukio.nossobancodigital.model.Endereco;

public interface ClienteService {

	Cliente salvarCliente(long propostaId, ClienteDTO cliente);
	
	Cliente atualizarEnderecoCliente(Cliente cliente, Endereco endereco);
	
	Cliente atualizarDocumentoCliente(Cliente cliente, Documento documento);
	
	Cliente buscarClientePorId(long id);
	
	Cliente buscarClientePorCpfEEmail(String cpf, String email);
	
}
