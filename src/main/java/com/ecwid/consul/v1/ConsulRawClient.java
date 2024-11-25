package com.ecwid.consul.v1;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import javax.net.ssl.SSLContext;

import org.apache.hc.client5.http.async.HttpAsyncClient;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URIBuilder;

import com.ecwid.consul.QueryParameters;
import com.ecwid.consul.Utils;
import com.ecwid.consul.transport.DefaultHttpTransport;
import com.ecwid.consul.transport.DefaultHttpsTransport;
import com.ecwid.consul.transport.HttpRequest;
import com.ecwid.consul.transport.HttpResponse;
import com.ecwid.consul.transport.HttpTransport;
import com.ecwid.consul.transport.TLSConfig;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public class ConsulRawClient {
	public static final String DEFAULT_HOST = "localhost";
	public static final int DEFAULT_PORT = 8500;
	public static final String DEFAULT_PATH = "";

	// one real HTTP client for all instances
	private static final HttpTransport DEFAULT_HTTP_TRANSPORT = new DefaultHttpTransport();

	private final HttpTransport httpTransport;
	private final URL agentAddress;
	private final AtomicReference<char[]> token = new AtomicReference<>();

	public static final class Builder {
		private String agentHost;
		private int agentPort;
		private String agentPath;
		// Allow clients to specify their own SSL Context
		private SSLContext sslCtx;
		// Allow clients to specify their own Apache HTTP Clients
		private HttpClient httpClient;
		private HttpAsyncClient asyncHttpClient;

		public static ConsulRawClient.Builder builder() {
			return new ConsulRawClient.Builder();
		}

		private Builder() {
			this.agentHost = DEFAULT_HOST;
			this.agentPort = DEFAULT_PORT;
			this.agentPath = DEFAULT_PATH;
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

		public Builder setSSLContext(SSLContext sslCtx) {
			this.sslCtx = sslCtx;
			return this;
		}

		public Builder setHttpClient(HttpClient httpClient) {
			this.httpClient = httpClient;
			return this;
		}

		public Builder setAsyncHttpClient(HttpAsyncClient asyncHttpClient) {
			this.asyncHttpClient = asyncHttpClient;
			return this;
		}

		public ConsulRawClient build() {
			// The HTTP transport is determined by the presence of an SSL context
			HttpTransport httpTransport = sslCtx != null
					? new DefaultHttpsTransport(sslCtx, httpClient, asyncHttpClient)
					: new DefaultHttpTransport(httpClient, asyncHttpClient);

			return new ConsulRawClient(httpTransport, agentHost, agentPort, agentPath);
		}
	}

	public ConsulRawClient() {
		this(DEFAULT_HOST);
	}

	@Deprecated(forRemoval = true)
	public ConsulRawClient(TLSConfig tlsConfig) {
		this(DEFAULT_HOST, tlsConfig);
	}

	public ConsulRawClient(String agentHost) {
		this(agentHost, DEFAULT_PORT);
	}

	@Deprecated(forRemoval = true)
	public ConsulRawClient(String agentHost, TLSConfig tlsConfig) {
		this(agentHost, DEFAULT_PORT, tlsConfig);
	}

	public ConsulRawClient(String agentHost, int agentPort) {
		this(DEFAULT_HTTP_TRANSPORT, agentHost, agentPort, DEFAULT_PATH);
	}

	public ConsulRawClient(HttpClient httpClient) {
		this(DEFAULT_HOST, httpClient);
	}

	@Deprecated(forRemoval = true)
	public ConsulRawClient(String agentHost, HttpClient httpClient) {
		this(new DefaultHttpTransport(httpClient), agentHost, DEFAULT_PORT, DEFAULT_PATH);
	}

	@Deprecated(forRemoval = true)
	public ConsulRawClient(String agentHost, int agentPort, HttpClient httpClient) {
		this(new DefaultHttpTransport(httpClient), agentHost, agentPort, DEFAULT_PATH);
	}

	@Deprecated(forRemoval = true)
	public ConsulRawClient(String agentHost, int agentPort, TLSConfig tlsConfig) {
		this(new DefaultHttpsTransport(tlsConfig), agentHost, agentPort, DEFAULT_PATH);
	}

	@Deprecated(forRemoval = true)
	public ConsulRawClient(HttpClient httpClient, String host, int port, String path) {
		this(new DefaultHttpTransport(httpClient), host, port, path);
	}

	// hidden constructor, for tests
	ConsulRawClient(HttpTransport httpTransport, String agentHost, int agentPort, String path) {
		this.httpTransport = httpTransport;
		// check if the agentHost has a scheme or not
		String agentHostLc = agentHost.toLowerCase();
		String agentHostUrl;
		if (!agentHostLc.startsWith("https://") && !agentHostLc.startsWith("http://")) {
			// no scheme in host, use default 'http'
			agentHostUrl = "http://" + agentHost;
		} else {
			agentHostUrl = agentHost;
		}
		try {
			this.agentAddress = new URL(Utils.assembleAgentAddress(agentHostUrl, agentPort, path));
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

	public HttpResponse makePutRequest(String endpoint, String content, QueryParameters... queryParams) {
		URI uri = buildURI(endpoint, queryParams);
		HttpRequest request = HttpRequest.Builder.newBuilder().setURI(uri).setToken(token.get()).setContent(content)
				.build();
		return httpTransport.makePutRequest(request);
	}

	public HttpResponse makePutRequest(String endpoint, String content, List<QueryParameters> queryParams) {
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