package com.without.universe.wu.util;

public class AssetNameUtil {

	public static String numberToString(int number, int charsCount) {
		String iStr = Integer.toString(number);
		String counter = "";
		if (iStr.length() == 1) {
			counter = "000" + iStr;
		} else if (iStr.length() == 2) {
			counter = "00" + iStr;
		} else if (iStr.length() == 3) {
			counter = "0" + iStr;
		}
		counter = counter.substring(4 - charsCount);
		return counter;
	}

}
