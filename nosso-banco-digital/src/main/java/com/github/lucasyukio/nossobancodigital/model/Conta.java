package com.github.lucasyukio.nossobancodigital.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.Digits;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
public class Conta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Digits(integer = 4, fraction = 0)
	private int agencia;
	
	@Digits(integer = 8, fraction = 0)
	private int conta;
	
	@Digits(integer = 3, fraction = 0)
	private int codBanco;
	
	private double saldo;
	
	@OneToOne(mappedBy = "conta")
	@JsonIgnoreProperties("conta")
	private Proposta proposta;

}
