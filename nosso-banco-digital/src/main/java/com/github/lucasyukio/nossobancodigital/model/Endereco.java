package com.github.lucasyukio.nossobancodigital.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.lucasyukio.nossobancodigital.validator.CEP;

import lombok.Data;

@Data
@Entity
public class Endereco {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotBlank(message = "CEP não pode ser vazio")
	@Size(max = 8, message = "CEP não pode ser maior que 8 caracteres")
	@CEP
	private String cep;
	
	@NotBlank(message = "Rua não pode ser vazio")
	@Size(max = 250, message = "Rua não pode ser maior que 250 caracteres")
	private String rua;
	
	@NotBlank(message = "Bairro não pode ser vazio")
	@Size(max = 250, message = "Bairro não pode ser maior que 250 caracteres")
	private String bairro;
	
	@NotBlank(message = "Complemento não pode ser vazio")
	@Size(max = 250, message = "Complemento não pode ser maior que 250 caracteres")
	private String complemento;
	
	@NotBlank(message = "Cidade não pode ser vazio")
	@Size(max = 250, message = "Cidade não pode ser maior que 250 caracteres")
	private String cidade;
	
	@NotBlank(message = "Estado não pode ser vazio")
	@Size(max = 250, message = "Estado não pode ser maior que 250 caracteres")
	private String estado;
	
	@OneToOne(mappedBy = "endereco")
	@JsonIgnoreProperties("endereco")
	private Cliente cliente;

}
