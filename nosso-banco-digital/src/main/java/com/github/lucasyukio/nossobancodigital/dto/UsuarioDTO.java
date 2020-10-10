package com.github.lucasyukio.nossobancodigital.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.br.CPF;

import com.github.lucasyukio.nossobancodigital.validator.ConfirmaSenha;
import com.github.lucasyukio.nossobancodigital.validator.Senha;

import lombok.Data;

@Data
@ConfirmaSenha
public class UsuarioDTO {
	
	@NotBlank(message = "Email é obrigatório")
	@Email(message = "Email inválido")
	private String email;
	
	@NotBlank(message = "Cpf é obrigatório")
	@CPF(message = "CPF inválido")
	private String cpf;
	
	@NotBlank(message = "Senha é obrigatório")
	@Senha
	private String senha;
	
	@NotBlank(message = "Confirmação de Senha é obrigatório")
	private String confirmaSenha;
	
	@Digits(integer = 6, fraction = 0)
	private int token;

}
