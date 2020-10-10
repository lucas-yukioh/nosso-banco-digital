package com.github.lucasyukio.nossobancodigital.service;

import org.springframework.web.multipart.MultipartFile;

import com.github.lucasyukio.nossobancodigital.model.Documento;

public interface DocumentoService {
	
	public void criarDiretorio(long propostaId);
	
	public Documento salvarDocumento(long propostaId, MultipartFile documentoFile);
	
}
