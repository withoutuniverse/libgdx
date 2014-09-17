package com.without.universe.wu.helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * @author mralliance class for Rate, have 2 functions
 */
public class RateHelper {
	public static String sSharedPrefsName;
	public static final String NEED_SHOW_FIELD_NAME = "rate_helper_need_show";
	public static final String RATE_CALL_BEFORE_SHOW_NAME = "rate_helper_calls_count";
	public static final int RATE_CALL_BEFORE_SHOW = 3;
	public static final int RATE_INITIAL_COUNT = 2;

	public static void initialize(String prefsName) {
		sSharedPrefsName = prefsName;
	}

	/**
	 * @return true if time to rate
	 */
	public static boolean isNeeedIncrement() {
		Preferences preferences = Gdx.app.getPreferences(sSharedPrefsName);
		boolean availible = preferences.getBoolean(NEED_SHOW_FIELD_NAME, true);
		if (!availible) {
			return false;
		}
		boolean result = false;
		int calls = preferences.getInteger(RATE_CALL_BEFORE_SHOW_NAME, RATE_INITIAL_COUNT);
		calls = calls - 1;
		if (calls <= 0) {
			result = true;
			calls = RATE_CALL_BEFORE_SHOW;
		}
		preferences.putInteger(RATE_CALL_BEFORE_SHOW_NAME, calls);
		preferences.flush();
		return result;
	}

	public static boolean isNeedShow() {
		Preferences preferences = Gdx.app.getPreferences(sSharedPrefsName);
		boolean availible = preferences.getBoolean(NEED_SHOW_FIELD_NAME, true);
		return availible;
	}

	/**
	 * after calling, isNeed() will always return false
	 */
	public static void neverShow() {
		Preferences preferences = Gdx.app.getPreferences(sSharedPrefsName);
		preferences.putBoolean(NEED_SHOW_FIELD_NAME, false);
		preferences.flush();
	}

}
