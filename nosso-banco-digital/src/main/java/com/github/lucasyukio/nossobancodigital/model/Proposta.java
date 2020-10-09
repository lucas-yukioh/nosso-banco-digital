package com.github.lucasyukio.nossobancodigital.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
	
	@NotNull(message = "Status de aceita é obrigatório")
	private boolean aceita;
	
	@NotNull(message = "Status de liberada é obrigatório")
	private boolean liberada;
	
	@OneToOne(mappedBy = "proposta")
	@JsonIgnoreProperties("proposta")
	private Cliente cliente;
	
	@OneToOne
	@JoinColumn(name = "conta_id")
	@JsonIgnoreProperties("cliente")
	private Conta conta;

}
