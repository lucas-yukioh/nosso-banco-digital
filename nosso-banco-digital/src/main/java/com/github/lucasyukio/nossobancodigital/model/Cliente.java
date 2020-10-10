package com.github.lucasyukio.nossobancodigital.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.github.lucasyukio.nossobancodigital.validator.Idade;

import lombok.Data;

@Data
@Entity
@JsonInclude(Include.NON_NULL)
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotBlank(message = "Nome é obrigatório")
	@Size(max = 250, message = "Nome não pode ser maior que 250 caracteres")
	private String nome;
	
	@NotBlank(message = "Sobrenome é obrigatório")
	@Size(max = 250, message = "Sobrenome não pode ser maior que 250 caracteres")
	private String sobrenome;
	
	@NotBlank(message = "Email é obrigatório")
	@Size(max = 250, message = "Email não pode ser maior que 250 caracteres")
	@Email(message = "Email inválido")
	private String email;
	
	@NotBlank(message = "CPF é obrigatório")
	@Size(max = 11, message = "CPF não pode ser maior que 11 caracteres")
	@CPF(message = "CPF inválido")
	private String cpf;
	
	@NotNull(message = "Data de Nascimento é obrigatório")
	@Idade
	private LocalDate dataNascimento;
	
	@OneToOne
	@JoinColumn(name = "proposta_id")
	@JsonIgnoreProperties("cliente")
	private Proposta proposta;
	
	@OneToOne
	@JoinColumn(name = "endereco_id")
	@JsonIgnoreProperties("cliente")
	private Endereco endereco;
	
	@OneToOne
	@JoinColumn(name = "documento_id")
	@JsonIgnoreProperties("cliente")
	private Documento documento;
	
}
