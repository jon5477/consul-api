package com.ecwid.consul;

import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public class Utils {
	private Utils() {
	}

	public static char[] charSequenceToArray(CharSequence seq) {
		int length = seq.length();
		char[] arr = new char[length];
		for (int i = 0; i < length; i++) {
			arr[i] = seq.charAt(i);
		}
		return arr;
	}

	public static String encodeValue(String value) {
		return URLEncoder.encode(value, StandardCharsets.UTF_8);
	}

	public static String encodeUrl(String str) {
		try {
			URL url = new URL(str);
			URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(),
					url.getQuery(), url.getRef());
			return uri.toASCIIString();
		} catch (Exception e) {
			throw new RuntimeException("Can't encode url", e);
		}
	}

	public static String generateUrl(String baseUrl, UrlParameters... params) {
		return generateUrl(baseUrl, Arrays.asList(params));
	}

	public static String generateUrl(String baseUrl, List<UrlParameters> params) {
		if (params == null) {
			return baseUrl;
		}

		List<String> allParams = new ArrayList<>();
		for (UrlParameters item : params) {
			if (item != null) {
				allParams.addAll(item.toUrlParameters());
			}
		}

		// construct the whole url
		StringBuilder result = new StringBuilder(baseUrl);

		Iterator<String> paramsIterator = allParams.iterator();
		if (paramsIterator.hasNext()) {
			result.append("?").append(paramsIterator.next());
			while (paramsIterator.hasNext()) {
				result.append("&").append(paramsIterator.next());
			}
		}
		return result.toString();
	}

	public static String toSecondsString(long waitTime) {
		return String.valueOf(waitTime) + "s";
	}

	public static String assembleAgentAddress(String host, int port, String path) {
		String agentPath = "";
		if (path != null && !path.trim().isEmpty()) {
			agentPath = "/" + path;
		}
		return String.format("%s:%d%s", host, port, agentPath);
	}
}