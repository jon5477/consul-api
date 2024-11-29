package com.ecwid.consul;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandler;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import com.ecwid.consul.v1.ConsulRawClient;
import com.ecwid.consul.v1.QueryParams;

class ConsulRawClientTest {
	private static final String ENDPOINT = "/any/endpoint";
	private static final QueryParams EMPTY_QUERY_PARAMS = new QueryParams.Builder().build();
	private static final String HOST = "host";
	private static final int PORT = 8888;
	private static final String PATH = "path";
	private static final String EXPECTED_AGENT_ADDRESS_NO_PATH = "http://" + HOST + ":" + PORT + ENDPOINT;
	private static final String EXPECTED_AGENT_ADDRESS = "http://" + HOST + ":" + PORT + "/" + PATH + ENDPOINT;

	@SuppressWarnings("unchecked")
	private static <T> BodyHandler<T> anyBodyHandler() {
		return (BodyHandler<T>) any(BodyHandler.class);
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
		ArgumentCaptor<HttpRequest> calledUri = ArgumentCaptor.forClass(HttpRequest.class);
		verify(httpClient).send(calledUri.capture(), anyBodyHandler());
		assertEquals(EXPECTED_AGENT_ADDRESS_NO_PATH, calledUri.getValue().uri().toString());
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
		ArgumentCaptor<HttpRequest> calledUri = ArgumentCaptor.forClass(HttpRequest.class);
		verify(httpClient).send(calledUri.capture(), anyBodyHandler());
		assertEquals(EXPECTED_AGENT_ADDRESS, calledUri.getValue().uri().toString());
	}
}
