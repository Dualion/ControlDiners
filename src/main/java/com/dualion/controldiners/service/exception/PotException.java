package com.dualion.controldiners.service.exception;

public class PotException extends Exception {

	private static final long serialVersionUID = 1L;

	public PotException() {
		super();
	}
	
	public PotException(String message) {
		super(message);
	}

	public PotException(Throwable cause) {
		super(cause);
	}

	public PotException(String message, Throwable cause) {
		super(message, cause);
	}

	public PotException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
}