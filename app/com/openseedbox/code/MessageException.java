package com.openseedbox.code;

import play.exceptions.PlayException;

public class MessageException extends PlayException {
	public MessageException(String message, Throwable t) {
		super(message,t);
	}

	public MessageException(String message) {
		super(message);
	}
}
