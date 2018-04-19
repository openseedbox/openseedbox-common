package com.openseedbox.mvc.validation;

import com.openseedbox.code.Util;
import org.apache.commons.lang.StringUtils;
import play.Logger;
import play.data.validation.Check;

public class IsCertificateOrBlankString extends Check {

	@Override
	public boolean isSatisfied(Object validatedObject, Object value) {
		try {
			String s = value.toString();
			if (StringUtils.isEmpty(StringUtils.trimToEmpty(s))) {
				return true;
			}
			Util.stringToCertificates(s);
		} catch (Throwable t) {
			this.setMessage("validation.certificate_string");
			return false;
		}
		return true;
	}
}
