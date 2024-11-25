package com.ecwid.consul;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public class Utils {
	private Utils() {
	}

	public static char[] charSequenceToArray(CharSequence seq) {
		int length = seq.length();
		char[] arr = new char[length];
		for (int i = 0; i < length; i++) {
			arr[i] = seq.charAt(i);
		}
		return arr;
	}

	public static String toSecondsString(long waitTime) {
		return String.valueOf(waitTime) + "s";
	}

	public static String assembleAgentAddress(String host, int port, String path) {
		String agentPath = "";
		if (path != null && !path.trim().isEmpty()) {
			agentPath = "/" + path;
		}
		return host + ":" + port + agentPath;
	}
}