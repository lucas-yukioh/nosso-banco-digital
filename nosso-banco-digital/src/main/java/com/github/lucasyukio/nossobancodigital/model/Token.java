package com.github.lucasyukio.nossobancodigital.model;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
public class Token {
	
	private static final int EXPIRACAO = 60 * 24;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private int token;
	
	@OneToOne
	@JoinColumn(name = "cliente_id")
	private Usuario usuario;
	
	private Date dataExpiracao;
	
	private Date calcularDataExpiracao() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Timestamp(cal.getTime().getTime()));
		cal.add(Calendar.MINUTE, EXPIRACAO);
		
		return new Date(cal.getTime().getTime());
	}
	
	public Token() {
		token = 100000 + new Random().nextInt(900000);
		dataExpiracao = calcularDataExpiracao();
	}

}
