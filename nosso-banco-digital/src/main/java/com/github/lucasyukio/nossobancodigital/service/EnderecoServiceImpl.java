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
	public Endereco salvarEndereco(long clienteId, EnderecoDTO enderecoDTO) {
		Cliente cliente = clienteService.buscarClientePorId(clienteId);
		Endereco enderecoNovo = new Endereco();
		
		enderecoNovo.setCep(enderecoDTO.getCep());
		enderecoNovo.setRua(enderecoDTO.getRua());
		enderecoNovo.setBairro(enderecoDTO.getBairro());
		enderecoNovo.setComplemento(enderecoDTO.getComplemento());
		enderecoNovo.setCidade(enderecoDTO.getCidade());
		enderecoNovo.setEstado(enderecoDTO.getEstado());
		enderecoNovo.setCliente(cliente);
		
		enderecoRepository.save(enderecoNovo);
		
		clienteService.atualizarEnderecoCliente(cliente, enderecoNovo);
		
		return enderecoNovo;
	}

}
