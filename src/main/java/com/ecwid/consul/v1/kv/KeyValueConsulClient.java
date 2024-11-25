/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.ecwid.consul.v1.kv;

import java.util.List;
import java.util.Objects;

import org.checkerframework.checker.nullness.qual.NonNull;

import com.ecwid.consul.ConsulException;
import com.ecwid.consul.QueryParameters;
import com.ecwid.consul.SingleQueryParameters;
import com.ecwid.consul.json.JsonFactory;
import com.ecwid.consul.transport.HttpResponse;
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
 * Includes the following modifications:
 * <ul>
 * <li>Upgrade to latest Consul KV API changes (v1.19.3)</li>
 * <li>Add support for {@link CharSequence} or {@code char[]} tokens.</li>
 * </ul>
 * 
 * @author Vasily Vasilkov (vgv@ecwid.com)
 * @author Jon Huang (jon5477)
 *
 */
public final class KeyValueConsulClient implements KeyValueClient {
	private static final TypeReference<List<GetBinaryValue>> BINARY_VALUE_LIST_TYPE_REF = new TypeReference<List<GetBinaryValue>>() {
	};
	private static final TypeReference<List<GetValue>> VALUE_LIST_TYPE_REF = new TypeReference<List<GetValue>>() {
	};
	private static final TypeReference<List<String>> STRING_LIST_TYPE_REF = new TypeReference<List<String>>() {
	};
	private static final String API_KV_PREFIX = "/v1/kv/";
	private static final SingleQueryParameters RECURSE_QUERY_PARAM = new SingleQueryParameters("recurse");
	private final ConsulRawClient rawClient;

	public KeyValueConsulClient(@NonNull ConsulRawClient rawClient) {
		this.rawClient = Objects.requireNonNull(rawClient, "consul raw client cannot be null");
	}

	@Override
	public Response<GetValue> getKVValue(String key) {
		return getKVValue(key, QueryParams.DEFAULT);
	}

	@Override
	public Response<GetValue> getKVValue(String key, char[] token) {
		return getKVValue(key, token, QueryParams.DEFAULT);
	}

	@Override
	public Response<GetValue> getKVValue(String key, QueryParams queryParams) {
		return getKVValue(key, (char[]) null, queryParams);
	}

