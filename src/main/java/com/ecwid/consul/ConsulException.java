package com.ecwid.consul;

/**
 * Base exception for any consul errors
 *
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public class ConsulException extends RuntimeException {
	private static final long serialVersionUID = -3520481098875184703L;

	public ConsulException() {
	}

	public ConsulException(Throwable cause) {
		super(cause);
	}

	public ConsulException(String message) {
		super(message);
	}
}