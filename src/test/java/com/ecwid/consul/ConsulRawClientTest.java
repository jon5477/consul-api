package com.ecwid.consul;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpUriRequest;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import com.ecwid.consul.v1.ConsulRawClient;
import com.ecwid.consul.v1.QueryParams;

class ConsulRawClientTest {
	private static final String ENDPOINT = "/any/endpoint";
	private static final QueryParams EMPTY_QUERY_PARAMS = QueryParams.Builder.builder().build();
	private static final String HOST = "host";
	private static final int PORT = 8888;
	private static final String PATH = "path";
	private static final String EXPECTED_AGENT_ADDRESS_NO_PATH = "http://" + HOST + ":" + PORT + ENDPOINT;
	private static final String EXPECTED_AGENT_ADDRESS = "http://" + HOST + ":" + PORT + "/" + PATH + ENDPOINT;

	@SuppressWarnings("unchecked")
	private static <T> HttpClientResponseHandler<T> anyResponseHandler() {
		return (HttpClientResponseHandler<T>) any(HttpClientResponseHandler.class);
	}

	@Test
	void verifyDefaultUrl() throws Exception {
		// Given
		HttpClient httpClient = mock(HttpClient.class);
		ConsulRawClient client = new ConsulRawClient.Builder().setHttpClient(httpClient).setHost(HOST).setPort(PORT)
				.build();
		// When
		client.makeGetRequest(ENDPOINT, EMPTY_QUERY_PARAMS);

		// Then
		ArgumentCaptor<HttpUriRequest> calledUri = ArgumentCaptor.forClass(HttpUriRequest.class);
		verify(httpClient).execute(calledUri.capture(), anyResponseHandler());
		assertEquals(EXPECTED_AGENT_ADDRESS_NO_PATH, calledUri.getValue().getUri().toString());
	}

	@Test
	void verifyUrlWithPath() throws Exception {
		// Given
		HttpClient httpClient = mock(HttpClient.class);
		ConsulRawClient client = new ConsulRawClient.Builder().setHttpClient(httpClient).setHost(HOST).setPort(PORT)
				.setPath(PATH).build();

		// When
		client.makeGetRequest(ENDPOINT, EMPTY_QUERY_PARAMS);

		// Then
		ArgumentCaptor<HttpUriRequest> calledUri = ArgumentCaptor.forClass(HttpUriRequest.class);
		verify(httpClient).execute(calledUri.capture(), anyResponseHandler());
		assertEquals(EXPECTED_AGENT_ADDRESS, calledUri.getValue().getUri().toString());
	}
}
