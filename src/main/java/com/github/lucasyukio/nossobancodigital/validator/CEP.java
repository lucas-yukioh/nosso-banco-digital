package com.github.lucasyukio.nossobancodigital.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = CEPValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CEP {
	
	String message() default "CEP inválido";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};

}
