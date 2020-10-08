package com.github.lucasyukio.nossobancodigital.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.lucasyukio.nossobancodigital.repository.ClienteRepository;

public class EmailUnicoValidator implements ConstraintValidator<EmailUnico, String> {
	
	@Autowired
	ClienteRepository clienteRepository;

	@Override
	public boolean isValid(String email, ConstraintValidatorContext context) {
		return !clienteRepository.findByEmail(email).isPresent();
	}

}
