package com.ecwid.consul.json;

import com.google.gson.Gson;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public class GsonFactory {
	private static final Gson GSON = new Gson();

	public static Gson getGson() {
		return GSON;
	}
	// TODO Utilize Jackson if it is on the classpath
}