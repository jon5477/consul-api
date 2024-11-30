package com.ecwid.consul.transport;

import java.net.URI;
import java.net.http.HttpClient;

import org.checkerframework.checker.nullness.qual.NonNull;

import com.ecwid.consul.TimedOutException;
import com.ecwid.consul.v1.Request;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 * @author Jon Huang (jon5477)
 */
public interface HttpTransport {
	HttpClient getHttpClient();

	/**
	 * Makes a HTTP request to the given Consul Agent {@link URI} with the specified
	 * {@link HttpMethod} and {@link Request}.
	 * 
	 * @param agentUri The Consul Agent {@link URI}.
	 * @param method   The HTTP method to use.
	 * @param request  The {@link Request} to make.
	 * @return The {@link ConsulHttpResponse} from the given API.
	 * @throws TimedOutException If the request timed-out, this can happen due to a
	 *                           {@link Thread#interrupt()} or an
	 *                           {@link InterruptedException}.
	 */
	ConsulHttpResponse makeRequest(@NonNull URI agentUri, @NonNull HttpMethod method, @NonNull Request request)
			throws TimedOutException;
}