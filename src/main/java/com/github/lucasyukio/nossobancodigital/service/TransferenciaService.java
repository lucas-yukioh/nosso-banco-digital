package com.github.lucasyukio.nossobancodigital.service;

import java.util.Optional;

import com.github.lucasyukio.nossobancodigital.dto.TransferenciaDTO;
import com.github.lucasyukio.nossobancodigital.model.Transferencia;

public interface TransferenciaService {
	
	Optional<Transferencia> buscarTransferenciaPorCodUnicoEBancoOrigem(int codUnico, int bancoOrigem);
	
	void receberTransferencia(TransferenciaDTO transferenciaDTO);

}
