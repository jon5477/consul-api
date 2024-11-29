package com.ecwid.consul.v1.event;

import java.util.List;
import java.util.Objects;

import org.checkerframework.checker.nullness.qual.NonNull;

import com.ecwid.consul.json.JsonUtil;
import com.ecwid.consul.transport.ConsulHttpResponse;
import com.ecwid.consul.v1.ConsulRawClient;
import com.ecwid.consul.v1.OperationException;
import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.event.model.Event;
import com.ecwid.consul.v1.event.model.EventParams;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public final class EventConsulClient implements EventClient {
	private static final TypeReference<List<Event>> EVENT_LIST_TYPE_REF = new TypeReference<List<Event>>() {
	};
	private final ConsulRawClient rawClient;

	public EventConsulClient(@NonNull ConsulRawClient rawClient) {
		this.rawClient = Objects.requireNonNull(rawClient, "consul raw client cannot be null");
	}

	@Override
	public Response<Event> eventFire(String event, byte[] payload, EventParams eventParams, QueryParams queryParams) {
		ConsulHttpResponse httpResponse = rawClient.makePutRequest("/v1/event/fire/" + event, payload, eventParams,
				queryParams);
		if (httpResponse.getStatusCode() == 200) {
			Event value = JsonUtil.toPOJO(httpResponse.getContent(), Event.class);
			return new Response<>(value, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<List<Event>> eventList(EventListRequest eventListRequest) {
		ConsulHttpResponse httpResponse = rawClient.makeGetRequest("/v1/event/list", eventListRequest);
		if (httpResponse.getStatusCode() == 200) {
			List<Event> value = JsonUtil.toPOJO(httpResponse.getContent(), EVENT_LIST_TYPE_REF);
			return new Response<>(value, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}
}
