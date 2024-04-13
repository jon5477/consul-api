package com.ecwid.consul.v1.status;

import java.util.List;

import com.ecwid.consul.v1.Response;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public interface StatusClient {
	Response<String> getStatusLeader();

	Response<List<String>> getStatusPeers();
}