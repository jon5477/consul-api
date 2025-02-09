package com.ecwid.consul.v1;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.net.ssl.SSLContext;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import com.ecwid.consul.Utils;
import com.ecwid.consul.transport.TLSConfig;
import com.ecwid.consul.v1.acl.AclClient;
import com.ecwid.consul.v1.acl.AclConsulClient;
import com.ecwid.consul.v1.acl.AclTokensRequest;
import com.ecwid.consul.v1.acl.model.AclToken;
import com.ecwid.consul.v1.acl.model.NewAcl;
import com.ecwid.consul.v1.acl.model.UpdateAcl;
import com.ecwid.consul.v1.agent.AgentClient;
import com.ecwid.consul.v1.agent.AgentConsulClient;
import com.ecwid.consul.v1.agent.model.Check;
import com.ecwid.consul.v1.agent.model.Member;
import com.ecwid.consul.v1.agent.model.NewCheck;
import com.ecwid.consul.v1.agent.model.NewService;
import com.ecwid.consul.v1.agent.model.Self;
import com.ecwid.consul.v1.agent.model.Service;
import com.ecwid.consul.v1.catalog.CatalogClient;
import com.ecwid.consul.v1.catalog.CatalogConsulClient;
import com.ecwid.consul.v1.catalog.CatalogNodesRequest;
import com.ecwid.consul.v1.catalog.CatalogServiceRequest;
import com.ecwid.consul.v1.catalog.CatalogServicesRequest;
import com.ecwid.consul.v1.catalog.model.CatalogDeregistration;
import com.ecwid.consul.v1.catalog.model.CatalogNode;
import com.ecwid.consul.v1.catalog.model.CatalogRegistration;
import com.ecwid.consul.v1.catalog.model.CatalogService;
import com.ecwid.consul.v1.catalog.model.Node;
import com.ecwid.consul.v1.coordinate.CoordinateClient;
import com.ecwid.consul.v1.coordinate.CoordinateConsulClient;
import com.ecwid.consul.v1.coordinate.model.Datacenter;
import com.ecwid.consul.v1.event.EventClient;
import com.ecwid.consul.v1.event.EventConsulClient;
import com.ecwid.consul.v1.event.EventListRequest;
import com.ecwid.consul.v1.event.model.Event;
import com.ecwid.consul.v1.event.model.EventParams;
import com.ecwid.consul.v1.health.HealthChecksForServiceRequest;
import com.ecwid.consul.v1.health.HealthClient;
import com.ecwid.consul.v1.health.HealthConsulClient;
import com.ecwid.consul.v1.health.HealthServicesRequest;
import com.ecwid.consul.v1.health.model.HealthService;
import com.ecwid.consul.v1.kv.KeyValueClient;
import com.ecwid.consul.v1.kv.KeyValueConsulClient;
import com.ecwid.consul.v1.kv.model.GetBinaryValue;
import com.ecwid.consul.v1.kv.model.GetValue;
import com.ecwid.consul.v1.kv.model.PutParams;
import com.ecwid.consul.v1.query.QueryClient;
import com.ecwid.consul.v1.query.QueryConsulClient;
import com.ecwid.consul.v1.query.model.QueryExecution;
import com.ecwid.consul.v1.session.SessionClient;
import com.ecwid.consul.v1.session.SessionConsulClient;
import com.ecwid.consul.v1.session.model.NewSession;
import com.ecwid.consul.v1.session.model.Session;
import com.ecwid.consul.v1.status.StatusClient;
import com.ecwid.consul.v1.status.StatusConsulClient;

/**
 * Full consul-api client with all supported methods. If you like to use more
 * specific clients, please look at *Client classes (AclClient, AgentClient
 * etc.)
 * <p>
 * Implementation notes: Do not afraid of the class size :) There aren't any
 * 'smart' or specific methods - all methods in this class are just delegates
 * and auto-generated by IntelliJ IDEA
 *
 * @author Vasily Vasilkov (vgv@ecwid.com)
 * @author Jon Huang (jon5477)
 */
