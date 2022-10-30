package com.openseedbox.code;

import play.exceptions.PlayExceptionWithJavaSource;

public class MessageException extends PlayExceptionWithJavaSource {
	public MessageException(String message, Throwable t) {
		super(message,t);
	}

	@Override
	public String getErrorTitle() {
		return getMessage();
	}

	@Override
	public String getErrorDescription() {
		return getCause().getMessage();
	}

	public MessageException(String message) {
		super(message);
	}
}
