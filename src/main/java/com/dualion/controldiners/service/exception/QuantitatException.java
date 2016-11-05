package com.dualion.controldiners.service.exception;

public class QuantitatException extends Exception {

	private static final long serialVersionUID = 1L;

	public QuantitatException() {
		super();
	}
	
	public QuantitatException(String message) {
		super(message);
	}

	public QuantitatException(Throwable cause) {
		super(cause);
	}

	public QuantitatException(String message, Throwable cause) {
		super(message, cause);
	}

	public QuantitatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
}