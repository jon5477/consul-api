package com.ecwid.consul;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

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
	private static BodyHandler<InputStream> anyBodyHandler() {
		return Mockito.any(BodyHandler.class);
	}

	@SuppressWarnings({ "unchecked", "resource" })
	private static HttpResponse<InputStream> createMockResponse() {
		ByteArrayInputStream bais = new ByteArrayInputStream(new byte[0]);
		HttpResponse<InputStream> resp = Mockito.mock(HttpResponse.class);
		HttpHeaders headers = Mockito.mock(HttpHeaders.class);
		Mockito.when(resp.statusCode()).thenReturn(200);
		Mockito.when(resp.headers()).thenReturn(headers);
		Mockito.when(resp.body()).thenReturn(bais);
		return resp;
	}

	@Test
	void verifyDefaultUrl() throws Exception {
		// Given
		HttpClient httpClient = Mockito.mock(HttpClient.class);
		HttpResponse<InputStream> mockResponse = createMockResponse();
		Mockito.when(httpClient.send(Mockito.any(HttpRequest.class), anyBodyHandler())).thenReturn(mockResponse);
		ConsulRawClient client = new ConsulRawClient.Builder().setHttpClient(httpClient).setHost(HOST).setPort(PORT)
				.build();
		// When
		client.makeGetRequest(ENDPOINT, EMPTY_QUERY_PARAMS);

		// Then
		ArgumentCaptor<HttpRequest> calledUri = ArgumentCaptor.forClass(HttpRequest.class);
		Mockito.verify(httpClient).send(calledUri.capture(), anyBodyHandler());
		assertEquals(EXPECTED_AGENT_ADDRESS_NO_PATH, calledUri.getValue().uri().toString());
	}

	@Test
	void verifyUrlWithPath() throws Exception {
		// Given
		HttpClient httpClient = Mockito.mock(HttpClient.class);
		HttpResponse<InputStream> mockResponse = createMockResponse();
		Mockito.when(httpClient.send(Mockito.any(HttpRequest.class), anyBodyHandler())).thenReturn(mockResponse);
		ConsulRawClient client = new ConsulRawClient.Builder().setHttpClient(httpClient).setHost(HOST).setPort(PORT)
				.setPath(PATH).build();

		// When
		client.makeGetRequest(ENDPOINT, EMPTY_QUERY_PARAMS);

		// Then
		ArgumentCaptor<HttpRequest> calledUri = ArgumentCaptor.forClass(HttpRequest.class);
		Mockito.verify(httpClient).send(calledUri.capture(), anyBodyHandler());
		assertEquals(EXPECTED_AGENT_ADDRESS, calledUri.getValue().uri().toString());
	}
}
