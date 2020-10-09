package com.github.lucasyukio.nossobancodigital.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.github.lucasyukio.nossobancodigital.model.Cliente;
import com.github.lucasyukio.nossobancodigital.model.DocumentoFoto;
import com.github.lucasyukio.nossobancodigital.repository.DocumentoFotoRepository;

@Service
public class DocumentoFotoServiceImpl implements DocumentoFotoService {
	
	@Autowired
	DocumentoFotoRepository documentoFotoRepository;
	
	@Autowired
	ClienteService clienteService;
	
	@Value("${file.upload-dir}")
	private String uploadDir;
	
	@Override
	public void criarDiretorio(long propostaId) {
		try {
			Path pathUpload = Paths.get(uploadDir + propostaId);
			
			if (!Files.exists(pathUpload))
				Files.createDirectory(pathUpload);
		} catch (IOException e) {
			throw new RuntimeException("Não foi possível criar a pasta para os documentos");
		}
	}
	
	@Override
	public DocumentoFoto salvarDocumentoFoto(long clienteId, MultipartFile documentoFile) {
		Cliente cliente = clienteService.buscarClientePorId(clienteId);
		DocumentoFoto documentoFoto = new DocumentoFoto();
		
		Path pathUpload = Paths.get(uploadDir + cliente.getProposta().getId());
		
		String originalNome = StringUtils.cleanPath(documentoFile.getOriginalFilename());
		String extensaoArq = originalNome.substring(originalNome.lastIndexOf("."));
		String documentoNome = "cliente_" + clienteId + "_documento" + extensaoArq;
		
		try {
			Files.copy(documentoFile.getInputStream(), pathUpload.resolve(documentoNome), StandardCopyOption.REPLACE_EXISTING);
			
			documentoFoto.setCliente(cliente);
			documentoFoto.setDocumentoNome(documentoNome);
			documentoFoto.setDocumentoTipo(documentoFile.getContentType());
			documentoFoto.setDocumentoData(documentoFile.getBytes());
		} catch (IOException e) {
			throw new RuntimeException("Erro ao salvar o documento");
		}
		
		documentoFotoRepository.save(documentoFoto);
		
		clienteService.atualizarDocumentoFotoCliente(cliente, documentoFoto);
		
		return documentoFoto;
	}

}
