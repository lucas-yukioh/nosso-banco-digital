package com.github.lucasyukio.nossobancodigital.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.github.lucasyukio.nossobancodigital.validator.CEP;

import lombok.Data;

@Data
public class EnderecoDTO {
	
	@NotBlank(message = "CEP é obrigatório")
	@CEP
	private String cep;
	
	@NotBlank(message = "Rua é obrigatório")
	@Size(max = 250, message = "Rua não pode ser maior que 250 caracteres")
	private String rua;
	
	@NotBlank(message = "Bairro é obrigatório")
	@Size(max = 250, message = "Bairro não pode ser maior que 250 caracteres")
	private String bairro;
	
	@NotBlank(message = "Complemento é obrigatório")
	@Size(max = 250, message = "Complemento não pode ser maior que 250 caracteres")
	private String complemento;
	
	@NotBlank(message = "Cidade é obrigatório")
	@Size(max = 250, message = "Cidade não pode ser maior que 250 caracteres")
	private String cidade;
	
	@NotBlank(message = "Estado é obrigatório")
	@Size(max = 250, message = "Estado não pode ser maior que 250 caracteres")
	private String estado;

}
