package com.ecwid.consul.transport;

import java.io.IOException;

import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.checkerframework.checker.nullness.qual.NonNull;

final class HttpConsulResponseHandler implements HttpClientResponseHandler<HttpResponse> {
	public static final HttpConsulResponseHandler INSTANCE = new HttpConsulResponseHandler();

	private HttpConsulResponseHandler() {
		// singleton
	}

	@Override
	public final HttpResponse handleResponse(@NonNull ClassicHttpResponse response) throws HttpException, IOException {
		return HttpUtils.parseResponse(response);
	}
}