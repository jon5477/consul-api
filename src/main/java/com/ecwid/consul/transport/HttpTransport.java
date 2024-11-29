package com.ecwid.consul.transport;

import java.net.http.HttpClient;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 * @author Jon Huang (jon5477)
 */
public interface HttpTransport {
	HttpClient getHttpClient();

	ConsulHttpResponse makeGetRequest(ConsulHttpRequest request);

	ConsulHttpResponse makePutRequest(ConsulHttpRequest request);

	ConsulHttpResponse makeDeleteRequest(ConsulHttpRequest request);
}