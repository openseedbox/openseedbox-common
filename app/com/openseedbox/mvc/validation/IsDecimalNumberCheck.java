package com.openseedbox.mvc.validation;

import net.sf.oval.ValidationCycle;
import net.sf.oval.configuration.annotation.AbstractAnnotationCheck;
import net.sf.oval.exception.OValException;
import net.sf.oval.exception.ValidationFailedException;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

public class IsDecimalNumberCheck extends AbstractAnnotationCheck<IsDecimalNumber> {

	static final String msg = "validation.decimal_number";

	@Override
	public void configure(IsDecimalNumber number) {
		setMessage(number.message());
	}

	@Override
	public boolean isSatisfied(Object validatedObject, Object value, ValidationCycle cycle) throws OValException {
		try {
			String num = value.toString();
			if (!StringUtils.isEmpty(num)) {
				BigDecimal d = new BigDecimal(num);
			} else {
				return false;
			}
		} catch (Exception ex) {
			return false;
		}
		return true;
	}
}
