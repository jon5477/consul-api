package com.ecwid.consul;

import java.util.Map;

/**
 * Mapping of keys to values to be attached to the query portion of a URL.
 * 
 * @author Jon Huang (jon5477)
 *
 */
public interface QueryParameters {
	Map<String, String> getQueryParameters();
}