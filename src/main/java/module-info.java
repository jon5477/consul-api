module com.ecwid.consul.api {
	exports com.ecwid.consul;
	exports com.ecwid.consul.json;
	exports com.ecwid.consul.transport;
	exports com.ecwid.consul.v1;
	exports com.ecwid.consul.v1.acl;
	exports com.ecwid.consul.v1.acl.model;
	exports com.ecwid.consul.v1.agent;
	exports com.ecwid.consul.v1.agent.model;
	exports com.ecwid.consul.v1.catalog;
	exports com.ecwid.consul.v1.catalog.model;
	exports com.ecwid.consul.v1.coordinate;
	exports com.ecwid.consul.v1.coordinate.model;
	exports com.ecwid.consul.v1.event;
	exports com.ecwid.consul.v1.event.model;
	exports com.ecwid.consul.v1.health;
	exports com.ecwid.consul.v1.health.model;
	exports com.ecwid.consul.v1.kv;
	exports com.ecwid.consul.v1.kv.model;
	exports com.ecwid.consul.v1.query;
	exports com.ecwid.consul.v1.query.model;
	exports com.ecwid.consul.v1.session;
	exports com.ecwid.consul.v1.session.model;
	exports com.ecwid.consul.v1.status;

	opens com.ecwid.consul;
	opens com.ecwid.consul.json;
	opens com.ecwid.consul.transport;
	opens com.ecwid.consul.v1;
	opens com.ecwid.consul.v1.acl;
	opens com.ecwid.consul.v1.acl.model;
	opens com.ecwid.consul.v1.agent;
	opens com.ecwid.consul.v1.agent.model;
	opens com.ecwid.consul.v1.catalog;
	opens com.ecwid.consul.v1.catalog.model;
	opens com.ecwid.consul.v1.coordinate;
	opens com.ecwid.consul.v1.coordinate.model;
	opens com.ecwid.consul.v1.event;
	opens com.ecwid.consul.v1.event.model;
	opens com.ecwid.consul.v1.health;
	opens com.ecwid.consul.v1.health.model;
	opens com.ecwid.consul.v1.kv;
	opens com.ecwid.consul.v1.kv.model;
	opens com.ecwid.consul.v1.query;
	opens com.ecwid.consul.v1.query.model;
	opens com.ecwid.consul.v1.session;
	opens com.ecwid.consul.v1.session.model;
	opens com.ecwid.consul.v1.status;

	requires java.net.http;

	requires com.fasterxml.jackson.annotation;
	requires com.fasterxml.jackson.core;
	requires transitive com.fasterxml.jackson.databind;
//	requires transitive org.apache.httpcomponents.client5.httpclient5;
//	requires org.apache.httpcomponents.core5.httpcore5;
	requires org.checkerframework.checker.qual;
	requires org.slf4j;
}