public class ConsulClient implements AclClient, AgentClient, CatalogClient, CoordinateClient, EventClient, HealthClient,
		KeyValueClient, QueryClient, SessionClient, StatusClient {
	private final ConsulRawClient rawClient;
	private final AclClient aclClient;
	private final AgentClient agentClient;
	private final CatalogClient catalogClient;
	private final CoordinateClient coordinateClient;
	private final EventClient eventClient;
	private final HealthClient healthClient;
	private final KeyValueClient keyValueClient;
	private final QueryClient queryClient;
	private final SessionClient sessionClient;
	private final StatusClient statusClient;

	/**
	 * Consul client will connect using the {@link ConsulRawClient}.
	 * 
	 * @param rawClient The {@link ConsulRawClient} instance to use for all API
	 *                  calls.
	 */
	public ConsulClient(ConsulRawClient rawClient) {
		this(rawClient, (char[]) null);
	}

	/**
	 * Consul client will connect using the {@link ConsulRawClient}.
	 * 
	 * @param rawClient The {@link ConsulRawClient} instance to use for all API
	 *                  calls.
	 * @param token     The Consul API ACL token for authentication
	 */
	public ConsulClient(ConsulRawClient rawClient, CharSequence token) {
		this(rawClient, Utils.charSequenceToArray(token));
	}

	/**
	 * Consul client will connect using the {@link ConsulRawClient}.
	 * 
	 * @param rawClient The {@link ConsulRawClient} instance to use for all API
	 *                  calls.
	 * @param token     The Consul API ACL token for authentication
	 */
	public ConsulClient(ConsulRawClient rawClient, char[] token) {
		this.rawClient = Objects.requireNonNull(rawClient, "raw client cannot be null");
		this.rawClient.setToken(token);
		this.aclClient = new AclConsulClient(rawClient);
		this.agentClient = new AgentConsulClient(rawClient);
		this.catalogClient = new CatalogConsulClient(rawClient);
		this.coordinateClient = new CoordinateConsulClient(rawClient);
		this.eventClient = new EventConsulClient(rawClient);
		this.healthClient = new HealthConsulClient(rawClient);
		this.keyValueClient = new KeyValueConsulClient(rawClient);
		this.queryClient = new QueryConsulClient(rawClient);
		this.sessionClient = new SessionConsulClient(rawClient);
		this.statusClient = new StatusConsulClient(rawClient);
	}

	/**
	 * Consul client will connect to local consul agent on 'http://localhost:8500'
	 */
	public ConsulClient() {
		this(new ConsulRawClient.Builder().build());
	}

	/**
	 * Consul client will connect to local consul agent on 'http://localhost:8500'
	 *
	 * @param tlsConfig TLS configuration
	 * @deprecated Use
	 *             {@link ConsulRawClient.Builder#setSSLContext(javax.net.ssl.SSLContext)}
	 *             and {@link #ConsulClient(ConsulRawClient)} or
	 *             {@link #ConsulClient(SSLContext)} for TLS configuration
	 */
	@Deprecated(since = "2.0.0", forRemoval = true)
	public ConsulClient(TLSConfig tlsConfig) {
		this(new ConsulRawClient.Builder().setTlsConfig(tlsConfig).build());
	}

	/**
	 * Consul client will connect to local consul agent on 'http://localhost:8500'
	 *
	 * @param sslCtx The {@link SSLContext} to use for TLS configuration.
	 */
	public ConsulClient(SSLContext sslCtx) {
		this(new ConsulRawClient.Builder().setSSLContext(sslCtx).build());
	}

	/**
	 * Connect to consul agent on specific address and default port (8500)
	 *
	 * @param agentHost Hostname or IP address of consul agent. You can specify
	 *                  scheme (HTTP/HTTPS) in address. If there is no scheme in
	 *                  address - client will use HTTP.
	 */
	public ConsulClient(String agentHost) {
		this(new ConsulRawClient.Builder().setHost(agentHost).build());
	}

	/**
	 * Connect to consul agent on specific address and default port (8500)
	 *
	 * @param agentHost Hostname or IP address of consul agent. You can specify
	 *                  scheme (HTTP/HTTPS) in address. If there is no scheme in
	 *                  address - client will use HTTP.
	 * @param tlsConfig TLS configuration
	 * @deprecated Use
	 *             {@link ConsulRawClient.Builder#setSSLContext(javax.net.ssl.SSLContext)}
	 *             and {@link #ConsulClient(ConsulRawClient)} or
	 *             {@link #ConsulClient(String, SSLContext)} for TLS configuration
	 */
	@Deprecated(since = "2.0.0", forRemoval = true)
	public ConsulClient(String agentHost, TLSConfig tlsConfig) {
		this(new ConsulRawClient.Builder().setHost(agentHost).setTlsConfig(tlsConfig).build());
	}

	/**
	 * Connect to consul agent on specific address and default port (8500)
	 *
	 * @param agentHost Hostname or IP address of consul agent. You can specify
	 *                  scheme (HTTP/HTTPS) in address. If there is no scheme in
	 *                  address - client will use HTTP.
	 * @param sslCtx    The {@link SSLContext} to use for TLS configuration.
	 */
	public ConsulClient(String agentHost, SSLContext sslCtx) {
		this(new ConsulRawClient.Builder().setHost(agentHost).setSSLContext(sslCtx).build());
	}

	/**
	 * Connect to consul agent on specific address and port
	 *
	 * @param agentHost Hostname or IP address of consul agent. You can specify
	 *                  scheme (HTTP/HTTPS) in address. If there is no scheme in
	 *                  address - client will use HTTP.
	 * @param agentPort Consul agent port
	 */
	public ConsulClient(String agentHost, int agentPort) {
		this(new ConsulRawClient.Builder(agentHost, agentPort).build());
	}

	/**
	 * Connect to consul agent on specific address and port
	 *
	 * @param agentHost Hostname or IP address of consul agent. You can specify
	 *                  scheme (HTTP/HTTPS) in address. If there is no scheme in
	 *                  address - client will use HTTP.
	 * @param agentPort Consul agent port
	 * @param tlsConfig TLS configuration
	 * @deprecated Use
	 *             {@link ConsulRawClient.Builder#setSSLContext(javax.net.ssl.SSLContext)}
	 *             and {@link #ConsulClient(ConsulRawClient)} or
	 *             {@link #ConsulClient(String, int, SSLContext)} for TLS
	 *             configuration
	 */
	@Deprecated(since = "2.0.0", forRemoval = true)
	public ConsulClient(String agentHost, int agentPort, TLSConfig tlsConfig) {
		this(new ConsulRawClient.Builder(agentHost, agentPort).setTlsConfig(tlsConfig).build());
	}

	/**
	 * Connect to consul agent on specific address and port
	 *
	 * @param agentHost Hostname or IP address of consul agent. You can specify
	 *                  scheme (HTTP/HTTPS) in address. If there is no scheme in
	 *                  address - client will use HTTP.
	 * @param agentPort Consul agent port
	 * @param sslCtx    The {@link SSLContext} to use for TLS configuration.
	 */
	public ConsulClient(String agentHost, int agentPort, SSLContext sslCtx) {
		this(new ConsulRawClient.Builder(agentHost, agentPort).setSSLContext(sslCtx).build());
	}

	/**
	 * Sets the Consul API ACL token to use on all API calls. This will be passed in
	 * X-Consul-Token HTTP header.
	 * 
	 * @param token The Consul API ACL token for authentication
	 */
	public void setToken(CharSequence token) {
		if (token != null) {
			this.setToken(Utils.charSequenceToArray(token));
		} else {
			this.setToken((char[]) null);
		}
	}

	/**
	 * Sets the Consul API ACL token to use on all API calls. This will be passed in
	 * X-Consul-Token HTTP header.
	 * 
	 * @param token The Consul API ACL token for authentication
	 */
	public void setToken(char[] token) {
		this.rawClient.setToken(token);
	}

	// -------------------------------------------------------------------------------------------
	// ACL

	@Override
	public Response<AclToken> aclCreate(NewAcl newAcl) {
		return aclClient.aclCreate(newAcl);
	}

	@Override
	public Response<AclToken> aclRead(String accessorId) {
		return aclClient.aclRead(accessorId);
	}

	@Override
	public Response<AclToken> aclRead(String accessorId, boolean expanded) {
		return aclClient.aclRead(accessorId, expanded);
	}

	@Override
	public Response<AclToken> aclReadSelf() {
		return aclClient.aclReadSelf();
	}

	@Override
	public Response<AclToken> aclUpdate(UpdateAcl updateAcl, String accessorId) {
		return aclClient.aclUpdate(updateAcl, accessorId);
	}

	@Override
	public Response<AclToken> aclClone(@NonNull String accessorId) {
		return aclClient.aclClone(accessorId);
	}

	@Override
	public Response<AclToken> aclClone(@NonNull String accessorId, @Nullable String description) {
		return aclClient.aclClone(accessorId, description);
	}

	@Override
	public Response<Boolean> aclDelete(String accessorId) {
		return aclClient.aclDelete(accessorId);
	}

	@Override
	public Response<List<AclToken>> aclList(AclTokensRequest request) {
		return aclClient.aclList(request);
	}

	// -------------------------------------------------------------------------------------------
	// Agent

	@Override
	public Response<Map<String, Check>> getAgentChecks() {
		return agentClient.getAgentChecks();
	}

	@Override
	public Response<Map<String, Service>> getAgentServices() {
		return agentClient.getAgentServices();
	}

	@Override
	public Response<List<Member>> getAgentMembers() {
		return agentClient.getAgentMembers();
	}

	@Override
	public Response<Self> getAgentSelf() {
		return agentClient.getAgentSelf();
	}

	@Override
	public Response<Void> agentSetMaintenance(boolean maintenanceEnabled) {
		return agentClient.agentSetMaintenance(maintenanceEnabled);
	}

	@Override
	public Response<Void> agentSetMaintenance(boolean maintenanceEnabled, String reason) {
		return agentClient.agentSetMaintenance(maintenanceEnabled, reason);
	}

	@Override
	public Response<Void> agentJoin(String address, boolean wan) {
		return agentClient.agentJoin(address, wan);
	}

	@Override
	public Response<Void> agentForceLeave(String node) {
		return agentClient.agentForceLeave(node);
	}

	@Override
	public Response<Void> agentCheckRegister(NewCheck newCheck) {
		return agentClient.agentCheckRegister(newCheck);
	}

	@Override
	public Response<Void> agentCheckDeregister(String checkId) {
		return agentClient.agentCheckDeregister(checkId);
	}

	@Override
	public Response<Void> agentCheckPass(String checkId) {
		return agentClient.agentCheckPass(checkId);
	}

	@Override
	public Response<Void> agentCheckPass(String checkId, String note) {
		return agentClient.agentCheckPass(checkId, note);
	}

	@Override
	public Response<Void> agentCheckWarn(String checkId) {
		return agentClient.agentCheckWarn(checkId);
	}

	@Override
	public Response<Void> agentCheckWarn(String checkId, String note) {
		return agentClient.agentCheckWarn(checkId, note);
	}

	@Override
	public Response<Void> agentCheckFail(String checkId) {
		return agentClient.agentCheckFail(checkId);
	}

	@Override
	public Response<Void> agentCheckFail(String checkId, String note) {
		return agentClient.agentCheckFail(checkId, note);
	}

	@Override
	public Response<Void> agentServiceRegister(NewService newService) {
		return agentClient.agentServiceRegister(newService);
	}

	@Override
	public Response<Void> agentServiceDeregister(String serviceId) {
		return agentClient.agentServiceDeregister(serviceId);
	}

	@Override
	public Response<Void> agentServiceSetMaintenance(String serviceId, boolean maintenanceEnabled) {
		return agentClient.agentServiceSetMaintenance(serviceId, maintenanceEnabled);
	}

	@Override
	public Response<Void> agentServiceSetMaintenance(String serviceId, boolean maintenanceEnabled, String reason) {
		return agentClient.agentServiceSetMaintenance(serviceId, maintenanceEnabled, reason);
	}

	@Override
	public Response<Void> agentReload() {
		return agentClient.agentReload();
	}

	// -------------------------------------------------------------------------------------------
	// Catalog

	@Override
	public Response<Void> catalogRegister(CatalogRegistration catalogRegistration) {
		return catalogClient.catalogRegister(catalogRegistration);
	}

	@Override
	public Response<Void> catalogDeregister(CatalogDeregistration catalogDeregistration) {
		return catalogClient.catalogDeregister(catalogDeregistration);
	}

	@Override
	public Response<List<String>> getCatalogDatacenters() {
		return catalogClient.getCatalogDatacenters();
	}

	@Override
	public Response<List<Node>> getCatalogNodes(CatalogNodesRequest catalogNodesRequest) {
		return catalogClient.getCatalogNodes(catalogNodesRequest);
	}

	@Override
	public Response<Map<String, List<String>>> getCatalogServices(CatalogServicesRequest catalogServicesRequest) {
		return catalogClient.getCatalogServices(catalogServicesRequest);
	}

	@Override
	public Response<List<CatalogService>> getCatalogService(String serviceName,
			CatalogServiceRequest catalogServiceRequest) {
		return catalogClient.getCatalogService(serviceName, catalogServiceRequest);
	}

	@Override
	public Response<CatalogNode> getCatalogNode(String nodeName, QueryParams queryParams) {
		return catalogClient.getCatalogNode(nodeName, queryParams);
	}

	// -------------------------------------------------------------------------------------------
	// Coordinates

	@Override
	public Response<List<Datacenter>> getDatacenters() {
		return coordinateClient.getDatacenters();
	}

	@Override
	public Response<List<com.ecwid.consul.v1.coordinate.model.Node>> getNodes(QueryParams queryParams) {
		return coordinateClient.getNodes(queryParams);
	}

	// -------------------------------------------------------------------------------------------
	// Event

	@Override
	public Response<Event> eventFire(String event, byte[] payload, EventParams eventParams, QueryParams queryParams) {
		return eventClient.eventFire(event, payload, eventParams, queryParams);
	}

	@Override
	public Response<List<Event>> eventList(EventListRequest eventListRequest) {
		return eventClient.eventList(eventListRequest);
	}

	// -------------------------------------------------------------------------------------------
	// Health

	@Override
	public Response<List<com.ecwid.consul.v1.health.model.Check>> getHealthChecksForNode(String nodeName,
			QueryParams queryParams) {
		return healthClient.getHealthChecksForNode(nodeName, queryParams);
	}

	@Override
	public Response<List<com.ecwid.consul.v1.health.model.Check>> getHealthChecksForService(String serviceName,
			HealthChecksForServiceRequest healthChecksForServiceRequest) {
		return healthClient.getHealthChecksForService(serviceName, healthChecksForServiceRequest);
	}

	@Override
	public Response<List<HealthService>> getHealthServices(String serviceName,
			HealthServicesRequest healthServicesRequest) {
		return healthClient.getHealthServices(serviceName, healthServicesRequest);
	}

	@Override
	public Response<List<com.ecwid.consul.v1.health.model.Check>> getHealthChecksState(QueryParams queryParams) {
		return healthClient.getHealthChecksState(queryParams);
	}

	@Override
	public Response<List<com.ecwid.consul.v1.health.model.Check>> getHealthChecksState(
			com.ecwid.consul.v1.health.model.Check.CheckStatus checkStatus, QueryParams queryParams) {
		return healthClient.getHealthChecksState(checkStatus, queryParams);
	}

	// -------------------------------------------------------------------------------------------
	// KV

	@Override
	public Response<GetValue> getKVValue(String key) {
		return keyValueClient.getKVValue(key);
	}

	@Override
	public Response<GetValue> getKVValue(String key, QueryParams queryParams) {
		return keyValueClient.getKVValue(key, queryParams);
	}

	@Override
	public Response<GetBinaryValue> getKVBinaryValue(String key) {
		return keyValueClient.getKVBinaryValue(key);
	}

	@Override
	public Response<GetBinaryValue> getKVBinaryValue(String key, QueryParams queryParams) {
		return keyValueClient.getKVBinaryValue(key, queryParams);
	}

	@Override
	public Response<List<GetValue>> getKVValues(String keyPrefix) {
		return keyValueClient.getKVValues(keyPrefix);
	}

	@Override
	public Response<List<GetValue>> getKVValues(String keyPrefix, QueryParams queryParams) {
		return keyValueClient.getKVValues(keyPrefix, queryParams);
	}

	@Override
	public Response<List<GetBinaryValue>> getKVBinaryValues(String keyPrefix) {
		return keyValueClient.getKVBinaryValues(keyPrefix);
	}

	@Override
	public Response<List<GetBinaryValue>> getKVBinaryValues(String keyPrefix, QueryParams queryParams) {
		return keyValueClient.getKVBinaryValues(keyPrefix, queryParams);
	}

	@Override
	public Response<List<String>> getKVKeysOnly(String keyPrefix) {
		return keyValueClient.getKVKeysOnly(keyPrefix);
	}

	@Override
	public Response<List<String>> getKVKeysOnly(String keyPrefix, String separator) {
		return keyValueClient.getKVKeysOnly(keyPrefix, separator);
	}

	@Override
	public Response<List<String>> getKVKeysOnly(String keyPrefix, QueryParams queryParams) {
		return keyValueClient.getKVKeysOnly(keyPrefix, queryParams);
	}

	@Override
	public Response<List<String>> getKVKeysOnly(String keyPrefix, String separator, QueryParams queryParams) {
		return keyValueClient.getKVKeysOnly(keyPrefix, separator, queryParams);
	}

	@Override
	public Response<Boolean> setKVValue(String key, String value) {
		return keyValueClient.setKVValue(key, value);
	}

	@Override
	public Response<Boolean> setKVValue(String key, String value, PutParams putParams) {
		return keyValueClient.setKVValue(key, value, putParams);
	}

	@Override
	public Response<Boolean> setKVValue(String key, String value, QueryParams queryParams) {
		return keyValueClient.setKVValue(key, value, queryParams);
	}

	@Override
	public Response<Boolean> setKVValue(String key, String value, PutParams putParams, QueryParams queryParams) {
		return keyValueClient.setKVValue(key, value, putParams, queryParams);
	}

	@Override
	public Response<Boolean> setKVBinaryValue(String key, byte[] value) {
		return keyValueClient.setKVBinaryValue(key, value);
	}

	@Override
	public Response<Boolean> setKVBinaryValue(String key, byte[] value, PutParams putParams) {
		return keyValueClient.setKVBinaryValue(key, value, putParams);
	}

	@Override
	public Response<Boolean> setKVBinaryValue(String key, byte[] value, QueryParams queryParams) {
		return keyValueClient.setKVBinaryValue(key, value, queryParams);
	}

	@Override
	public Response<Boolean> setKVBinaryValue(String key, byte[] value, PutParams putParams, QueryParams queryParams) {
		return keyValueClient.setKVBinaryValue(key, value, putParams, queryParams);
	}

	@Override
	public Response<Boolean> deleteKVValue(String key) {
		return keyValueClient.deleteKVValue(key);
	}

	@Override
	public Response<Boolean> deleteKVValue(String key, QueryParams queryParams) {
		return keyValueClient.deleteKVValue(key, queryParams);
	}

	@Override
	public Response<Boolean> deleteKVValues(String key) {
		return keyValueClient.deleteKVValues(key);
	}

	@Override
	public Response<Boolean> deleteKVValues(String key, QueryParams queryParams) {
		return keyValueClient.deleteKVValues(key, queryParams);
	}

	// -------------------------------------------------------------------------------------------
	// Prepared Query

	@Override
	public Response<QueryExecution> executePreparedQuery(String uuid, QueryParams queryParams) {
		return queryClient.executePreparedQuery(uuid, queryParams);
	}

	// -------------------------------------------------------------------------------------------
	// Session

	@Override
	public Response<String> sessionCreate(NewSession newSession, QueryParams queryParams) {
		return sessionClient.sessionCreate(newSession, queryParams);
	}

	@Override
	public Response<Void> sessionDestroy(String session, QueryParams queryParams) {
		return sessionClient.sessionDestroy(session, queryParams);
	}

	@Override
	public Response<Session> getSessionInfo(String session, QueryParams queryParams) {
		return sessionClient.getSessionInfo(session, queryParams);
	}

	@Override
	public Response<List<Session>> getSessionNode(String node, QueryParams queryParams) {
		return sessionClient.getSessionNode(node, queryParams);
	}

	@Override
	public Response<List<Session>> getSessionList(QueryParams queryParams) {
		return sessionClient.getSessionList(queryParams);
	}

	@Override
	public Response<Session> renewSession(String session, QueryParams queryParams) {
		return sessionClient.renewSession(session, queryParams);
	}

	// -------------------------------------------------------------------------------------------
	// Status

	@Override
	public Response<String> getStatusLeader() {
		return statusClient.getStatusLeader();
	}

	@Override
	public Response<List<String>> getStatusPeers() {
		return statusClient.getStatusPeers();
	}
}