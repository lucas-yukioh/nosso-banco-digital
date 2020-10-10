package com.github.lucasyukio.nossobancodigital.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.github.lucasyukio.nossobancodigital.dto.UsuarioDTO;

public class ConfirmaSenhaValidator implements ConstraintValidator<ConfirmaSenha, Object> {

	@Override
	public boolean isValid(Object obj, ConstraintValidatorContext context) {
		UsuarioDTO usuarioDTO = (UsuarioDTO) obj;
		
		return usuarioDTO.getSenha().equals(usuarioDTO.getConfirmaSenha());
	}

}
