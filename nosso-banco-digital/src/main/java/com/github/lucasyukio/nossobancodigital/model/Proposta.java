package com.github.lucasyukio.nossobancodigital.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
public class Proposta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull(message = "Obrigatório o status de aceita ou não aceita")
	private boolean aceita;
	
	@NotNull(message = "Obrigatório o status de liberada ou não liberada")
	private boolean liberada;
	
	@OneToOne(mappedBy = "proposta")
	@JsonIgnoreProperties("proposta")
	private Cliente cliente;

}
