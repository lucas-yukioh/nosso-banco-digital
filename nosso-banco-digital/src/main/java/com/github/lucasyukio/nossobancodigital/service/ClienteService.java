package com.github.lucasyukio.nossobancodigital.service;

import com.github.lucasyukio.nossobancodigital.dto.ClienteDTO;
import com.github.lucasyukio.nossobancodigital.model.Cliente;
import com.github.lucasyukio.nossobancodigital.model.DocumentoFoto;
import com.github.lucasyukio.nossobancodigital.model.Endereco;
import com.github.lucasyukio.nossobancodigital.model.Proposta;

public interface ClienteService {

	Cliente salvarCliente(ClienteDTO cliente, long propostaId);
	
	Cliente atualizarEnderecoCliente(Cliente cliente, Endereco endereco);
	
	Cliente atualizarDocumentoFotoCliente(Cliente cliente, DocumentoFoto documentoFoto);
	
	Cliente atualizarPropostaCliente(Cliente cliente, Proposta proposta);
	
	Cliente buscarClientePorId(long id);
	
}
