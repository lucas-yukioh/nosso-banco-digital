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
	public DocumentoFoto salvarDocumentoFoto(long clienteId, MultipartFile docFrente, MultipartFile docVerso) {
		Cliente cliente = clienteService.buscarClientePorId(clienteId);
		DocumentoFoto documentoFoto = new DocumentoFoto();
		
		Path pathUpload = Paths.get(uploadDir + cliente.getProposta().getId());
		
		String originalFrenteNome = StringUtils.cleanPath(docFrente.getOriginalFilename());
		String originalVersoNome = StringUtils.cleanPath(docVerso.getOriginalFilename());
		
		String extensaoArqFrente = originalFrenteNome.substring(originalFrenteNome.lastIndexOf("."));
		String extensaoArqVerso = originalVersoNome.substring(originalVersoNome.lastIndexOf("."));
		
		String docFrenteNome = "cliente_" + clienteId + "_documento_frente" + extensaoArqFrente;
		String docFrenteVerso = "cliente_" + clienteId + "_documento_verso" + extensaoArqVerso;
		
		try {
			Files.copy(docFrente.getInputStream(), pathUpload.resolve(docFrenteNome), StandardCopyOption.REPLACE_EXISTING);
			Files.copy(docVerso.getInputStream(), pathUpload.resolve(docFrenteVerso), StandardCopyOption.REPLACE_EXISTING);
			
			documentoFoto.setCliente(cliente);
			documentoFoto.setDocFrenteNome(docFrenteNome);
			documentoFoto.setDocFrenteTipo(docFrente.getContentType());
			documentoFoto.setDocFrenteData(docFrente.getBytes());
			documentoFoto.setDocVersoNome(docFrenteVerso);
			documentoFoto.setDocVersoTipo(docVerso.getContentType());
			documentoFoto.setDocVersoData(docVerso.getBytes());
		} catch (IOException e) {
			throw new RuntimeException("Erro ao salvar os documentos");
		}
		
		documentoFotoRepository.save(documentoFoto);
		
		clienteService.atualizarDocumentoFotoCliente(cliente, documentoFoto);
		
		return documentoFoto;
	}

}
