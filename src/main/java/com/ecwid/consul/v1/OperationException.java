package com.ecwid.consul.v1;

import com.ecwid.consul.ConsulException;
import com.ecwid.consul.transport.HttpResponse;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public final class OperationException extends ConsulException {
	private static final long serialVersionUID = 7189026270432209391L;
	private final int statusCode;
	private final String statusMessage;
	private final JsonNode statusContent;

	public OperationException(int statusCode, String statusMessage, JsonNode statusContent) {
		super("OperationException(statusCode=" + statusCode + ", statusMessage='" + statusMessage + "', statusContent='"
				+ statusContent + "')");
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
		this.statusContent = statusContent;
	}

	public OperationException(HttpResponse httpResponse) {
		this(httpResponse.getStatusCode(), httpResponse.getStatusMessage(), httpResponse.getContent());
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public JsonNode getStatusContent() {
		return statusContent;
	}

	@Override
	public String toString() {
		return "OperationException [statusCode=" + statusCode + ", statusMessage=" + statusMessage + ", statusContent="
				+ statusContent + "]";
	}
}