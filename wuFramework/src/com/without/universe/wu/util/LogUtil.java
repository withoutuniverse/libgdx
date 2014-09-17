package com.without.universe.wu.util;

import com.badlogic.gdx.Gdx;

public final class LogUtil {

	public static void d(String msg) {
		Gdx.app.log(LogUtil.class.getSimpleName(), msg);
	}

	public static void dTrace(String msg) {
		Gdx.app.log(LogUtil.class.getSimpleName(), getLocation() + msg);
	}

	public static void dTrace() {
		Gdx.app.log(LogUtil.class.getSimpleName(), getLocation());
	}

	private static String getLocation() {
		final String className = LogUtil.class.getName();
		final StackTraceElement[] traces = Thread.currentThread().getStackTrace();
		boolean found = false;

		for (int i = 0; i < traces.length; i++) {
			StackTraceElement trace = traces[i];

			try {
				if (found) {
					if (!trace.getClassName().startsWith(className)) {
						Class<?> clazz = Class.forName(trace.getClassName());
						return "[" + getClassName(clazz) + ":" + trace.getMethodName() + ":" + trace.getLineNumber()
								+ "]: ";
					}
				} else if (trace.getClassName().startsWith(className)) {
					found = true;
					continue;
				}
			} catch (ClassNotFoundException e) {
			}
		}

		return "[]: ";
	}

	private static String getClassName(Class<?> clazz) {
		if (clazz != null) {
			if (clazz.getSimpleName() != null && !clazz.getSimpleName().equals("")) {
				return clazz.getSimpleName();
			}
			return getClassName(clazz.getEnclosingClass());
		}
		return "";
	}
}
