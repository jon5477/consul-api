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

import com.ecwid.consul.Utils;
import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.kv.model.GetBinaryValue;
import com.ecwid.consul.v1.kv.model.GetValue;
import com.ecwid.consul.v1.kv.model.PutParams;

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
public interface KeyValueClient {
	Response<GetValue> getKVValue(String key);

	default Response<GetValue> getKVValue(String key, CharSequence token) {
		return getKVValue(key, Utils.charSequenceToArray(token));
	}

	Response<GetValue> getKVValue(String key, char[] token);

	Response<GetValue> getKVValue(String key, QueryParams queryParams);

	default Response<GetValue> getKVValue(String key, CharSequence token, QueryParams queryParams) {
		return getKVValue(key, Utils.charSequenceToArray(token), queryParams);
	}

	Response<GetValue> getKVValue(String key, char[] token, QueryParams queryParams);

	Response<GetBinaryValue> getKVBinaryValue(String key);

	default Response<GetBinaryValue> getKVBinaryValue(String key, CharSequence token) {
		return getKVBinaryValue(key, Utils.charSequenceToArray(token));
	}

	Response<GetBinaryValue> getKVBinaryValue(String key, char[] token);

	Response<GetBinaryValue> getKVBinaryValue(String key, QueryParams queryParams);

	default Response<GetBinaryValue> getKVBinaryValue(String key, CharSequence token, QueryParams queryParams) {
		return getKVBinaryValue(key, Utils.charSequenceToArray(token), queryParams);
	}

	Response<GetBinaryValue> getKVBinaryValue(String key, char[] token, QueryParams queryParams);

	Response<List<GetValue>> getKVValues(String keyPrefix);

	default Response<List<GetValue>> getKVValues(String keyPrefix, CharSequence token) {
		return getKVValues(keyPrefix, Utils.charSequenceToArray(token));
	}

	Response<List<GetValue>> getKVValues(String keyPrefix, char[] token);

	Response<List<GetValue>> getKVValues(String keyPrefix, QueryParams queryParams);

	default Response<List<GetValue>> getKVValues(String keyPrefix, CharSequence token, QueryParams queryParams) {
		return getKVValues(keyPrefix, Utils.charSequenceToArray(token), queryParams);
	}

	Response<List<GetValue>> getKVValues(String keyPrefix, char[] token, QueryParams queryParams);

	Response<List<GetBinaryValue>> getKVBinaryValues(String keyPrefix);

	default Response<List<GetBinaryValue>> getKVBinaryValues(String keyPrefix, CharSequence token) {
		return getKVBinaryValues(keyPrefix, Utils.charSequenceToArray(token));
	}

	Response<List<GetBinaryValue>> getKVBinaryValues(String keyPrefix, char[] token);

	Response<List<GetBinaryValue>> getKVBinaryValues(String keyPrefix, QueryParams queryParams);

	default Response<List<GetBinaryValue>> getKVBinaryValues(String keyPrefix, CharSequence token,
			QueryParams queryParams) {
		return getKVBinaryValues(keyPrefix, Utils.charSequenceToArray(token), queryParams);
	}

	Response<List<GetBinaryValue>> getKVBinaryValues(String keyPrefix, char[] token, QueryParams queryParams);

	Response<List<String>> getKVKeysOnly(String keyPrefix);

	default Response<List<String>> getKVKeysOnly(String keyPrefix, String separator, CharSequence token) {
		return getKVKeysOnly(keyPrefix, separator, Utils.charSequenceToArray(token));
	}

	Response<List<String>> getKVKeysOnly(String keyPrefix, String separator, char[] token);

	Response<List<String>> getKVKeysOnly(String keyPrefix, QueryParams queryParams);

	default Response<List<String>> getKVKeysOnly(String keyPrefix, String separator, CharSequence token,
			QueryParams queryParams) {
		return getKVKeysOnly(keyPrefix, separator, Utils.charSequenceToArray(token), queryParams);
	}

	Response<List<String>> getKVKeysOnly(String keyPrefix, String separator, char[] token, QueryParams queryParams);

	Response<Boolean> setKVValue(String key, String value);

	Response<Boolean> setKVValue(String key, String value, PutParams putParams);

	default Response<Boolean> setKVValue(String key, String value, CharSequence token, PutParams putParams) {
		return setKVValue(key, value, Utils.charSequenceToArray(token), putParams);
	}

	Response<Boolean> setKVValue(String key, String value, char[] token, PutParams putParams);

	Response<Boolean> setKVValue(String key, String value, QueryParams queryParams);

	Response<Boolean> setKVValue(String key, String value, PutParams putParams, QueryParams queryParams);

	default Response<Boolean> setKVValue(String key, String value, CharSequence token, PutParams putParams,
			QueryParams queryParams) {
		return setKVValue(key, value, Utils.charSequenceToArray(token), putParams, queryParams);
	}

	Response<Boolean> setKVValue(String key, String value, char[] token, PutParams putParams, QueryParams queryParams);

	Response<Boolean> setKVBinaryValue(String key, byte[] value);

	Response<Boolean> setKVBinaryValue(String key, byte[] value, PutParams putParams);

	default Response<Boolean> setKVBinaryValue(String key, byte[] value, CharSequence token, PutParams putParams) {
		return setKVBinaryValue(key, value, Utils.charSequenceToArray(token), putParams);
	}

	Response<Boolean> setKVBinaryValue(String key, byte[] value, char[] token, PutParams putParams);

	Response<Boolean> setKVBinaryValue(String key, byte[] value, QueryParams queryParams);

	Response<Boolean> setKVBinaryValue(String key, byte[] value, PutParams putParams, QueryParams queryParams);

	default Response<Boolean> setKVBinaryValue(String key, byte[] value, CharSequence token, PutParams putParams,
			QueryParams queryParams) {
		return setKVBinaryValue(key, value, Utils.charSequenceToArray(token), putParams, queryParams);
	}

	Response<Boolean> setKVBinaryValue(String key, byte[] value, char[] token, PutParams putParams,
			QueryParams queryParams);

	Response<Void> deleteKVValue(String key);

	default Response<Void> deleteKVValue(String key, CharSequence token) {
		return deleteKVValue(key, Utils.charSequenceToArray(token));
	}

	Response<Void> deleteKVValue(String key, char[] token);

	Response<Void> deleteKVValue(String key, QueryParams queryParams);

	default Response<Void> deleteKVValue(String key, CharSequence token, QueryParams queryParams) {
		return deleteKVValue(key, Utils.charSequenceToArray(token), queryParams);
	}

	Response<Void> deleteKVValue(String key, char[] token, QueryParams queryParams);

	Response<Void> deleteKVValues(String key);

	default Response<Void> deleteKVValues(String key, CharSequence token) {
		return deleteKVValues(key, Utils.charSequenceToArray(token));
	}

	Response<Void> deleteKVValues(String key, char[] token);

	Response<Void> deleteKVValues(String key, QueryParams queryParams);

	default Response<Void> deleteKVValues(String key, CharSequence token, QueryParams queryParams) {
		return deleteKVValues(key, Utils.charSequenceToArray(token), queryParams);
	}

	Response<Void> deleteKVValues(String key, char[] token, QueryParams queryParams);
}