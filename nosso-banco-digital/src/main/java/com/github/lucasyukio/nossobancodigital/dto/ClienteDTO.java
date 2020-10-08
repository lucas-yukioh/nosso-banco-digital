package com.github.lucasyukio.nossobancodigital.dto;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.lucasyukio.nossobancodigital.validator.CPFUnico;
import com.github.lucasyukio.nossobancodigital.validator.EmailUnico;
import com.github.lucasyukio.nossobancodigital.validator.Idade;

import lombok.Data;

@Data
public class ClienteDTO {

	@NotBlank(message = "Nome não pode ser vazio")
	@Size(max = 250, message = "Nome não pode ser maior que 250 caracteres")
	private String nome;
	
	@NotBlank(message = "Sobrenome não pode ser vazio")
	@Size(max = 250, message = "Sobrenome não pode ser maior que 250 caracteres")
	private String sobrenome;
	
	@NotBlank(message = "Email não pode ser vazio")
	@Size(max = 250, message = "Email não pode ser maior que 250 caracteres")
	@Email(message = "Email inválido")
	@EmailUnico
	private String email;
	
	@NotBlank(message = "CPF não pode ser vazio")
	@Size(max = 11, message = "CPF não pode ser maior que 11 caracteres")
	@CPF(message = "CPF inválido")
	@CPFUnico
	private String cpf;
	
	@NotNull(message = "Data de Nascimento não pode ser vazio")
	@Idade
	private LocalDate dataNascimento;
	
	@JsonIgnoreProperties("cliente")
	private EnderecoDTO endereco;
	
}
