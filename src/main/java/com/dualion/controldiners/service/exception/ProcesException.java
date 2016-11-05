package com.dualion.controldiners.service.exception;

public class ProcesException extends Exception {

	private static final long serialVersionUID = 1L;

	public ProcesException() {
		super();
	}
	
	public ProcesException(String message) {
		super(message);
	}

	public ProcesException(Throwable cause) {
		super(cause);
	}

	public ProcesException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProcesException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
}