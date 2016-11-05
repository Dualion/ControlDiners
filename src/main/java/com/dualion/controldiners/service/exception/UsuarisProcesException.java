package com.dualion.controldiners.service.exception;

public class UsuarisProcesException extends Exception {

	private static final long serialVersionUID = 1L;

	public UsuarisProcesException() {
		super();
	}
	
	public UsuarisProcesException(String message) {
		super(message);
	}

	public UsuarisProcesException(Throwable cause) {
		super(cause);
	}

	public UsuarisProcesException(String message, Throwable cause) {
		super(message, cause);
	}

	public UsuarisProcesException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
}