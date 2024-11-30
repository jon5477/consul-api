package com.ecwid.consul.v1;

import java.io.IOException;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import com.ecwid.consul.ConsulException;
import com.ecwid.consul.QueryParameters;
import com.ecwid.consul.Utils;
import com.ecwid.consul.transport.ClientUtils;
import com.ecwid.consul.transport.ConsulHttpResponse;
import com.ecwid.consul.transport.DefaultHttpTransport;
import com.ecwid.consul.transport.HttpMethod;
import com.ecwid.consul.transport.HttpTransport;
import com.ecwid.consul.transport.TLSConfig;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 * @author Jon Huang (jon5477)
 */
public final class ConsulRawClient {
	private static final String ENDPOINT_NOT_NULL_MSG = "endpoint cannot be null";
	private static final String REQUEST_NOT_NULL_MSG = "request cannot be null";
	public static final String DEFAULT_HOST = "localhost";
	public static final int DEFAULT_PORT = 8500;
	public static final String DEFAULT_PATH = "";
	private final HttpTransport httpTransport;
	private final URI agentUri;
	private final AtomicReference<char[]> token = new AtomicReference<>();

	public static final class Builder {
		private String agentHost;
		private int agentPort;
		private String agentPath;
		private ProxySelector proxy;
		private SSLContext sslCtx;
		private SSLParameters sslParams;
		private Executor executor;
		// Allow clients to specify their own HTTP Client
		private HttpClient httpClient;
		private char[] token;

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

		public Builder setProxy(ProxySelector proxy) {
			this.proxy = proxy;
			return this;
		}

		/**
		 * Sets the {@link TLSConfig} option for the Consul client. This will be used to
		 * automatically create the {@link SSLContext}.
		 * 
		 * @deprecated Use {@link #setSSLContext(SSLContext)} to pass SSL/TLS
		 *             configuration instead.
		 * 
		 * @param tlsConfig The {@link TLSConfig} option for the Consul client
		 * @return This {@link Builder} instance for method chaining.
		 */
		@Deprecated(since = "2.0.0", forRemoval = true)
		public Builder setTlsConfig(TLSConfig tlsConfig) {
			try {
				this.sslCtx = ClientUtils.createSSLContext(tlsConfig);
			} catch (UnrecoverableKeyException | KeyManagementException | KeyStoreException | NoSuchAlgorithmException
					| CertificateException | IOException e) {
				throw new ConsulException(e);
			}
			return this;
		}

		public Builder setSSLContext(SSLContext sslCtx) {
			this.sslCtx = sslCtx;
			return this;
		}

		public Builder setSSLParameters(SSLParameters sslParams) {
			this.sslParams = sslParams;
			return this;
		}

		public Builder setExecutor(Executor executor) {
			this.executor = executor;
			return this;
		}

		public Builder setHttpClient(HttpClient httpClient) {
			this.httpClient = httpClient;
			return this;
		}

		public Builder setToken(CharSequence token) {
			if (token != null) {
				this.setToken(Utils.charSequenceToArray(token));
			}
			return this;
		}

		public Builder setToken(char[] token) {
			this.token = Arrays.copyOf(token, token.length);
			return this;
		}

		public ConsulRawClient build() {
			HttpTransport httpTransport;
			if (httpClient != null) {
				httpTransport = new DefaultHttpTransport(httpClient);
			} else {
				httpTransport = ClientUtils.createDefaultHttpTransport(proxy, sslCtx, sslParams, executor);
			}
			return new ConsulRawClient(this, httpTransport);
		}
	}

	private ConsulRawClient(Builder b, HttpTransport httpTransport) {
		this.httpTransport = httpTransport;
		// check if the agentHost has a scheme or not
		String agentHostLc = b.agentHost.toLowerCase(Locale.ROOT);
		String agentHostUrl;
		if (!agentHostLc.startsWith("https://") && !agentHostLc.startsWith("http://")) {
			// no scheme in host, use default 'http'
			agentHostUrl = "http://" + b.agentHost;
		} else {
			agentHostUrl = b.agentHost;
		}
		try {
			this.agentUri = new URI(Utils.assembleAgentAddress(agentHostUrl, b.agentPort, b.agentPath));
		} catch (URISyntaxException e) {
			throw new ConsulException(e);
		}
		this.token.set(b.token);
	}

