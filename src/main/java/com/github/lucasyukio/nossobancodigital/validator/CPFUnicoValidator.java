package com.github.lucasyukio.nossobancodigital.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.lucasyukio.nossobancodigital.repository.ClienteRepository;

public class CPFUnicoValidator implements ConstraintValidator<CPFUnico, String> {
	
	@Autowired
	ClienteRepository clienteRepository;

	@Override
	public boolean isValid(String cpf, ConstraintValidatorContext context) {
		return !clienteRepository.findByCpf(cpf).isPresent();
	}

}
