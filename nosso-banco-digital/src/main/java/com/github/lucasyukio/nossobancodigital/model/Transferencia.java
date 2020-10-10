package com.github.lucasyukio.nossobancodigital.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
public class Transferencia {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	private int codUnico;
	
	@NotNull
	@DecimalMin("0.00")
	private double valor;
	
	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate dataTransferencia;
	
	@NotBlank
	@CPF(message = "CPF inválido")
	private String cpfOrigem;
	
	@Digits(integer = 3, fraction = 0)
	private int bancoOrigem;
	
	@Digits(integer = 4, fraction = 0)
	private int agenciaOrigem;
	
	@Digits(integer = 8, fraction = 0)
	private int contaOrigem;
	
	@ManyToOne
	@JoinColumn(name = "conta_id")
	@JsonIgnoreProperties("transferencias")
	private Conta conta;

}
