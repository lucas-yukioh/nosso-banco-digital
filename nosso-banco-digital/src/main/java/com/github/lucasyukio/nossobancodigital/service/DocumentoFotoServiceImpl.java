package com.github.lucasyukio.nossobancodigital.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

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
	public DocumentoFoto salvarDocumentoFoto(Long clienteId, MultipartFile documentoFrente, MultipartFile documentoVerso) {
		Cliente cliente = clienteService.buscarClientePorId(clienteId);
		DocumentoFoto documentoFoto = new DocumentoFoto();
		
		if (cliente.getEndereco() != null) {
			String originalNomeFrente = StringUtils.cleanPath(documentoFrente.getOriginalFilename());
			String originalNomeVerso = StringUtils.cleanPath(documentoVerso.getOriginalFilename());
			
			String extensaoArqFrente = originalNomeFrente.substring(originalNomeFrente.lastIndexOf("."));
			String extensaoArqVerso = originalNomeVerso.substring(originalNomeVerso.lastIndexOf("."));
			
			String nomeFrente = "cliente_id_" + clienteId + "_documento_frente" + extensaoArqFrente;
			String nomeVerso = "cliente_id_" + clienteId + "_documento_verso" + extensaoArqVerso;
			
			Path uploadDirFrente = Paths.get(uploadDir).toAbsolutePath().normalize().resolve(nomeFrente);
			Path uploadDirVerso = Paths.get(uploadDir).toAbsolutePath().normalize().resolve(nomeVerso);
			
			try {
				Files.copy(documentoFrente.getInputStream(), uploadDirFrente, StandardCopyOption.REPLACE_EXISTING);
				Files.copy(documentoVerso.getInputStream(), uploadDirVerso, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			documentoFoto.setCliente(cliente);
			documentoFoto.setDocumentoFrente(nomeFrente);
			documentoFoto.setDocumentoVerso(nomeVerso);
			
			documentoFotoRepository.save(documentoFoto);
			
			clienteService.atualizarDocumentoFotoCliente(cliente, documentoFoto);
		} else 
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Cliente não possui endereço salvo");
		
		return documentoFoto;
	}

}
