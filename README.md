Hashicorp Consul API Java Client
================================
This is a Java client for consuming from the Hashicorp Consul API (http://consul.io)
Supports all [API](https://developer.hashicorp.com/consul/api-docs) endpoints, all consistency modes and parameters (tags, datacenters etc.)

For additional information such as how to use this client, please refer to the [Wiki](https://github.com/jon5477/consul-api/wiki) page.

This library is built with the following dependencies:
- SLF4J
- Jackson Databind
- Apache HTTP Client 5.x

At a minimum, Java 11 is required although Java 17 is highly recommended.

This project has been forked from Ecwid/consul-api and highly modified to address the following issues:
- Vulnerable dependencies (CVEs)
- Consul API deprecations and removals
- Various secure coding standards
- API has been tested against Consul 1.18.1

If there is a need for updates to be made against the API, please create an issue.
