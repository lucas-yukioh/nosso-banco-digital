package com.github.lucasyukio.nossobancodigital.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = ConfirmaSenhaValidator.class)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfirmaSenha {
	
	String message() default "Senha e Confirmação de Senha não são iguais";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};

}
