package com.ecwid.consul;

/**
 * Base exception for all Consul errors.
 *
 * @author Vasily Vasilkov (vgv@ecwid.com)
 * @author Jon Huang (jon5477)
 */
public class ConsulException extends RuntimeException {
	private static final long serialVersionUID = -7401694043589719175L;

	public ConsulException() {
		super();
	}

	public ConsulException(String message) {
		super(message);
	}

	public ConsulException(String message, Throwable cause) {
		super(cause);
	}

	public ConsulException(Throwable cause) {
		super(cause);
	}
}