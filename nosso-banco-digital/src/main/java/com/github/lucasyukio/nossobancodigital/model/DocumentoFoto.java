package com.github.lucasyukio.nossobancodigital.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
public class DocumentoFoto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotBlank(message = "Nome do Documento é obrigatório")
	private String documentoNome;
	
	private String documentoTipo;
	
	@Lob
	@JsonIgnore
	private byte[] documentoData;
	
	@OneToOne(mappedBy = "documentoFoto")
	@JsonIgnoreProperties("documentoFoto")
	private Cliente cliente;

}
