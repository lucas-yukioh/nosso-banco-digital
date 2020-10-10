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
import com.github.lucasyukio.nossobancodigital.model.Documento;
import com.github.lucasyukio.nossobancodigital.model.Proposta;
import com.github.lucasyukio.nossobancodigital.repository.DocumentoRepository;

@Service
public class DocumentoServiceImpl implements DocumentoService {
	
	@Autowired
	DocumentoRepository documentoRepository;
	
	@Autowired
	PropostaService propostaService;
	
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
	public Documento salvarDocumento(long propostaId, MultipartFile documentoFile) {
		Proposta proposta = propostaService.buscarPropostaPorId(propostaId);
		Cliente cliente = proposta.getCliente();
		
		if (cliente == null || cliente.getEndereco() == null)
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Proposta não possui todos os dados do Cliente");
		else if (cliente.getDocumento() != null)
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Cliente já possui Documento cadastrado");
		
		criarDiretorio(propostaId);
		
		Documento documento = new Documento();
		
		Path pathUpload = Paths.get(uploadDir + cliente.getProposta().getId());
		
		String originalNome = StringUtils.cleanPath(documentoFile.getOriginalFilename());
		String extensaoArq = originalNome.substring(originalNome.lastIndexOf("."));
		String documentoNome = "cliente_" + cliente.getId() + "_documento" + extensaoArq;
		
		try {
			Files.copy(documentoFile.getInputStream(), pathUpload.resolve(documentoNome), StandardCopyOption.REPLACE_EXISTING);
			
			documento.setDocumentoNome(documentoNome);
			documento.setDocumentoTipo(documentoFile.getContentType());
			documento.setDocumentoData(documentoFile.getBytes());
			documento.setCliente(cliente);
		} catch (IOException e) {
			throw new RuntimeException("Erro ao salvar o documento");
		}
		
		documentoRepository.save(documento);
		
		clienteService.atualizarDocumentoCliente(cliente, documento);
		
		return documento;
	}

}
