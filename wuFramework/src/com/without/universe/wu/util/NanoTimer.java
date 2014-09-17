package com.without.universe.wu.util;

import com.badlogic.gdx.Gdx;

public class NanoTimer {

	private static final String TAG = NanoTimer.class.getSimpleName();

	private long nanotime;
	private String comment;

	public NanoTimer(String comment) {
		this.comment = comment;
	}

	public void start() {
		nanotime = System.nanoTime();
	}

	public void stop() {
		long msecs = (System.nanoTime() - nanotime) / 1000000;
		Gdx.app.log(TAG, (comment != null ? comment : "") + "; msecs=" + msecs);
	}

}
