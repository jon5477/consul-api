package com.ecwid.consul.transport;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.classic.methods.HttpUriRequest;
import org.apache.hc.client5.http.classic.methods.HttpUriRequestBase;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.io.entity.ByteArrayEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicHeader;
import org.apache.hc.core5.util.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecwid.consul.ConsulException;

public abstract class AbstractHttpTransport implements HttpTransport {
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractHttpTransport.class);
	static final int DEFAULT_MAX_CONNECTIONS = 1000;
	static final int DEFAULT_MAX_PER_ROUTE_CONNECTIONS = 500;
	static final Timeout DEFAULT_CONNECTION_TIMEOUT = Timeout.ofSeconds(10);
	/**
	 * 10 minutes for read timeout due to blocking queries timeout
	 * 
	 * @see https://www.consul.io/api/index.html#blocking-queries
	 */
	static final Timeout DEFAULT_READ_TIMEOUT = Timeout.ofMinutes(10);

	@Override
	public HttpResponse makeGetRequest(HttpRequest request) {
		HttpGet httpGet = new HttpGet(request.getUrl());
		addHeadersToRequest(httpGet, request.getToken(), request.getHeaders());
		return executeRequest(httpGet);
	}

	@Override
	public HttpResponse makePutRequest(HttpRequest request) {
		HttpPut httpPut = new HttpPut(request.getUrl());
		addHeadersToRequest(httpPut, request.getToken(), request.getHeaders());
		if (request.getContent() != null) {
			httpPut.setEntity(new ByteArrayEntity(request.getContent(), null));
		}
		return executeRequest(httpPut);
	}

	@Override
	public HttpResponse makeDeleteRequest(HttpRequest request) {
		HttpDelete httpDelete = new HttpDelete(request.getUrl());
		addHeadersToRequest(httpDelete, request.getToken(), request.getHeaders());
		return executeRequest(httpDelete);
	}

	/**
	 * You should override this method to instantiate ready to use HttpClient
	 *
	 * @return HttpClient
	 */
	protected abstract HttpClient getHttpClient();

	private HttpResponse executeRequest(HttpUriRequest httpRequest) {
		logRequest(httpRequest);
		try {
			return getHttpClient().execute(httpRequest, response -> {
				int statusCode = response.getCode();
				String statusMessage = response.getReasonPhrase();
				String content = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
				Long consulIndex = parseUnsignedLong(response.getFirstHeader("X-Consul-Index"));
				Boolean consulKnownLeader = parseBoolean(response.getFirstHeader("X-Consul-Knownleader"));
				Long consulLastContact = parseUnsignedLong(response.getFirstHeader("X-Consul-Lastcontact"));
				return new HttpResponse(statusCode, statusMessage, content, consulIndex, consulKnownLeader,
						consulLastContact);
			});
		} catch (IOException e) {
			throw new TransportException(e);
		}
	}

	private Long parseUnsignedLong(Header header) {
		if (header == null) {
			return null;
		}
		String value = header.getValue();
		if (value == null) {
			return null;
		}
		try {
			return Long.parseUnsignedLong(value);
		} catch (Exception e) {
			return null;
		}
	}

	private Boolean parseBoolean(Header header) {
		if (header == null) {
			return null;
		}
		if ("true".equals(header.getValue())) {
			return true;
		}
		if ("false".equals(header.getValue())) {
			return false;
		}
		return null;
	}

	private void addHeadersToRequest(HttpUriRequestBase request, char[] token, Map<String, String> headers) {
		if (token != null) {
			request.addHeader(new BasicHeader("X-Consul-Token", new String(token), true));
		}
		if (headers != null && !headers.isEmpty()) {
			for (Map.Entry<String, String> headerValue : headers.entrySet()) {
				String name = headerValue.getKey();
				String value = headerValue.getValue();
				request.addHeader(name, value);
			}
		}
	}

	private void logRequest(HttpUriRequest httpRequest) {
		if (LOGGER.isTraceEnabled()) {
			StringBuilder sb = new StringBuilder();
			// method
			sb.append(httpRequest.getMethod());
			sb.append(" ");
			// url
			try {
				sb.append(httpRequest.getUri());
			} catch (URISyntaxException e) {
				throw new ConsulException(e);
			}
			sb.append(" ");
			// headers, if any
			Iterator<Header> iterator = httpRequest.headerIterator();
			if (iterator.hasNext()) {
				sb.append("Headers:[");
				Header header = iterator.next();
				sb.append(header.getName()).append("=").append(header.getValue());
				while (iterator.hasNext()) {
					header = iterator.next();
					sb.append(header.getName()).append("=").append(header.getValue());
					sb.append(";");
				}
				sb.append("] ");
			}
			LOGGER.trace(sb.toString());
		}
	}
}