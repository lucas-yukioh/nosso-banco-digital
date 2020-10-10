package com.github.lucasyukio.nossobancodigital.dto;

import java.time.LocalDate;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class TransferenciaDTO {
	
	@NotNull(message = "Código Único é obrigatório")
	private int codUnico;
	
	@NotNull(message = "Valor é obrigatório")
	@DecimalMin("0.00")
	private double valor;
	
	@NotNull(message = "Data de Transferência é obrigatório")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate dataTransferencia;
	
	@NotBlank(message = "CPF de Origem é obrigatório")
	@CPF(message = "CPF inválido")
	private String cpfOrigem;
	
	@Digits(integer = 3, fraction = 0)
	private int bancoOrigem;
	
	@Digits(integer = 4, fraction = 0)
	private int agenciaOrigem;
	
	@Digits(integer = 8, fraction = 0)
	private int contaOrigem;
	
	@Digits(integer = 4, fraction = 0)
	private int agenciaDestino;
	
	@Digits(integer = 8, fraction = 0)
	private int contaDestino;

}