	/**
	 * Fetches the Consul API token currently set on this client.
	 * 
	 * @return The Consul API token that is set on the client.
	 */
	@Nullable
	public final char[] getToken() {
		char[] arr = this.token.get();
		if (arr != null) {
			return Arrays.copyOf(arr, arr.length); // defensive copy
		}
		return null;
	}

	/**
	 * Sets the Consul API token that will be included in the X-Consul-Token HTTP
	 * header on all requests.
	 * 
	 * @param token The Consul API token to set.
	 */
	public final void setToken(char[] token) {
		if (token != null) {
			this.token.set(Arrays.copyOf(token, token.length)); // defensive copy
		} else {
			this.token.set(null);
		}
	}

	/**
	 * Sets the Consul API token that will be included in the X-Consul-Token HTTP
	 * header on all requests.
	 * 
	 * @param token The Consul API token to set.
	 */
	public final void setToken(@Nullable CharSequence token) {
		if (token != null) {
			this.token.set(Utils.charSequenceToArray(token));
		} else {
			this.token.set(null);
		}
	}

	public final ConsulHttpResponse makeGetRequest(@NonNull String endpoint, QueryParameters... queryParams) {
		Objects.requireNonNull(endpoint, ENDPOINT_NOT_NULL_MSG);
		Request request = new Request.Builder(endpoint).addQueryParameters(queryParams).setToken(getToken()).build();
		return httpTransport.makeRequest(agentUri, HttpMethod.GET, request);
	}

	public final ConsulHttpResponse makeGetRequest(@NonNull String endpoint, List<QueryParameters> queryParams) {
		Objects.requireNonNull(endpoint, ENDPOINT_NOT_NULL_MSG);
		return makeGetRequest(endpoint, queryParams.toArray(QueryParameters[]::new));
	}

	public final ConsulHttpResponse makeGetRequest(@NonNull Request request) {
		Objects.requireNonNull(request, REQUEST_NOT_NULL_MSG);
		if (request.getToken() == null) {
			request.setToken(getToken());
		}
		return httpTransport.makeRequest(agentUri, HttpMethod.GET, request);
	}

	/**
	 * Makes a HTTP PUT request for the following endpoint, content, and query
	 * parameters.
	 * 
	 * @param endpoint    The HTTP endpoint to call.
	 * @param content     The HTTP content body, can be {@code null} if there is no
	 *                    content.
	 * @param queryParams The HTTP query parameters to attach.
	 * @return The {@link HttpResponse} after making the HTTP request.
	 */
	public final ConsulHttpResponse makePutRequest(@NonNull String endpoint, byte[] content,
			QueryParameters... queryParams) {
		Objects.requireNonNull(endpoint, ENDPOINT_NOT_NULL_MSG);
		Request.Builder request = new Request.Builder(endpoint).addQueryParameters(queryParams).setToken(getToken());
		if (content != null) {
			request.setContent(content);
		}
		return httpTransport.makeRequest(agentUri, HttpMethod.PUT, request.build());
	}

	/**
	 * Makes a HTTP PUT request for the following endpoint, content, and query
	 * parameters.
	 * 
	 * @param endpoint    The HTTP endpoint to call.
	 * @param content     The HTTP content body, can be {@code null} if there is no
	 *                    content.
	 * @param queryParams The HTTP query parameters to attach.
	 * @return The {@link HttpResponse} after making the HTTP request.
	 */
	public final ConsulHttpResponse makePutRequest(@NonNull String endpoint, byte[] content,
			List<QueryParameters> queryParams) {
		Objects.requireNonNull(endpoint, ENDPOINT_NOT_NULL_MSG);
		return makePutRequest(endpoint, content, queryParams.toArray(QueryParameters[]::new));
	}

	/**
	 * Makes a HTTP PUT request with the given {@link Request}.
	 * 
	 * @param request The HTTP {@link Request} to send.
	 * @return The {@link HttpResponse} after making the HTTP request.
	 */
	public final ConsulHttpResponse makePutRequest(@NonNull Request request) {
		Objects.requireNonNull(request, REQUEST_NOT_NULL_MSG);
		if (request.getToken() == null) {
			request.setToken(getToken());
		}
		return httpTransport.makeRequest(agentUri, HttpMethod.PUT, request);
	}

	public final ConsulHttpResponse makeDeleteRequest(@NonNull Request request) {
		Objects.requireNonNull(request, REQUEST_NOT_NULL_MSG);
		if (request.getToken() == null) {
			request.setToken(getToken());
		}
		return httpTransport.makeRequest(agentUri, HttpMethod.DELETE, request);
	}
}