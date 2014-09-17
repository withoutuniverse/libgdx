package com.without.universe.wu.core;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public abstract class LoaderScreen extends CoreScreen {

	private Actor actor;
	private CoreScreen caller;

	public LoaderScreen() {
	}

	public void setCaller(CoreScreen caller) {
		this.caller = caller;
	}

	@Override
	public void resume() {
		super.resume();
		loadAssets();
		setActive(true);
	}

	protected void loadAllAssets() {
		for (CoreScreen screen : manager.screens) {
			screen.loadAssets();
		}
	}

	@Override
	public void assetsReady() {
		super.assetsReady();
		actor = new Actor();
		actor.addAction(Actions.sequence(new ShowingAction(), new ProgressAction(), new HidingAction()));
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		manager.resources.update(delta);
		if (actor != null) {
			actor.act(delta);
		}
	}

	protected abstract boolean isShowedScreen();

	protected abstract void updateProgress();

	protected abstract boolean isHidedScreen();

	protected void onAssetsLoaded() {
		for (CoreScreen screen : manager.screens) {
			screen.setActive(true);
		}

		if (caller != null) {
			manager.removeScreen(caller);
			caller = null;
		}
	}

	private final class ShowingAction extends Action {
		@Override
		public boolean act(float delta) {
			boolean result = isShowedScreen();
			if (result) {
				loadAllAssets();
			}
			return result;
		}
	}

	private final class ProgressAction extends Action {
		@Override
		public boolean act(float delta) {
			updateProgress();
			if (manager.resources.update(delta)) {
				onAssetsLoaded();
				return true;
			}
			return false;
		}
	}

	private final class HidingAction extends Action {
		@Override
		public boolean act(float delta) {
			if (isHidedScreen()) {
				manager.popScreen();
				actor = null;
				return true;
			}
			return false;
		}
	}

}
