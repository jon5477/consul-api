package com.ecwid.consul.transport;

import com.ecwid.consul.ConsulException;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 * @author Jon Huang (jon5477)
 */
public class TransportException extends ConsulException {
	private static final long serialVersionUID = -799287906786269285L;

	public TransportException() {
		super();
	}

	public TransportException(String message) {
		super(message);
	}

	public TransportException(String message, Throwable cause) {
		super(message, cause);
	}

	public TransportException(Throwable cause) {
		super(cause);
	}
}