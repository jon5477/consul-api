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
	/**
	 * Read WAN Coordinates
	 * 
	 * @return The WAN network coordinates for all Consul servers, organized by
	 *         datacenters.
	 * @see <a href=
	 *      "https://developer.hashicorp.com/consul/api-docs/coordinate#read-wan-coordinates">Read
	 *      WAN Coordinates</a>
	 */
	Response<List<Datacenter>> getDatacenters();

	/**
	 * Read LAN Coordinates for all nodes
	 * 
	 * @param queryParams The query parameters for this request.
	 * @return The LAN network coordinates for all nodes in a given datacenter.
	 * @see <a href=
	 *      "https://developer.hashicorp.com/consul/api-docs/coordinate#read-lan-coordinates-for-all-nodes">Read
	 *      LAN Coordinates for all nodes</a>
	 */
	Response<List<Node>> getNodes(QueryParams queryParams);

	/**
	 * Read LAN Coordinates for a node
	 * 
	 * @param nodeName    The name of the node to return LAN network coordinates
	 *                    for.
	 * @param queryParams The query parameters for this request.
	 * @return The LAN network coordinates for the given node.
	 * @see <a href=
	 *      "https://developer.hashicorp.com/consul/api-docs/coordinate#read-lan-coordinates-for-a-node">Read
	 *      LAN Coordinates for a node</a>
	 */
	Response<List<Node>> getNode(String nodeName, QueryParams queryParams);

	/**
	 * Update LAN Coordinates for a node
	 * 
	 * @param node        The node payload to update.
	 * @param queryParams The query parameters for this request.
	 * @return The update response.
	 * @see <a href=
	 *      "https://developer.hashicorp.com/consul/api-docs/coordinate#update-lan-coordinates-for-a-node">Update
	 *      LAN Coordinates for a node</a>
	 */
	Response<Void> updateNode(Node node, QueryParams queryParams);
}