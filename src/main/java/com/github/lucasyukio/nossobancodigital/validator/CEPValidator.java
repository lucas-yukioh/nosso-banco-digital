package com.github.lucasyukio.nossobancodigital.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CEPValidator implements ConstraintValidator<CEP, String> {

	@Override
	public boolean isValid(String cep, ConstraintValidatorContext context) {
		Pattern pattern = Pattern.compile("^(([0-9]{2}\\.[0-9]{3}-[0-9]{3})|([0-9]{2}[0-9]{3}-[0-9]{3})|([0-9]{8}))$");
		Matcher matcher = pattern.matcher(cep);
		
		boolean result = matcher.find();
		
		return result;
	}

}
