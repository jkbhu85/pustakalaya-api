package com.jk.ptk.app;

public class ResourceExpiredException extends Exception {
	private static final long serialVersionUID = 257305670729719601L;

	public ResourceExpiredException() {
		super();
	}

	public ResourceExpiredException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ResourceExpiredException(String message, Throwable cause) {
		super(message, cause);
	}

	public ResourceExpiredException(String message) {
		super(message);
	}

	public ResourceExpiredException(Throwable cause) {
		super(cause);
	}


}
