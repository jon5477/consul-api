package com.ecwid.consul.v1.event;

import java.util.List;

import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.event.model.Event;
import com.ecwid.consul.v1.event.model.EventParams;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public interface EventClient {
	Response<Event> eventFire(String event, byte[] payload, EventParams eventParams, QueryParams queryParams);

	// -------------------------------------------------------------------------------

	Response<List<Event>> eventList(EventListRequest eventListRequest);
}