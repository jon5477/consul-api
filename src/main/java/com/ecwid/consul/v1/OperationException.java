package com.ecwid.consul.v1;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import com.ecwid.consul.ConsulException;
import com.ecwid.consul.transport.ConsulHttpResponse;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 * @author Jon Huang (jon5477)
 *
 */
public final class OperationException extends ConsulException {
	private static final long serialVersionUID = 7189026270432209391L;
	private final int statusCode;
	private final JsonNode statusContent;

	public OperationException(int statusCode, @Nullable JsonNode statusContent) {
		super("OperationException(statusCode=" + statusCode + "', statusContent='" + statusContent + "')");
		this.statusCode = statusCode;
		this.statusContent = statusContent;
	}

	public OperationException(@NonNull ConsulHttpResponse httpResponse) {
		this(httpResponse.getStatusCode(), httpResponse.getContent());
	}

	public int getStatusCode() {
		return statusCode;
	}

	@Nullable
	public JsonNode getStatusContent() {
		return statusContent;
	}

	@Override
	public String toString() {
		return "OperationException [statusCode=" + statusCode + ", statusContent=" + statusContent + "]";
	}
}