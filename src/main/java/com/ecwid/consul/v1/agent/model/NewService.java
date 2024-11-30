package com.ecwid.consul.v1.agent.model;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import com.ecwid.consul.Utils;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 * @author Spencer Gibb (spencer@gibb.us)
 * @author Jon Huang
 */
public class NewService {
	public static class Check {
		@JsonProperty("CheckID")
		private String checkId;

		@JsonProperty("Name")
		private String name;

		@JsonProperty("Interval")
		private String interval;

		@JsonProperty("Notes")
		private String notes;

		@JsonProperty("DeregisterCriticalServiceAfter")
		private String deregisterCriticalServiceAfter;

		@JsonProperty("Args")
		private List<String> args;

		@JsonProperty("AliasNode")
		private String aliasNode;

		@JsonProperty("AliasService")
		private String aliasService;

		@JsonProperty("DockerContainerID")
		private String dockerContainerID;

		@JsonProperty("GRPC")
		private String grpc;

		@JsonProperty("GRPCUseTLS")
		private Boolean grpcUseTLS;

		@JsonProperty("H2PING")
		private String h2Ping;

		@JsonProperty("H2PingUseTLS")
		private Boolean h2PingUseTLS;

		@JsonProperty("HTTP")
		private String http;

		@JsonProperty("Method")
		private String method;

		@JsonProperty("Body")
		private String body;

		@JsonProperty("DisableRedirects")
		private Boolean disableRedirects;

		@JsonProperty("Header")
		private Map<String, List<String>> header;

		@JsonProperty("Timeout")
		private String timeout;

		@JsonProperty("OutputMaxSize")
		private Integer outputMaxSize;

		@JsonProperty("TLSServerName")
		private String tlsServerName;

		@JsonProperty("TLSSkipVerify")
		private Boolean tlsSkipVerify;

		@JsonProperty("TCP")
		private String tcp;

		@JsonProperty("TCPUseTLS")
		private Boolean tcpUseTLS;

		@JsonProperty("UDP")
		private String udp;

		@JsonProperty("OSService")
		private String osService;

		@JsonProperty("TTL")
		private String ttl;

		@JsonProperty("ServiceID")
		private String serviceId;

		@JsonProperty("Status")
		private CheckStatus status;

		@JsonProperty("SuccessBeforePassing")
		private Integer successBeforePassing;

		@JsonProperty("FailuresBeforeWarning")
		private Integer failuresBeforeWarning;

		@JsonProperty("FailuresBeforeCritical")
		private Integer failuresBeforeCritical;

		public String getCheckId() {
			return checkId;
		}

