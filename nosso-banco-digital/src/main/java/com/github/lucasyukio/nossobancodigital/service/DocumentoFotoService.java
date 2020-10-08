package com.github.lucasyukio.nossobancodigital.service;

import org.springframework.web.multipart.MultipartFile;

import com.github.lucasyukio.nossobancodigital.model.DocumentoFoto;

public interface DocumentoFotoService {
	
	public DocumentoFoto salvarDocumentoFoto(Long clienteId, MultipartFile documentoFrente, MultipartFile documentoVerso);
	
}
