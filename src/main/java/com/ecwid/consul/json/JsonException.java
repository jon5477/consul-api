/*********************************************************************
* Copyright (c) 2024 Jon Huang
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package com.ecwid.consul.json;

/**
 * Exception when JSON serialization / deserialization fails.
 * 
 * @author Jon Huang (jon5477)
 *
 */
public final class JsonException extends RuntimeException {
	private static final long serialVersionUID = 3868689254489977799L;

	public JsonException() {
		super();
	}

	public JsonException(String message) {
		super(message);
	}

	public JsonException(String message, Throwable cause) {
		super(message, cause);
	}

	public JsonException(Throwable cause) {
		super(cause);
	}
}