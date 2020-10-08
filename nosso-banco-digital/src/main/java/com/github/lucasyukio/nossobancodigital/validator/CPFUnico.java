package com.github.lucasyukio.nossobancodigital.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = CPFUnicoValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CPFUnico {
	
	String message() default "CPF já cadastrado no sistema";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};

}
