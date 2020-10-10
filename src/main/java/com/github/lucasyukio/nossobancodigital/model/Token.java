package com.github.lucasyukio.nossobancodigital.model;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Digits;

import lombok.Data;

@Data
@Entity
public class Token {
	
	private static final int EXPIRACAO = 60 * 24;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Digits(integer = 6, fraction = 0)
	private int token;
	
	private Date dataExpiracao;
	
	private boolean usado;
	
	@OneToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;
	
	private Date calcularDataExpiracao() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Timestamp(cal.getTime().getTime()));
		cal.add(Calendar.MINUTE, EXPIRACAO);
		
		return new Date(cal.getTime().getTime());
	}
	
	public Token() {
		usado = false;
		dataExpiracao = calcularDataExpiracao();
	}

}
