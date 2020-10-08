package com.github.lucasyukio.nossobancodigital.validator;

import java.time.LocalDate;
import java.time.Period;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IdadeValidator implements ConstraintValidator<Idade, LocalDate> {

	@Override
	public boolean isValid(LocalDate dataNascimento, ConstraintValidatorContext context) {
		return Period.between(dataNascimento, LocalDate.now()).getYears() >= 18;
	}

}
