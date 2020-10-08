package com.github.lucasyukio.nossobancodigital.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.lucasyukio.nossobancodigital.validator.CEP;

import lombok.Data;

@Data
public class EnderecoDTO {
	
	@NotBlank(message = "CEP não pode ser vazio")
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
	
	@JsonIgnoreProperties("endereco")
	private ClienteDTO cliente;

}
