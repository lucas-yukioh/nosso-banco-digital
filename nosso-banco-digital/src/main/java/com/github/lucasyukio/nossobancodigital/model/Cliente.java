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
import com.github.lucasyukio.nossobancodigital.validator.Idade;

import lombok.Data;

@Data
@Entity
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotBlank(message = "Nome não pode ser vazio")
	@Size(max = 250, message = "Nome não pode ser maior que 250 caracteres")
	private String nome;
	
	@NotBlank(message = "Sobrenome não pode ser vazio")
	@Size(max = 250, message = "Sobrenome não pode ser maior que 250 caracteres")
	private String sobrenome;
	
	@NotBlank(message = "Email não pode ser vazio")
	@Size(max = 250, message = "Email não pode ser maior que 250 caracteres")
	@Email(message = "Email inválido")
	private String email;
	
	@NotBlank(message = "CPF não pode ser vazio")
	@Size(max = 11, message = "CPF não pode ser maior que 11 caracteres")
	@CPF(message = "CPF inválido")
	private String cpf;
	
	@NotNull(message = "Data de Nascimento não pode ser vazio")
	@Idade
	private LocalDate dataNascimento;
	
	@OneToOne
	@JoinColumn(name = "endereco_id")
	@JsonIgnoreProperties("cliente")
	private Endereco endereco;
	
	@OneToOne
	@JoinColumn(name = "documento_foto_id")
	@JsonIgnoreProperties("cliente")
	private DocumentoFoto documentoFoto;
	
	@OneToOne
	@JoinColumn(name = "proposta_id")
	@JsonIgnoreProperties("cliente")
	private Proposta proposta;
	
}
