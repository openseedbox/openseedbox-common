package com.openseedbox.code;

public class MessageException extends RuntimeException {
	public MessageException(String message) {
		super(message);
	}
	
	public MessageException(String message, Object... args) {
		super(String.format(message, args));
	}
	
	public MessageException(Throwable t, String message, Object... args) {
		super(String.format(message, args), t);
	}
}
