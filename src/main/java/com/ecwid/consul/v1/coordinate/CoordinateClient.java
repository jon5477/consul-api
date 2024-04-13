package com.ecwid.consul.v1.coordinate;

import java.util.List;

import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.coordinate.model.Datacenter;
import com.ecwid.consul.v1.coordinate.model.Node;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public interface CoordinateClient {
	Response<List<Datacenter>> getDatacenters();

	Response<List<Node>> getNodes(QueryParams queryParams);
}