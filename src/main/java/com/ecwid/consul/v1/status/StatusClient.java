package com.ecwid.consul.v1.status;

import com.ecwid.consul.v1.Response;

import java.util.List;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public interface StatusClient {
	Response<String> getStatusLeader();

	Response<List<String>> getStatusPeers();
}