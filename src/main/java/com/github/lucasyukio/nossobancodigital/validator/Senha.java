package com.github.lucasyukio.nossobancodigital.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = SenhaValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Senha {
	
	String message() default "Senha precisa ter entre 8 e 16 caracteres, possuir letras e números, letra maiúscula e símbolo";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};

}
