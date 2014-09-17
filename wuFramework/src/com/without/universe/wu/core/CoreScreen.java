package com.without.universe.wu.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.without.universe.wu.core.asset.BaseAsset;
import com.without.universe.wu.util.LogUtil;

public abstract class CoreScreen {

	protected CoreManager manager;
	protected Stage stage;
	protected SpriteBatch spriteBatch;
	protected OrthographicCamera hudCamera;
	protected InputProcessor inputProcessor;
	protected BaseAsset<?>[] assets;
	protected boolean active = false;
	protected boolean isAssetsInitialized;

	public void create(CoreManager parent) {
		this.manager = parent;
		spriteBatch = new SpriteBatch();
		stage = new Stage(parent.viewport, new SpriteBatch());
		float viewportWidth = parent.viewport.getWorldWidth();
		float viewportHeight = parent.viewport.getWorldHeight();
		hudCamera = new OrthographicCamera(viewportWidth, viewportHeight);
		hudCamera.position.set(viewportWidth * 0.5f, viewportHeight * 0.5f, 0.0f);
	}

	public void setActive(boolean active) {
		this.active = active;

		if (CoreManager.sDebug) {
			LogUtil.d("setActive = " + active + " ;" + this);
		}
	}

	public boolean isActive() {
		return active;
	}

	public abstract LoaderScreen createLoaderScreen();

	public abstract void replaceWithLoader(CoreScreen screen);

	public abstract void act(float delta);

	public void resume() {
	}

	public void pause() {
		isAssetsInitialized = false;
	}

	public void destroy() {
		stage.dispose();
		unloadAssets(true);
	}

	public void assetsReady() {
	}

	protected void unloadAssets(boolean checkDependencies) {
		isAssetsInitialized = false;
		for (BaseAsset<?> asset : assets) {
			asset.unload(checkDependencies);
		}
	}

	public void update(float delta) {
		boolean isAssetsReady = isAssetsReady();
		if (isAssetsReady) {
			if (!isAssetsInitialized) {
				assetsReady();
				isAssetsInitialized = true;
			} else if (isActive()) {
				act(delta);
			}
		}
	}

	public void backPressed() {
	}

	public BaseAsset<?>[] getAssets() {
		return assets;
	}

	public boolean isAssetsReady() {
		if (isAssetsInitialized) {
			return true;
		}
		BaseAsset<?>[] assets = getAssets();
		for (BaseAsset<?> asset : assets) {
			if (!asset.isLoaded()) {
				return false;
			}
		}
		return true;
	}

	public void loadAssets() {
		BaseAsset<?>[] assets = getAssets();
		for (BaseAsset<?> asset : assets) {
			asset.load();
		}
	}

	public InputProcessor getInputProcessor() {
		return inputProcessor;
	}

	public Stage getStage() {
		return stage;
	}

	public boolean isTouched(Rectangle rect, int pointer) {
		if (isActive()) {
			return Gdx.input.isTouched(pointer) && rect.contains(Gdx.input.getX(pointer), Gdx.input.getY(pointer));
		}
		return false;
	}

	public boolean isPressed(int key) {
		if (isActive()) {
			return Gdx.input.isKeyPressed(key);
		}
		return false;
	}

	public Ray getPickRay(int screenX, int screenY, Camera camera) {
		Viewport viewport = manager.viewport;
		Ray ray = camera.getPickRay(screenX, screenY, viewport.getScreenX(), viewport.getScreenY(),
				viewport.getScreenWidth(), viewport.getScreenHeight());
		return ray;
	}
}
