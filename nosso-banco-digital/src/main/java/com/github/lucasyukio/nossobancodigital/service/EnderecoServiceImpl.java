package com.github.lucasyukio.nossobancodigital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.lucasyukio.nossobancodigital.dto.EnderecoDTO;
import com.github.lucasyukio.nossobancodigital.model.Cliente;
import com.github.lucasyukio.nossobancodigital.model.Endereco;
import com.github.lucasyukio.nossobancodigital.repository.EnderecoRepository;

@Service
public class EnderecoServiceImpl implements EnderecoService {

	@Autowired
	EnderecoRepository enderecoRepository;
	
	@Autowired
	ClienteService clienteService;
	
	@Override
	public Endereco salvarEndereco(Long clienteId, EnderecoDTO endereco) {
		Cliente cliente = clienteService.buscarClientePorId(clienteId);
		Endereco enderecoNovo = new Endereco();
		
		enderecoNovo.setCep(endereco.getCep());
		enderecoNovo.setRua(endereco.getRua());
		enderecoNovo.setBairro(endereco.getBairro());
		enderecoNovo.setComplemento(endereco.getComplemento());
		enderecoNovo.setCidade(endereco.getCidade());
		enderecoNovo.setEstado(endereco.getEstado());
		enderecoNovo.setCliente(cliente);
		
		enderecoRepository.save(enderecoNovo);
		
		clienteService.atualizarEnderecoCliente(cliente, enderecoNovo);
		
		return enderecoNovo;
	}

}
