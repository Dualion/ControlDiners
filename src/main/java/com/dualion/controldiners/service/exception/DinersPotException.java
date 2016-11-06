package com.dualion.controldiners.service.exception;

public class DinersPotException extends Exception {

	private static final long serialVersionUID = 1L;

	public DinersPotException() {
		super();
	}
	
	public DinersPotException(String message) {
		super(message);
	}

	public DinersPotException(Throwable cause) {
		super(cause);
	}

	public DinersPotException(String message, Throwable cause) {
		super(message, cause);
	}

	public DinersPotException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
}