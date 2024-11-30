/*********************************************************************
* Copyright (c) 2024 Jon Huang
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

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