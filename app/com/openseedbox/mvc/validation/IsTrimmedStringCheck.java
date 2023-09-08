package com.openseedbox.mvc.validation;

import net.sf.oval.ValidationCycle;
import net.sf.oval.configuration.annotation.AbstractAnnotationCheck;
import net.sf.oval.exception.OValException;
import org.apache.commons.lang3.StringUtils;

public class IsTrimmedStringCheck extends AbstractAnnotationCheck<IsTrimmedString> {

    static final String msg = "validation.trimmed_string";

    @Override
    public void configure(IsTrimmedString isTrimmedString) {
        setMessage(isTrimmedString.message());
    }

    @Override
    public boolean isSatisfied(Object validatedObject, Object value, ValidationCycle cycle) throws OValException {
        String s = value.toString();
        if (!StringUtils.trimToEmpty(s).equals(s)) {
            return false;
        }
        return true;
    }
}
