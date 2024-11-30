package com.ecwid.consul.v1.session;

import java.util.List;

import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.session.model.NewSession;
import com.ecwid.consul.v1.session.model.Session;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public interface SessionClient {
	Response<String> sessionCreate(NewSession newSession, QueryParams queryParams);

	Response<Void> sessionDestroy(String session, QueryParams queryParams);

	Response<Session> getSessionInfo(String session, QueryParams queryParams);

	Response<List<Session>> getSessionNode(String node, QueryParams queryParams);

	Response<List<Session>> getSessionList(QueryParams queryParams);

	Response<Session> renewSession(String session, QueryParams queryParams);
}