	@Override
	public Response<GetValue> getKVValue(String key, char[] token, QueryParams queryParams) {
		Request request = Request.Builder.newBuilder().setEndpoint(API_KV_PREFIX + key).setToken(token)
				.addQueryParameters(queryParams).build();
		HttpResponse httpResponse = rawClient.makeGetRequest(request);
		if (httpResponse.getStatusCode() == 200) {
			List<GetValue> value = JsonFactory.toPOJO(httpResponse.getContent(), VALUE_LIST_TYPE_REF);
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
	public Response<GetBinaryValue> getKVBinaryValue(String key, char[] token) {
		return getKVBinaryValue(key, token, QueryParams.DEFAULT);
	}

	@Override
	public Response<GetBinaryValue> getKVBinaryValue(String key, QueryParams queryParams) {
		return getKVBinaryValue(key, (char[]) null, queryParams);
	}

	@Override
	public Response<GetBinaryValue> getKVBinaryValue(String key, char[] token, QueryParams queryParams) {
		Request request = Request.Builder.newBuilder().setEndpoint(API_KV_PREFIX + key).setToken(token)
				.addQueryParameters(queryParams).build();
		HttpResponse httpResponse = rawClient.makeGetRequest(request);
		if (httpResponse.getStatusCode() == 200) {
			List<GetBinaryValue> value = JsonFactory.toPOJO(httpResponse.getContent(), BINARY_VALUE_LIST_TYPE_REF);
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
	public Response<List<GetValue>> getKVValues(String keyPrefix, char[] token) {
		return getKVValues(keyPrefix, token, QueryParams.DEFAULT);
	}

	@Override
	public Response<List<GetValue>> getKVValues(String keyPrefix, QueryParams queryParams) {
		return getKVValues(keyPrefix, (char[]) null, queryParams);
	}

	@Override
	public Response<List<GetValue>> getKVValues(String keyPrefix, char[] token, QueryParams queryParams) {
		Request request = Request.Builder.newBuilder().setEndpoint(API_KV_PREFIX + keyPrefix).setToken(token)
				.addQueryParameters(RECURSE_QUERY_PARAM, queryParams).build();
		HttpResponse httpResponse = rawClient.makeGetRequest(request);
		if (httpResponse.getStatusCode() == 200) {
			List<GetValue> value = JsonFactory.toPOJO(httpResponse.getContent(), VALUE_LIST_TYPE_REF);
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
	public Response<List<GetBinaryValue>> getKVBinaryValues(String keyPrefix, char[] token) {
		return getKVBinaryValues(keyPrefix, token, QueryParams.DEFAULT);
	}

	@Override
	public Response<List<GetBinaryValue>> getKVBinaryValues(String keyPrefix, QueryParams queryParams) {
		return getKVBinaryValues(keyPrefix, (char[]) null, queryParams);
	}

	@Override
	public Response<List<GetBinaryValue>> getKVBinaryValues(String keyPrefix, char[] token, QueryParams queryParams) {
		Request request = Request.Builder.newBuilder().setEndpoint(API_KV_PREFIX + keyPrefix).setToken(token)
				.addQueryParameters(RECURSE_QUERY_PARAM, queryParams).build();
		HttpResponse httpResponse = rawClient.makeGetRequest(request);
		if (httpResponse.getStatusCode() == 200) {
			List<GetBinaryValue> value = JsonFactory.toPOJO(httpResponse.getContent(), BINARY_VALUE_LIST_TYPE_REF);
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
	public Response<List<String>> getKVKeysOnly(String keyPrefix, String separator, char[] token) {
		return getKVKeysOnly(keyPrefix, separator, token, QueryParams.DEFAULT);
	}

	@Override
	public Response<List<String>> getKVKeysOnly(String keyPrefix, QueryParams queryParams) {
		return getKVKeysOnly(keyPrefix, null, (char[]) null, queryParams);
	}

	@Override
	public Response<List<String>> getKVKeysOnly(String keyPrefix, String separator, char[] token,
			QueryParams queryParams) {
		QueryParameters keysParam = new SingleQueryParameters("keys");
		QueryParameters separatorParam = separator != null ? new SingleQueryParameters("separator", separator) : null;
		Request request = Request.Builder.newBuilder().setEndpoint(API_KV_PREFIX + keyPrefix).setToken(token)
				.addQueryParameters(keysParam, separatorParam, queryParams).build();
		HttpResponse httpResponse = rawClient.makeGetRequest(request);
		if (httpResponse.getStatusCode() == 200) {
			List<String> value = JsonFactory.toPOJO(httpResponse.getContent(), STRING_LIST_TYPE_REF);
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
	public Response<Boolean> setKVValue(String key, String value, char[] token, PutParams putParams) {
		return setKVValue(key, value, token, putParams, QueryParams.DEFAULT);
	}

	@Override
	public Response<Boolean> setKVValue(String key, String value, QueryParams queryParams) {
		return setKVValue(key, value, (char[]) null, null, queryParams);
	}

	@Override
	public Response<Boolean> setKVValue(String key, String value, PutParams putParams, QueryParams queryParams) {
		return setKVValue(key, value, (char[]) null, putParams, queryParams);
	}

	@Override
	public Response<Boolean> setKVValue(String key, String value, char[] token, PutParams putParams,
			QueryParams queryParams) {
		Request request = Request.Builder.newBuilder().setEndpoint(API_KV_PREFIX + key).setToken(token)
				.addQueryParameters(queryParams, putParams).build();
		HttpResponse httpResponse = rawClient.makePutRequest(request);
		if (httpResponse.getStatusCode() == 200) {
			boolean result = JsonFactory.toPOJO(httpResponse.getContent(), boolean.class);
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
	public Response<Boolean> setKVBinaryValue(String key, byte[] value, char[] token, PutParams putParams) {
		return setKVBinaryValue(key, value, token, putParams, QueryParams.DEFAULT);
	}

	@Override
	public Response<Boolean> setKVBinaryValue(String key, byte[] value, QueryParams queryParams) {
		return setKVBinaryValue(key, value, (char[]) null, null, queryParams);
	}

	@Override
	public Response<Boolean> setKVBinaryValue(String key, byte[] value, PutParams putParams, QueryParams queryParams) {
		return setKVBinaryValue(key, value, (char[]) null, putParams, queryParams);
	}

	@Override
	public Response<Boolean> setKVBinaryValue(String key, byte[] value, char[] token, PutParams putParams,
			QueryParams queryParams) {
		Request request = Request.Builder.newBuilder().setEndpoint(API_KV_PREFIX + key).setToken(token)
				.addQueryParameters(queryParams, putParams).setContent(value).build();
		HttpResponse httpResponse = rawClient.makePutRequest(request);
		if (httpResponse.getStatusCode() == 200) {
			boolean result = JsonFactory.toPOJO(httpResponse.getContent(), boolean.class);
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
	public Response<Void> deleteKVValue(String key, char[] token) {
		return deleteKVValue(key, token, QueryParams.DEFAULT);
	}

	@Override
	public Response<Void> deleteKVValue(String key, QueryParams queryParams) {
		return deleteKVValue(key, (char[]) null, queryParams);
	}

	@Override
	public Response<Void> deleteKVValue(String key, char[] token, QueryParams queryParams) {
		Request request = Request.Builder.newBuilder().setEndpoint(API_KV_PREFIX + key).setToken(token)
				.addQueryParameters(queryParams).build();
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
	public Response<Void> deleteKVValues(String key, char[] token) {
		return deleteKVValues(key, token, QueryParams.DEFAULT);
	}

	@Override
	public Response<Void> deleteKVValues(String key, QueryParams queryParams) {
		return deleteKVValues(key, (char[]) null, queryParams);
	}

	@Override
	public Response<Void> deleteKVValues(String key, char[] token, QueryParams queryParams) {
		Request request = Request.Builder.newBuilder().setEndpoint(API_KV_PREFIX + key).setToken(token)
				.addQueryParameters(RECURSE_QUERY_PARAM, queryParams).build();
		HttpResponse httpResponse = rawClient.makeDeleteRequest(request);
		if (httpResponse.getStatusCode() == 200) {
			return new Response<>(null, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}
}