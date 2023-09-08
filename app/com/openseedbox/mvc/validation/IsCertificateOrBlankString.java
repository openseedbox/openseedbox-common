package com.openseedbox.mvc.validation;

import net.sf.oval.configuration.annotation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(checkWith = IsCertificateOrBlankStringCheck.class)
public @interface IsCertificateOrBlankString {
	String message() default IsCertificateOrBlankStringCheck.msg;
}
