package com.openseedbox.mvc.validation;

import com.openseedbox.code.Util;
import net.sf.oval.ValidationCycle;
import net.sf.oval.configuration.annotation.AbstractAnnotationCheck;
import net.sf.oval.exception.OValException;
import org.apache.commons.lang3.StringUtils;

public class IsCertificateOrBlankStringCheck extends AbstractAnnotationCheck<IsCertificateOrBlankString> {

	static final String msg = "validation.certificate_string";

	@Override
	public void configure(IsCertificateOrBlankString cert) {
		setMessage(cert.message());
	}

	@Override
	public boolean isSatisfied(Object validatedObject, Object value, ValidationCycle cycle) throws OValException {
		try {
			String s = value.toString();
			if (StringUtils.isEmpty(StringUtils.trimToEmpty(s))) {
				return true;
			}
			Util.stringToCertificates(s);
		} catch (Throwable t) {
			return false;
		}
		return true;
	}
}
