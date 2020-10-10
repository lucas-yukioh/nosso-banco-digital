package com.github.lucasyukio.nossobancodigital.validator;

import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.passay.DigitCharacterRule;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.SpecialCharacterRule;
import org.passay.UppercaseCharacterRule;
import org.passay.WhitespaceRule;

public class SenhaValidator implements ConstraintValidator<Senha, String> {

	@Override
	public boolean isValid (String senha, ConstraintValidatorContext context) {
		PasswordValidator validator = new PasswordValidator(Arrays.asList(
					new LengthRule(8, 16),
					new UppercaseCharacterRule(1),
					new DigitCharacterRule(1),
					new SpecialCharacterRule(1),
					new WhitespaceRule()
				));
		
		RuleResult result = validator.validate(new PasswordData(senha));
		
		return result.isValid();
	}

}
