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
public class Conta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotBlank(message = "Agência é obrigatório")
	private int agencia;
	
	@NotBlank(message = "Conta é obrigatório")
	private int conta;
	
	@NotBlank(message = "Código do Banco é obrigatório")
	private int codBanco;
	
	@NotBlank(message = "Saldo é obrigatório")
	private double saldo;
	
	@OneToOne(mappedBy = "conta")
	@JsonIgnoreProperties("conta")
	private Cliente cliente;

}
