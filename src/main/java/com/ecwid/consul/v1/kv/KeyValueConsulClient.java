package com.ecwid.consul.v1.kv;

import java.util.List;

import com.ecwid.consul.ConsulException;
import com.ecwid.consul.SingleUrlParameters;
import com.ecwid.consul.UrlParameters;
import com.ecwid.consul.json.JsonFactory;
import com.ecwid.consul.transport.HttpResponse;
import com.ecwid.consul.transport.TLSConfig;
import com.ecwid.consul.v1.ConsulRawClient;
import com.ecwid.consul.v1.OperationException;
import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.Request;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.kv.model.GetBinaryValue;
import com.ecwid.consul.v1.kv.model.GetValue;
import com.ecwid.consul.v1.kv.model.PutParams;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public final class KeyValueConsulClient implements KeyValueClient {
	private final ConsulRawClient rawClient;

	public KeyValueConsulClient(ConsulRawClient rawClient) {
		this.rawClient = rawClient;
	}

	public KeyValueConsulClient() {
		this(new ConsulRawClient());
	}

	public KeyValueConsulClient(String agentHost) {
		this(new ConsulRawClient(agentHost));
	}

	public KeyValueConsulClient(String agentHost, int agentPort) {
		this(new ConsulRawClient(agentHost, agentPort));
	}

	public KeyValueConsulClient(TLSConfig tlsConfig) {
		this(new ConsulRawClient(tlsConfig));
	}

	public KeyValueConsulClient(String agentHost, TLSConfig tlsConfig) {
		this(new ConsulRawClient(agentHost, tlsConfig));
	}

	public KeyValueConsulClient(String agentHost, int agentPort, TLSConfig tlsConfig) {
		this(new ConsulRawClient(agentHost, agentPort, tlsConfig));
	}

	@Override
	public Response<GetValue> getKVValue(String key) {
		return getKVValue(key, QueryParams.DEFAULT);
	}

	@Override
	public Response<GetValue> getKVValue(String key, String token) {
		return getKVValue(key, token, QueryParams.DEFAULT);
	}

	@Override
	public Response<GetValue> getKVValue(String key, QueryParams queryParams) {
		return getKVValue(key, null, queryParams);
	}

	@Override
	public Response<GetValue> getKVValue(String key, String token, QueryParams queryParams) {
		Request request = Request.Builder.newBuilder().setEndpoint("/v1/kv/" + key).addUrlParameter(queryParams)
				.setToken(token).build();
		HttpResponse httpResponse = rawClient.makeGetRequest(request);
		if (httpResponse.getStatusCode() == 200) {
			List<GetValue> value = JsonFactory.fromJson(httpResponse.getContent(),
					new TypeReference<List<GetValue>>() {
					});
			if (value.isEmpty()) {
				return new Response<>(null, httpResponse);
			} else if (value.size() == 1) {
				return new Response<>(value.get(0), httpResponse);
			} else {
				throw new ConsulException("Strange response (list size=" + value.size() + ")");
			}
		} else if (httpResponse.getStatusCode() == 404) {
			return new Response<>(null, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<GetBinaryValue> getKVBinaryValue(String key) {
		return getKVBinaryValue(key, QueryParams.DEFAULT);
	}

	@Override
	public Response<GetBinaryValue> getKVBinaryValue(String key, String token) {
		return getKVBinaryValue(key, token, QueryParams.DEFAULT);
	}

	@Override
	public Response<GetBinaryValue> getKVBinaryValue(String key, QueryParams queryParams) {
		return getKVBinaryValue(key, null, queryParams);
	}

	@Override
	public Response<GetBinaryValue> getKVBinaryValue(String key, String token, QueryParams queryParams) {
		Request request = Request.Builder.newBuilder().setEndpoint("/v1/kv/" + key).addUrlParameter(queryParams)
				.setToken(token).build();
		HttpResponse httpResponse = rawClient.makeGetRequest(request);
		if (httpResponse.getStatusCode() == 200) {
			List<GetBinaryValue> value = JsonFactory.fromJson(httpResponse.getContent(),
					new TypeReference<List<GetBinaryValue>>() {
					});
			if (value.isEmpty()) {
				return new Response<>(null, httpResponse);
			} else if (value.size() == 1) {
				return new Response<>(value.get(0), httpResponse);
			} else {
				throw new ConsulException("Strange response (list size=" + value.size() + ")");
			}
		} else if (httpResponse.getStatusCode() == 404) {
			return new Response<>(null, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<List<GetValue>> getKVValues(String keyPrefix) {
		return getKVValues(keyPrefix, QueryParams.DEFAULT);
	}

	@Override
	public Response<List<GetValue>> getKVValues(String keyPrefix, String token) {
		return getKVValues(keyPrefix, token, QueryParams.DEFAULT);
	}

	@Override
	public Response<List<GetValue>> getKVValues(String keyPrefix, QueryParams queryParams) {
		return getKVValues(keyPrefix, null, queryParams);
	}

	@Override
	public Response<List<GetValue>> getKVValues(String keyPrefix, String token, QueryParams queryParams) {
		UrlParameters recurseParam = new SingleUrlParameters("recurse");
		Request request = Request.Builder.newBuilder().setEndpoint("/v1/kv/" + keyPrefix).addUrlParameter(recurseParam)
				.addUrlParameter(queryParams).setToken(token).build();
		HttpResponse httpResponse = rawClient.makeGetRequest(request);
		if (httpResponse.getStatusCode() == 200) {
			List<GetValue> value = JsonFactory.fromJson(httpResponse.getContent(),
					new TypeReference<List<GetValue>>() {
					});
			return new Response<>(value, httpResponse);
		} else if (httpResponse.getStatusCode() == 404) {
			return new Response<>(null, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<List<GetBinaryValue>> getKVBinaryValues(String keyPrefix) {
		return getKVBinaryValues(keyPrefix, QueryParams.DEFAULT);
	}

	@Override
	public Response<List<GetBinaryValue>> getKVBinaryValues(String keyPrefix, String token) {
		return getKVBinaryValues(keyPrefix, token, QueryParams.DEFAULT);
	}

	@Override
	public Response<List<GetBinaryValue>> getKVBinaryValues(String keyPrefix, QueryParams queryParams) {
		return getKVBinaryValues(keyPrefix, null, queryParams);
	}

	@Override
	public Response<List<GetBinaryValue>> getKVBinaryValues(String keyPrefix, String token, QueryParams queryParams) {
		UrlParameters recurseParam = new SingleUrlParameters("recurse");
		Request request = Request.Builder.newBuilder().setEndpoint("/v1/kv/" + keyPrefix).addUrlParameter(recurseParam)
				.addUrlParameter(queryParams).setToken(token).build();
		HttpResponse httpResponse = rawClient.makeGetRequest(request);
		if (httpResponse.getStatusCode() == 200) {
			List<GetBinaryValue> value = JsonFactory.fromJson(httpResponse.getContent(),
					new TypeReference<List<GetBinaryValue>>() {
					});
			return new Response<>(value, httpResponse);
		} else if (httpResponse.getStatusCode() == 404) {
			return new Response<>(null, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<List<String>> getKVKeysOnly(String keyPrefix) {
		return getKVKeysOnly(keyPrefix, QueryParams.DEFAULT);
	}

	@Override
	public Response<List<String>> getKVKeysOnly(String keyPrefix, String separator, String token) {
		return getKVKeysOnly(keyPrefix, separator, token, QueryParams.DEFAULT);
	}

	@Override
	public Response<List<String>> getKVKeysOnly(String keyPrefix, QueryParams queryParams) {
		return getKVKeysOnly(keyPrefix, null, null, queryParams);
	}

	@Override
	public Response<List<String>> getKVKeysOnly(String keyPrefix, String separator, String token,
			QueryParams queryParams) {
		UrlParameters keysParam = new SingleUrlParameters("keys");
		UrlParameters separatorParam = separator != null ? new SingleUrlParameters("separator", separator) : null;
		UrlParameters tokenParam = token != null ? new SingleUrlParameters("token", token) : null;
		HttpResponse httpResponse = rawClient.makeGetRequest("/v1/kv/" + keyPrefix, keysParam, separatorParam,
				tokenParam, queryParams);
		if (httpResponse.getStatusCode() == 200) {
			List<String> value = JsonFactory.fromJson(httpResponse.getContent(),
					new TypeReference<List<String>>() {
					});
			return new Response<>(value, httpResponse);
		} else if (httpResponse.getStatusCode() == 404) {
			return new Response<>(null, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<Boolean> setKVValue(String key, String value) {
		return setKVValue(key, value, QueryParams.DEFAULT);
	}

	@Override
	public Response<Boolean> setKVValue(String key, String value, PutParams putParams) {
		return setKVValue(key, value, putParams, QueryParams.DEFAULT);
	}

	@Override
	public Response<Boolean> setKVValue(String key, String value, String token, PutParams putParams) {
		return setKVValue(key, value, token, putParams, QueryParams.DEFAULT);
	}

	@Override
	public Response<Boolean> setKVValue(String key, String value, QueryParams queryParams) {
		return setKVValue(key, value, null, null, queryParams);
	}

	@Override
	public Response<Boolean> setKVValue(String key, String value, PutParams putParams, QueryParams queryParams) {
		return setKVValue(key, value, null, putParams, queryParams);
	}

	@Override
	public Response<Boolean> setKVValue(String key, String value, String token, PutParams putParams,
			QueryParams queryParams) {
		UrlParameters tokenParam = token != null ? new SingleUrlParameters("token", token) : null;
		HttpResponse httpResponse = rawClient.makePutRequest("/v1/kv/" + key, value, putParams, tokenParam,
				queryParams);
		if (httpResponse.getStatusCode() == 200) {
			boolean result = JsonFactory.fromJson(httpResponse.getContent(), boolean.class);
			return new Response<>(result, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<Boolean> setKVBinaryValue(String key, byte[] value) {
		return setKVBinaryValue(key, value, QueryParams.DEFAULT);
	}

	@Override
	public Response<Boolean> setKVBinaryValue(String key, byte[] value, PutParams putParams) {
		return setKVBinaryValue(key, value, putParams, QueryParams.DEFAULT);
	}

	@Override
	public Response<Boolean> setKVBinaryValue(String key, byte[] value, String token, PutParams putParams) {
		return setKVBinaryValue(key, value, token, putParams, QueryParams.DEFAULT);
	}

	@Override
	public Response<Boolean> setKVBinaryValue(String key, byte[] value, QueryParams queryParams) {
		return setKVBinaryValue(key, value, null, null, queryParams);
	}

	@Override
	public Response<Boolean> setKVBinaryValue(String key, byte[] value, PutParams putParams, QueryParams queryParams) {
		return setKVBinaryValue(key, value, null, putParams, queryParams);
	}

	@Override
	public Response<Boolean> setKVBinaryValue(String key, byte[] value, String token, PutParams putParams,
			QueryParams queryParams) {
		Request request = Request.Builder.newBuilder().setEndpoint("/v1/kv/" + key).setToken(token)
				.addUrlParameter(queryParams).addUrlParameter(putParams).setBinaryContent(value).build();
		HttpResponse httpResponse = rawClient.makePutRequest(request);
		if (httpResponse.getStatusCode() == 200) {
			boolean result = JsonFactory.fromJson(httpResponse.getContent(), boolean.class);
			return new Response<>(result, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<Void> deleteKVValue(String key) {
		return deleteKVValue(key, QueryParams.DEFAULT);
	}

	@Override
	public Response<Void> deleteKVValue(String key, String token) {
		return deleteKVValue(key, token, QueryParams.DEFAULT);
	}

	@Override
	public Response<Void> deleteKVValue(String key, QueryParams queryParams) {
		return deleteKVValue(key, null, queryParams);
	}

	@Override
	public Response<Void> deleteKVValue(String key, String token, QueryParams queryParams) {
		Request request = Request.Builder.newBuilder().setEndpoint("/v1/kv/" + key).setToken(token)
				.addUrlParameter(queryParams).build();
		HttpResponse httpResponse = rawClient.makeDeleteRequest(request);
		if (httpResponse.getStatusCode() == 200) {
			return new Response<>(null, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<Void> deleteKVValues(String key) {
		return deleteKVValues(key, QueryParams.DEFAULT);
	}

	@Override
	public Response<Void> deleteKVValues(String key, String token) {
		return deleteKVValues(key, token, QueryParams.DEFAULT);
	}

	@Override
	public Response<Void> deleteKVValues(String key, QueryParams queryParams) {
		return deleteKVValues(key, null, queryParams);
	}

	@Override
	public Response<Void> deleteKVValues(String key, String token, QueryParams queryParams) {
		UrlParameters recurseParam = new SingleUrlParameters("recurse");
		Request request = Request.Builder.newBuilder().setEndpoint("/v1/kv/" + key).setToken(token)
				.addUrlParameter(recurseParam).addUrlParameter(queryParams).build();
		HttpResponse httpResponse = rawClient.makeDeleteRequest(request);
		if (httpResponse.getStatusCode() == 200) {
			return new Response<>(null, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}
}