		public void setCheckId(String checkId) {
			this.checkId = checkId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getInterval() {
			return interval;
		}

		public void setInterval(String interval) {
			this.interval = interval;
		}

		public void setInterval(Duration interval) {
			this.interval = Utils.toConsulDuration(interval);
		}

		public String getNotes() {
			return notes;
		}

		public void setNotes(String notes) {
			this.notes = notes;
		}

		public String getDeregisterCriticalServiceAfter() {
			return deregisterCriticalServiceAfter;
		}

		public void setDeregisterCriticalServiceAfter(String deregisterCriticalServiceAfter) {
			this.deregisterCriticalServiceAfter = deregisterCriticalServiceAfter;
		}

		public List<String> getArgs() {
			return args;
		}

		public void setArgs(List<String> args) {
			this.args = args;
		}

		public String getAliasNode() {
			return aliasNode;
		}

		public void setAliasNode(String aliasNode) {
			this.aliasNode = aliasNode;
		}

		public String getAliasService() {
			return aliasService;
		}

		public void setAliasService(String aliasService) {
			this.aliasService = aliasService;
		}

		public String getDockerContainerID() {
			return dockerContainerID;
		}

		public void setDockerContainerID(String dockerContainerID) {
			this.dockerContainerID = dockerContainerID;
		}

		public String getGrpc() {
			return grpc;
		}

		public void setGrpc(String grpc) {
			this.grpc = grpc;
		}

		public Boolean getGrpcUseTLS() {
			return grpcUseTLS;
		}

		public void setGrpcUseTLS(Boolean grpcUseTLS) {
			this.grpcUseTLS = grpcUseTLS;
		}

		public String getH2Ping() {
			return h2Ping;
		}

		public void setH2Ping(String h2Ping) {
			this.h2Ping = h2Ping;
		}

		public Boolean getH2PingUseTLS() {
			return h2PingUseTLS;
		}

		public void setH2PingUseTLS(Boolean h2PingUseTLS) {
			this.h2PingUseTLS = h2PingUseTLS;
		}

		public String getHttp() {
			return http;
		}

		public void setHttp(String http) {
			this.http = http;
		}

		public String getMethod() {
			return method;
		}

		public void setMethod(String method) {
			this.method = method;
		}

		public String getBody() {
			return body;
		}

		public void setBody(String body) {
			this.body = body;
		}

		public Boolean getDisableRedirects() {
			return disableRedirects;
		}

		public void setDisableRedirects(Boolean disableRedirects) {
			this.disableRedirects = disableRedirects;
		}

		public Map<String, List<String>> getHeader() {
			return header;
		}

		public void setHeader(Map<String, List<String>> header) {
			this.header = header;
		}

		public String getTimeout() {
			return timeout;
		}

		public void setTimeout(String timeout) {
			this.timeout = timeout;
		}

		public Integer getOutputMaxSize() {
			return outputMaxSize;
		}

		public void setOutputMaxSize(Integer outputMaxSize) {
			this.outputMaxSize = outputMaxSize;
		}

		public String getTlsServerName() {
			return tlsServerName;
		}

		public void setTlsServerName(String tlsServerName) {
			this.tlsServerName = tlsServerName;
		}

		public Boolean getTlsSkipVerify() {
			return tlsSkipVerify;
		}

		public void setTlsSkipVerify(Boolean tlsSkipVerify) {
			this.tlsSkipVerify = tlsSkipVerify;
		}

		public String getTcp() {
			return tcp;
		}

		public void setTcp(String tcp) {
			this.tcp = tcp;
		}

		public Boolean getTcpUseTLS() {
			return tcpUseTLS;
		}

		public void setTcpUseTLS(Boolean tcpUseTLS) {
			this.tcpUseTLS = tcpUseTLS;
		}

		public String getUdp() {
			return udp;
		}

		public void setUdp(String udp) {
			this.udp = udp;
		}

		public String getOsService() {
			return osService;
		}

		public void setOsService(String osService) {
			this.osService = osService;
		}

		public String getTtl() {
			return ttl;
		}

		public void setTtl(String ttl) {
			this.ttl = ttl;
		}

		public String getServiceId() {
			return serviceId;
		}

		public void setServiceId(String serviceId) {
			this.serviceId = serviceId;
		}

		public CheckStatus getStatus() {
			return status;
		}

		public void setStatus(CheckStatus status) {
			this.status = status;
		}

		public Integer getSuccessBeforePassing() {
			return successBeforePassing;
		}

		public void setSuccessBeforePassing(Integer successBeforePassing) {
			this.successBeforePassing = successBeforePassing;
		}

		public Integer getFailuresBeforeWarning() {
			return failuresBeforeWarning;
		}

		public void setFailuresBeforeWarning(Integer failuresBeforeWarning) {
			this.failuresBeforeWarning = failuresBeforeWarning;
		}

		public Integer getFailuresBeforeCritical() {
			return failuresBeforeCritical;
		}

		public void setFailuresBeforeCritical(Integer failuresBeforeCritical) {
			this.failuresBeforeCritical = failuresBeforeCritical;
		}

		@Override
		public String toString() {
			return "Check [name=" + name + ", checkId=" + checkId + ", interval=" + interval + ", notes=" + notes
					+ ", deregisterCriticalServiceAfter=" + deregisterCriticalServiceAfter + ", args=" + args
					+ ", aliasNode=" + aliasNode + ", aliasService=" + aliasService + ", dockerContainerID="
					+ dockerContainerID + ", grpc=" + grpc + ", grpcUseTLS=" + grpcUseTLS + ", h2Ping=" + h2Ping
					+ ", h2PingUseTLS=" + h2PingUseTLS + ", http=" + http + ", method=" + method + ", body=" + body
					+ ", disableRedirects=" + disableRedirects + ", header=" + header + ", timeout=" + timeout
					+ ", outputMaxSize=" + outputMaxSize + ", tlsServerName=" + tlsServerName + ", tlsSkipVerify="
					+ tlsSkipVerify + ", tcp=" + tcp + ", tcpUseTLS=" + tcpUseTLS + ", udp=" + udp + ", osService="
					+ osService + ", ttl=" + ttl + ", serviceId=" + serviceId + ", status=" + status
					+ ", successBeforePassing=" + successBeforePassing + ", failuresBeforeWarning="
					+ failuresBeforeWarning + ", failuresBeforeCritical=" + failuresBeforeCritical + "]";
		}
	}

	@JsonProperty("Name")
	private String name;

	@JsonProperty("ID")
	private String id;

	@JsonProperty("Tags")
	private List<String> tags;

	@JsonProperty("Address")
	private String address;

	@JsonProperty("TaggedAddresses")
	private Map<String, String> taggedAddresses;

	@JsonProperty("Meta")
	private Map<String, String> meta;

	@JsonProperty("Port")
	private Integer port;

	@JsonProperty("Kind")
	private ServiceKind kind;

	@JsonProperty("Proxy")
	private Proxy proxy;

	@JsonProperty("Connect")
	private Connect connect;

	@JsonProperty("Check")
	private Check check;

	@JsonProperty("Checks")
	private List<Check> checks;

	@JsonProperty("EnableTagOverride")
	private Boolean enableTagOverride;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Map<String, String> getTaggedAddresses() {
		return taggedAddresses;
	}

	public void setTaggedAddresses(Map<String, String> taggedAddresses) {
		this.taggedAddresses = taggedAddresses;
	}

	public Map<String, String> getMeta() {
		return meta;
	}

	public void setMeta(Map<String, String> meta) {
		this.meta = meta;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public ServiceKind getKind() {
		return kind;
	}

	public void setKind(ServiceKind kind) {
		this.kind = kind;
	}

	public Proxy getProxy() {
		return proxy;
	}

	public void setProxy(Proxy proxy) {
		this.proxy = proxy;
	}

	public Connect getConnect() {
		return connect;
	}

	public void setConnect(Connect connect) {
		this.connect = connect;
	}

	public Check getCheck() {
		return check;
	}

	public void setCheck(Check check) {
		this.check = check;
	}

	public List<Check> getChecks() {
		return checks;
	}

	public void setChecks(List<Check> checks) {
		this.checks = checks;
	}

	public Boolean getEnableTagOverride() {
		return enableTagOverride;
	}

	public void setEnableTagOverride(Boolean enableTagOverride) {
		this.enableTagOverride = enableTagOverride;
	}

	@Override
	public String toString() {
		return "NewService [name=" + name + ", id=" + id + ", tags=" + tags + ", address=" + address
				+ ", taggedAddresses=" + taggedAddresses + ", meta=" + meta + ", port=" + port + ", check=" + check
				+ ", checks=" + checks + ", enableTagOverride=" + enableTagOverride + "]";
	}
}