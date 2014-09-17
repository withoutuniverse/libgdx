package com.without.universe.wu.core;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.without.universe.wu.core.asset.BaseAsset;
import com.without.universe.wu.util.LogUtil;

public class CoreManager implements ApplicationListener {

	public static final float MAX_STEP_BETWEEN_FRAMES = 0.025f;

	public static volatile boolean sDebug = true;
	public static volatile boolean sCrypt = true;

	public final int worldWidth, worldHeight;
	public final CoreResources resources;
	public final CoreOsManager osManager;
	public Array<CoreScreen> screens;
	public Viewport viewport;

	protected int language;
	protected FPSLogger fpsLogger;

	public CoreManager(CoreOsManager osManager, int worldWidth, int worldHeight) {
		this.resources = new CoreResources();
		this.osManager = osManager;
		this.worldWidth = worldWidth;
		this.worldHeight = worldHeight;
	}

	public void create() {
		screens = new Array<CoreScreen>(8);
		language = 0; // not impl. for now
		viewport = new FitViewport(worldWidth, worldHeight);
		Gdx.input.setCatchBackKey(true);
		if (sDebug) {
			fpsLogger = new FPSLogger();
		}
		resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	public void pushScreen(CoreScreen screen) {
		screens.add(screen);
		screen.create(this);
		refreshInputProcessor();
		screen.resume();

		if (sDebug) {
			LogUtil.d("current screen = " + getCurrentScreen());
		}

		if (sDebug) {
			LogUtil.d("screens = " + screens.size);
		}
	}

	public CoreScreen getCurrentScreen() {
		return screens.peek();
	}

	public void popScreen() {
		if (screens.size > 0) {
			removeScreen(screens.size - 1);
		}
	}

	public void removeScreen(int index) {
		CoreScreen screen = screens.removeIndex(index);
		refreshInputProcessor();
		screen.pause();
		screen.destroy();

		if (sDebug) {
			LogUtil.d("current screen = " + getCurrentScreen());
		}

		if (sDebug) {
			LogUtil.d("screens = " + screens.size);
		}
	}

	public void removeScreen(CoreScreen screen) {
		int index = screens.indexOf(screen, true);
		if (index != -1) {
			removeScreen(index);
		}
	}

	public void exit() {
		Gdx.app.exit();
	}

	public void resize(int newWidth, int newHeight) {
		viewport.update(newWidth, newHeight, true);
	}

	public void render() {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		if (sDebug) {
			fpsLogger.log();
		}
		float delta = Gdx.graphics.getDeltaTime();
		delta = Math.min(delta, MAX_STEP_BETWEEN_FRAMES);

		for (int i = 0; i < screens.size; ++i) {
			screens.get(i).update(delta);
		}
	}

	public void pause() {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		for (int i = screens.size - 1; i >= 0; --i) {
			CoreScreen screen = screens.get(i);
			for (BaseAsset<?> asset : screen.getAssets()) {
				asset.unload(false);
			}
			screen.pause();
			screen.setActive(false);
		}

		resources.clear();
	}

	public void resume() {
		for (int i = 0; i < screens.size; ++i) {
			screens.get(i).resume();
		}

		CoreScreen currentScreen = getCurrentScreen();
		if (!(currentScreen instanceof LoaderScreen)) {
			pushScreen(currentScreen.createLoaderScreen());
		}
	}

	public void dispose() {
		for (int i = 0; i < screens.size; ++i) {
			screens.get(i).destroy();
		}
		screens.clear();
		resources.clear();
	}

	public void refreshInputProcessor() {
		Gdx.input.setInputProcessor(new UniversalInputProcessor(getCurrentScreen()));
	}

	private static final class UniversalInputProcessor implements InputProcessor {

		private Array<InputProcessor> inputProcessors;
		private CoreScreen screen;

		public UniversalInputProcessor(CoreScreen screen) {
			this.screen = screen;
			InputProcessor stageProcessor = screen.getStage();
			InputProcessor screenProcessor = screen.getInputProcessor();
			this.inputProcessors = new Array<InputProcessor>();
			if (stageProcessor != null) {
				inputProcessors.add(stageProcessor);
			}
			if (screenProcessor != null) {
				inputProcessors.add(screenProcessor);
			}

			if (sDebug) {
				LogUtil.d("stage processor = " + stageProcessor);
				LogUtil.d("screen processor = " + screenProcessor);
			}

		}

		public boolean keyDown(int keycode) {
			if (!screen.isActive()) {
				return false;
			}

			for (int i = 0, n = inputProcessors.size; i < n; i++) {
				if (inputProcessors.get(i).keyDown(keycode)) {
					return true;
				}
			}
			return false;
		}

		public boolean keyUp(int keycode) {
			if (!screen.isActive()) {
				return false;
			}
			if (keycode == Keys.BACK || keycode == Keys.BACKSPACE) {
				screen.backPressed();
				return true;
			}

			for (int i = 0, n = inputProcessors.size; i < n; i++) {
				if (inputProcessors.get(i).keyUp(keycode)) {
					return true;
				}
			}
			return false;
		}

		public boolean keyTyped(char character) {
			if (!screen.isActive()) {
				return false;
			}
			for (int i = 0, n = inputProcessors.size; i < n; i++) {
				if (inputProcessors.get(i).keyTyped(character)) {
					return true;
				}
			}
			return false;
		}

		public boolean touchDown(int screenX, int screenY, int pointer, int button) {
			if (!screen.isActive()) {
				return false;
			}
			for (int i = 0, n = inputProcessors.size; i < n; i++) {
				if (inputProcessors.get(i).touchDown(screenX, screenY, pointer, button)) {
					return true;
				}
			}
			return false;
		}

		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			if (!screen.isActive()) {
				return false;
			}
			for (int i = 0, n = inputProcessors.size; i < n; i++) {
				if (inputProcessors.get(i).touchUp(screenX, screenY, pointer, button)) {
					return true;
				}
			}
			return false;
		}

		public boolean touchDragged(int screenX, int screenY, int pointer) {
			if (!screen.isActive()) {
				return false;
			}
			for (int i = 0, n = inputProcessors.size; i < n; i++) {
				if (inputProcessors.get(i).touchDragged(screenX, screenY, pointer)) {
					return true;
				}
			}
			return false;
		}

		@Override
		public boolean mouseMoved(int screenX, int screenY) {
			if (!screen.isActive()) {
				return false;
			}
			for (int i = 0, n = inputProcessors.size; i < n; i++) {
				if (inputProcessors.get(i).mouseMoved(screenX, screenY)) {
					return true;
				}
			}
			return false;
		}

		@Override
		public boolean scrolled(int amount) {
			if (!screen.isActive()) {
				return false;
			}
			for (int i = 0, n = inputProcessors.size; i < n; i++) {
				if (inputProcessors.get(i).scrolled(amount)) {
					return true;
				}
			}
			return false;
		}
	}

}
