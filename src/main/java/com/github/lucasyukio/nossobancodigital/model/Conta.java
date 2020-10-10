package com.github.lucasyukio.nossobancodigital.model;

import java.util.List;
import java.util.Random;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Digits;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@Entity
@JsonInclude(Include.NON_NULL)
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
	
	@OneToMany(mappedBy = "conta", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties("conta")
	private List<Transferencia> transferencias;
	
	public Conta() {
		agencia = 1000 + new Random().nextInt(9000);
		conta = 10000000 + new Random().nextInt(90000000);
		codBanco = 123;
		saldo = 0.0;
	}

}
