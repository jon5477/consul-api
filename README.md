Hashicorp Consul API Java Client
================================
This is a Java client for consuming from the Hashicorp Consul API (http://consul.io)
Supports all [API](https://developer.hashicorp.com/consul/api-docs) endpoints, all consistency modes and parameters (tags, datacenters etc.)

For additional information such as how to use this client, please refer to the [Wiki](https://github.com/jon5477/consul-api/wiki) page.

If there is a need for changes or updates to be made, please create an issue. If you find this project useful, please consider sponsoring me so I have the necessary funding to continue providing updates 🙏

Fork Explanation:
-----------------
The [original project](https://github.com/Ecwid/consul-api) has not received any updates since 2021-2022 and can be considered abandoned. I believe this is due to Lightspeed's acquisition of Ecwid which you can read about [here](https://support.ecwid.com/hc/en-us/articles/5582039267996-Ecwid-is-now-Lightspeed), [here](https://www.lightspeedhq.com/news/lightspeed-announces-closing-of-ecwid-acquisition/) and [here](https://github.com/Ecwid/new-job). It is possible the original maintainer no longer works at Ecwid/Lightspeed. Due to the lack of updates and information regarding the original project, a fork has been made as a continuation of the original project.

Library Dependencies:
---------------------
- SLF4J (2.x)
- Jackson Databind (for JSON processing)

Requirements:
-------------
- Java 11 (for the Java HTTP client)

Forked Changes:
---------------
The Consul APIs have changed drastically from the [original codebase](https://github.com/Ecwid/consul-api). Please review the following changes before migrating to this API:
- Java Platform Module System
  - This project has been modularized, please let us know if there are any issues.
- Vulnerable maven dependencies (with open CVEs)
  - Apache HTTP Client 4.x
  - GSON
- Replaced Apache HTTP Client with built-in [HttpClient](https://openjdk.org/groups/net/httpclient/intro.html) - available since Java 11
  - Blocking queries are now interruptible with `Thread.interrupt()`
- Various Consul API deprecations and removals
  - Reworked ACL endpoint (legacy ACL was removed since Consul v1.11.x)
  - node-meta query specifier on [catalog](https://developer.hashicorp.com/consul/api-docs/catalog#node-meta) and [health](https://developer.hashicorp.com/consul/api-docs/health#node-meta-1) - deprecated since Consul v1.9.x
  - tag query specifier on [health](https://developer.hashicorp.com/consul/api-docs/health#tag) - deprecated since Consul v1.9.x
  - token query parameter - [deprecated](https://developer.hashicorp.com/consul/api-docs/v1.13.x/api-structure) and warned against since Consul v1.13.x
- Various secure coding standards (handle sensitive Java text data as `char[]`)
  - `CharSequence` is provided as a compatibility-layer for legacy code, but it is highly recommended to use `char[]`
  - Allow specifying your own `SSLContext` and `SSLParameters` implementations (important for secure TLS in Java) - `TlsConfig` has been kept (but deprecated), it is advised to migrate off using `TlsConfig` due to its built-in insecurities
- API has been tested against Consul 1.18.1 ([ecwid](https://github.com/Ecwid/consul-api/blob/232550b44e122f42446876835946baf2de333f53/src/test/java/com/ecwid/consul/ConsulTestConstants.java#L5) tests against Consul 1.6.0)
