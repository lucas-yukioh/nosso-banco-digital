package com.github.lucasyukio.nossobancodigital.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.github.lucasyukio.nossobancodigital.dto.TransferenciaDTO;
import com.github.lucasyukio.nossobancodigital.model.Conta;
import com.github.lucasyukio.nossobancodigital.model.Transferencia;
import com.github.lucasyukio.nossobancodigital.repository.TransferenciaRepository;

@Service
public class TransferenciaServiceImpl implements TransferenciaService {
	
	@Autowired
	TransferenciaRepository transferenciaRepository;
	
	@Autowired
	ContaService contaService;
	
	@Override
	public Optional<Transferencia> buscarTransferenciaPorCodUnicoEBancoOrigem(int codUnico, int bancoOrigem) {
		Optional<Transferencia> transferenciaOpt = transferenciaRepository.findByCodUnicoAndBancoOrigem(codUnico, bancoOrigem);
		
		return transferenciaOpt;
	}
	
	@Override
	public void receberTransferencia(TransferenciaDTO transferenciaDTO) {
		Conta conta = contaService.buscarContaPorAgenciaEConta(transferenciaDTO.getAgenciaDestino(), transferenciaDTO.getContaDestino());
		
		if (buscarTransferenciaPorCodUnicoEBancoOrigem(transferenciaDTO.getCodUnico(), transferenciaDTO.getBancoOrigem()).isPresent())
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Transferência já realizada para Conta");
		
		Transferencia transferencia = new Transferencia();
		
		transferencia.setCodUnico(transferenciaDTO.getCodUnico());
		transferencia.setValor(transferenciaDTO.getValor());
		transferencia.setDataTransferencia(transferenciaDTO.getDataTransferencia());
		transferencia.setCpfOrigem(transferenciaDTO.getCpfOrigem());
		transferencia.setBancoOrigem(transferenciaDTO.getBancoOrigem());
		transferencia.setAgenciaOrigem(transferenciaDTO.getAgenciaOrigem());
		transferencia.setContaOrigem(transferenciaDTO.getContaOrigem());
		transferencia.setConta(conta);
		
		transferenciaRepository.save(transferencia);
		
		contaService.atualizarSaldoConta(conta, transferencia.getValor());
	}

}
