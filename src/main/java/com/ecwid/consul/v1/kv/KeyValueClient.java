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

	Response<GetValue> getKVValue(String key, QueryParams queryParams);

	Response<GetBinaryValue> getKVBinaryValue(String key);

	Response<GetBinaryValue> getKVBinaryValue(String key, QueryParams queryParams);

	Response<List<GetValue>> getKVValues(String keyPrefix);

	Response<List<GetValue>> getKVValues(String keyPrefix, QueryParams queryParams);

	Response<List<GetBinaryValue>> getKVBinaryValues(String keyPrefix);

	Response<List<GetBinaryValue>> getKVBinaryValues(String keyPrefix, QueryParams queryParams);

	Response<List<String>> getKVKeysOnly(String keyPrefix);

	Response<List<String>> getKVKeysOnly(String keyPrefix, String separator);

	Response<List<String>> getKVKeysOnly(String keyPrefix, QueryParams queryParams);

	Response<List<String>> getKVKeysOnly(String keyPrefix, String separator, QueryParams queryParams);

	Response<Boolean> setKVValue(String key, String value);

	Response<Boolean> setKVValue(String key, String value, PutParams putParams);

	Response<Boolean> setKVValue(String key, String value, QueryParams queryParams);

	Response<Boolean> setKVValue(String key, String value, PutParams putParams, QueryParams queryParams);

	Response<Boolean> setKVBinaryValue(String key, byte[] value);

	Response<Boolean> setKVBinaryValue(String key, byte[] value, PutParams putParams);

	Response<Boolean> setKVBinaryValue(String key, byte[] value, QueryParams queryParams);

	Response<Boolean> setKVBinaryValue(String key, byte[] value, PutParams putParams, QueryParams queryParams);

	Response<Boolean> deleteKVValue(String key);

	Response<Boolean> deleteKVValue(String key, QueryParams queryParams);

	Response<Boolean> deleteKVValues(String key);

	Response<Boolean> deleteKVValues(String key, QueryParams queryParams);
}