package com.without.universe.wu.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

public abstract class ButtonEventListener implements EventListener {

	@Override
	public boolean handle(Event event) {
		if (event instanceof ChangeEvent) {
			String name = event.getTarget().getName();
			Gdx.app.log("BUTTON CLICK", name);
			onClick(event, name);
			return true;
		}
		return false;
	}

	public abstract void onClick(Event event, String name);

}
