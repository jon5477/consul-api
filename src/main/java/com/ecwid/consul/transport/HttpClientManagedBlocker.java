package com.ecwid.consul.transport;

import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinPool.ManagedBlocker;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpUriRequest;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class HttpClientManagedBlocker implements ManagedBlocker, Callable<HttpResponse> {
	private final HttpClient client;
	private final HttpUriRequest request;
	private final AtomicReference<HttpResponse> response = new AtomicReference<>();
	private final AtomicReference<Throwable> error = new AtomicReference<>();
	private final AtomicBoolean interrupted = new AtomicBoolean();

	public HttpClientManagedBlocker(@NonNull HttpClient client, @NonNull HttpUriRequest request) {
		this.client = client;
		this.request = request;
	}

	public final HttpResponse getResponse() {
		return response.get();
	}

	public final Throwable getError() {
		return error.get();
	}

	public final boolean isInterrupted() {
		return interrupted.get();
	}

	@Override
	public HttpResponse call() throws Exception {
		ForkJoinPool.managedBlock(this);
	}

	@Override
	public final boolean block() throws InterruptedException {
		try {
			if (Thread.currentThread().isInterrupted()) {
				interrupted.set(true);
			} else {
				response.set(client.execute(request, HttpConsulResponseHandler.INSTANCE));
			}
		} catch (Throwable t) {
			error.set(t);
		}
		return true;
	}

	@Override
	public final boolean isReleasable() {
		return response.get() != null || error.get() != null || interrupted.get();
	}
}