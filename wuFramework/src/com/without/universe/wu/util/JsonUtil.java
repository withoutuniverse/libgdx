package com.without.universe.wu.util;

public class JsonUtil {

	public static boolean parseBoolean(Object object) {
		return Boolean.parseBoolean(object.toString());
	}

	public static int parseInt(Object object) {
		return Integer.parseInt(object.toString());
	}

	public static float parseFloat(Object object) {
		return Float.parseFloat(object.toString());
	}

	public static byte parseByte(Object object) {
		return Byte.parseByte(object.toString());
	}

}
