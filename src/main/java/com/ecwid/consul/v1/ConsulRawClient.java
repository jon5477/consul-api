package com.ecwid.consul.v1;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URIBuilder;
import org.checkerframework.checker.nullness.qual.Nullable;

import com.ecwid.consul.QueryParameters;
import com.ecwid.consul.Utils;
import com.ecwid.consul.transport.ClientUtils;
import com.ecwid.consul.transport.HttpRequest;
import com.ecwid.consul.transport.HttpResponse;
import com.ecwid.consul.transport.HttpTransport;
import com.ecwid.consul.transport.TLSConfig;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public final class ConsulRawClient {
	public static final String DEFAULT_HOST = "localhost";
	public static final int DEFAULT_PORT = 8500;
	public static final String DEFAULT_PATH = "";
	private final HttpTransport httpTransport;
	private final URL agentAddress;
	private final AtomicReference<char[]> token = new AtomicReference<>();

	public static final class Builder {
		private String agentHost;
		private int agentPort;
		private String agentPath;
		// Still allow legacy TLSConfig for now
		private TLSConfig tlsConfig;
		// Allow clients to specify their own Apache HTTP Client configuration
		private HttpClientConnectionManager connectionManager;
		private HttpClient httpClient;

		public Builder() {
			this(DEFAULT_HOST, DEFAULT_PORT);
		}

		public Builder(String agentHost, int agentPort) {
			this(agentHost, agentPort, DEFAULT_PATH);
		}

		public Builder(String agentHost, int agentPort, String agentPath) {
			this.agentHost = agentHost;
			this.agentPort = agentPort;
			this.agentPath = agentPath;
		}

		public Builder setHost(String host) {
			this.agentHost = host;
			return this;
		}

		public Builder setPort(int port) {
			this.agentPort = port;
			return this;
		}

		public Builder setPath(String path) {
			this.agentPath = path;
			return this;
		}

		public Builder setTlsConfig(TLSConfig tlsConfig) {
			this.tlsConfig = tlsConfig;
			return this;
		}

		public Builder setConnectionManager(HttpClientConnectionManager connectionManager) {
			this.connectionManager = connectionManager;
			return this;
		}

		public Builder setHttpClient(HttpClient httpClient) {
			this.httpClient = httpClient;
			return this;
		}

		public ConsulRawClient build() {
			HttpTransport httpTransport = ClientUtils.createDefaultHttpTransport(connectionManager, httpClient,
					tlsConfig);
			return new ConsulRawClient(this, httpTransport);
		}
	}

	private ConsulRawClient(Builder b, HttpTransport httpTransport) {
		this.httpTransport = httpTransport;
		// check if the agentHost has a scheme or not
		String agentHostLc = b.agentHost.toLowerCase();
		String agentHostUrl;
		if (!agentHostLc.startsWith("https://") && !agentHostLc.startsWith("http://")) {
			// no scheme in host, use default 'http'
			agentHostUrl = "http://" + b.agentHost;
		} else {
			agentHostUrl = b.agentHost;
		}
		try {
			this.agentAddress = new URL(Utils.assembleAgentAddress(agentHostUrl, b.agentPort, b.agentPath));
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	public char[] getToken() {
		char[] arr = this.token.get();
		return Arrays.copyOf(arr, arr.length); // defensive copy
	}

	public void setToken(char[] token) {
		if (token != null) {
			this.token.set(Arrays.copyOf(token, token.length)); // defensive copy
		} else {
			this.token.set(null);
		}
	}

	private char[] getConsulToken(Request request) {
		char[] consulToken = null;
		if (request != null) {
			consulToken = request.getToken();
		}
		if (consulToken == null) {
			consulToken = token.get();
		}
		return consulToken;
	}

	public HttpResponse makeGetRequest(String endpoint, QueryParameters... queryParams) {
		URI uri = buildURI(endpoint, queryParams);
		HttpRequest request = HttpRequest.Builder.newBuilder().setURI(uri).setToken(token.get()).build();
		return httpTransport.makeGetRequest(request);
	}

	public HttpResponse makeGetRequest(String endpoint, List<QueryParameters> queryParams) {
		return makeGetRequest(endpoint, queryParams.toArray(QueryParameters[]::new));
	}

	public HttpResponse makeGetRequest(Request request) {
		URI uri = buildURI(request.getEndpoint(), request);
		HttpRequest httpRequest = HttpRequest.Builder.newBuilder().setURI(uri).setToken(getConsulToken(request))
				.build();
		return httpTransport.makeGetRequest(httpRequest);
	}

	public HttpResponse makePutRequest(String endpoint, @Nullable byte[] content, QueryParameters... queryParams) {
		URI uri = buildURI(endpoint, queryParams);
		HttpRequest.Builder request = HttpRequest.Builder.newBuilder().setURI(uri).setToken(token.get());
		if (content != null) {
			request.setContent(content);
		}
		return httpTransport.makePutRequest(request.build());
	}

	public HttpResponse makePutRequest(String endpoint, @Nullable byte[] content, List<QueryParameters> queryParams) {
		return makePutRequest(endpoint, content, queryParams.toArray(QueryParameters[]::new));
	}

	public HttpResponse makePutRequest(Request request) {
		URI uri = buildURI(request.getEndpoint(), request);
		HttpRequest.Builder reqBuilder = HttpRequest.Builder.newBuilder().setURI(uri).setToken(getConsulToken(request));
		if (request.getBinaryContent() != null) {
			reqBuilder.setContent(request.getBinaryContent());
		}
		HttpRequest httpRequest = reqBuilder.build();
		return httpTransport.makePutRequest(httpRequest);
	}

	public HttpResponse makeDeleteRequest(Request request) {
		URI uri = buildURI(request.getEndpoint(), request);
		HttpRequest httpRequest = HttpRequest.Builder.newBuilder().setURI(uri).setToken(getConsulToken(request))
				.setContent(request.getBinaryContent()).build();
		return httpTransport.makeDeleteRequest(httpRequest);
	}

	/**
	 * Creates a full {@link URI} to the Consul API endpoint.
	 * 
	 * @param endpoint    The Consul API endpoint to use.
	 * @param queryParams The query parameters to include.
	 * @return The {@link URI} to the Consul API endpoint including query
	 *         parameters.
	 */
	private URI buildURI(String endpoint, QueryParameters... queryParams) {
		try {
			List<NameValuePair> nvps = Arrays.stream(queryParams)
					.flatMap(e -> e.getQueryParameters().entrySet().stream())
					.map(e -> new BasicNameValuePair(e.getKey(), e.getValue())).collect(Collectors.toList());
			return new URIBuilder().setScheme(agentAddress.getProtocol()).setUserInfo(agentAddress.getUserInfo())
					.setHost(agentAddress.getHost()).setPort(agentAddress.getPort())
					.setPath(agentAddress.getPath() + endpoint).addParameters(nvps).build();
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
}