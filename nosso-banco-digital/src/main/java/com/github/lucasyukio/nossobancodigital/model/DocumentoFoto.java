package com.github.lucasyukio.nossobancodigital.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
public class DocumentoFoto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotBlank(message = "Frente do Documento não pode ser vazio")
	private String documentoFrente;
	
	@NotBlank(message = "Verso do Documento não pode ser vazio")
	private String documentoVerso;
	
	@OneToOne(mappedBy = "documentoFoto")
	@JsonIgnoreProperties("documentoFoto")
	private Cliente cliente;

}
