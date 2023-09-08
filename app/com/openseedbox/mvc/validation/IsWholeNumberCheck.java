package com.openseedbox.mvc.validation;

import net.sf.oval.ValidationCycle;
import net.sf.oval.configuration.annotation.AbstractAnnotationCheck;
import net.sf.oval.exception.OValException;
import org.apache.commons.lang3.StringUtils;

public class IsWholeNumberCheck extends AbstractAnnotationCheck<IsWholeNumber> {

	static final String msg = "validation.whole_number";

	@Override
	public void configure(IsWholeNumber number) {
		setMessage(number.message());
	}

	@Override
	public boolean isSatisfied(Object validatedObject, Object value, ValidationCycle cycle) throws OValException {
		try {
			String num = value.toString();
			if (!StringUtils.isEmpty(num)) {
				int i = Integer.parseInt(num);
			} else {
				return false;
			}
		} catch (Exception ex) {
			return false;
		}
		return true;
	}
}
