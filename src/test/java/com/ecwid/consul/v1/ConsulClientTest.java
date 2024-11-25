package com.ecwid.consul.v1;

import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.util.Map;

import org.hamcrest.collection.IsMapContaining;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ecwid.consul.ConsulTestConstants;
import com.ecwid.consul.transport.TLSConfig;
import com.ecwid.consul.v1.agent.model.NewService;
import com.ecwid.consul.v1.agent.model.Service;
import com.pszymczyk.consul.ConsulProcess;
import com.pszymczyk.consul.ConsulStarterBuilder;
import com.pszymczyk.consul.infrastructure.Ports;

class ConsulClientTest {
	private ConsulProcess consul;
	private int randomHttpsPort = Ports.nextAvailable();

	@BeforeEach
	public void setup() {
		String path = "src/test/resources/ssl";
		String certRootPath = new File(path).getAbsolutePath().replace('\\', '/');
		String customConfiguration = "{\n" + "  \"datacenter\": \"dc-test\",\n" + "  \"log_level\": \"info\",\n"
				+ "  \"ports\": {\n" + "    \"https\": " + randomHttpsPort + "\n" + "  },\n" + "  \"tls\": {\n"
				+ "    \"https\": {\n" + "      \"ca_file\": \"" + certRootPath + "/ca.cert\",\n"
				+ "      \"key_file\": \"" + certRootPath + "/key.key\",\n" + "      \"cert_file\": \"" + certRootPath
				+ "/key.crt\"\n" + "    }\n" + "  }\n" + "}\n";
		consul = ConsulStarterBuilder.consulStarter().withConsulVersion(ConsulTestConstants.CONSUL_VERSION)
				.withCustomConfig(customConfiguration).withWaitTimeout(60).build().start();
	}

	@AfterEach
	public void cleanup() throws Exception {
		consul.close();
	}

	@Test
	void agentHttpTest() throws Exception {
		String host = "http://localhost";
		int port = consul.getHttpPort();
		ConsulClient consulClient = new ConsulClient(host, port);
		serviceRegisterTest(consulClient);
	}

	@Test
	void agentHttpsTest() throws Exception {
		String host = "https://localhost";
		// TODO make https random port in consul
		int httpsPort = randomHttpsPort;
		String path = "src/test/resources/ssl";
		String certRootPath = new File(path).getAbsolutePath().replace('\\', '/');
		String certificatePath = certRootPath + "/trustStore.jks";
		char[] certificatePassword = "change_me".toCharArray();
		String keyStorePath = certRootPath + "/keyStore.jks";
		char[] keyStorePassword = "change_me".toCharArray();
		TLSConfig tlsConfig = new TLSConfig(TLSConfig.KeyStoreInstanceType.JKS, certificatePath, certificatePassword,
				keyStorePath, keyStorePassword);
		ConsulClient consulClient = new ConsulClient(host, httpsPort, tlsConfig);
		serviceRegisterTest(consulClient);
	}

	private void serviceRegisterTest(ConsulClient consulClient) {
		NewService newService = new NewService();
		String serviceName = "abc";
		newService.setName(serviceName);
		consulClient.agentServiceRegister(newService);
		Response<Map<String, Service>> agentServicesResponse = consulClient.getAgentServices();
		Map<String, Service> services = agentServicesResponse.getValue();
		assertThat(services, IsMapContaining.hasKey(serviceName));
	}
}