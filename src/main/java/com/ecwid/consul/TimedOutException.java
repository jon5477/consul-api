/*********************************************************************
* Copyright (c) 2024 Jon Huang
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package com.ecwid.consul;

/**
 * Represents when a Consul request has timed out. This is usually due to an
 * {@link InterruptedException}. This exception can be used to determine if a
 * Consul request has timed out due to Thread interruption.
 * 
 * @author Jon Huang (jon5477)
 *
 */
public class TimedOutException extends ConsulException {
	private static final long serialVersionUID = 2128231335344581714L;

	public TimedOutException() {
		super();
	}

	public TimedOutException(InterruptedException e) {
		super(e);
	}

	public TimedOutException(Throwable cause) {
		super(cause);
	}

	public TimedOutException(String message) {
		super(message);
	}
}