package com.ecwid.consul.transport;

import com.ecwid.consul.ConsulException;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public class TransportException extends ConsulException {
	private static final long serialVersionUID = -799287906786269285L;

	public TransportException(Throwable cause) {
		super(cause);
	}
}