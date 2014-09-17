package com.without.universe.wu.game;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.without.universe.wu.core.CoreManager;
import com.without.universe.wu.core.CoreScreen;
import com.without.universe.wu.core.LoaderScreen;
import com.without.universe.wu.core.asset.BaseAsset;
import com.without.universe.wu.core.asset.TextureAsset;
import com.without.universe.wu.util.LogUtil;

public class ExampleGameScreen extends CoreScreen implements InputProcessor {

	private static final String[] IMAGE_BGS = new String[] { "gfx/example_bg_01.png", "gfx/example_bg_02.png",
			"gfx/example_bg_03.png", "gfx/example_bg_04.png", "gfx/example_bg_05.png" };

	private Sprite spriteBg;
	private String currentImageName;
	private TextureAsset bgAsset;

	@Override
	public void create(CoreManager parent) {
		super.create(parent);
		Random random = new Random(System.currentTimeMillis());
		currentImageName = IMAGE_BGS[random.nextInt(IMAGE_BGS.length)];
		bgAsset = new TextureAsset(parent, currentImageName);
		assets = new BaseAsset[] { bgAsset };
		inputProcessor = this;
		if (CoreManager.sDebug) {
			LogUtil.d("ExampleGameScreen = " + this);
		}
	}

	@Override
	public LoaderScreen createLoaderScreen() {
		return new ExampleLoaderScreen();
	}

	@Override
	public void replaceWithLoader(CoreScreen screen) {
		LoaderScreen loaderScreen = createLoaderScreen();
		loaderScreen.setCaller(this);
		if (screen != null) {
			manager.pushScreen(screen);
		}
		manager.pushScreen(loaderScreen);
	}

	@Override
	public void assetsReady() {
		spriteBg = new Sprite(bgAsset.get());
		spriteBg.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);

		float h = manager.worldHeight;
		float w = spriteBg.getRegionWidth() * h / spriteBg.getRegionHeight();

		spriteBg.setSize(w, h);
		spriteBg.setPosition(0, 0);
	}

	@Override
	public void pause() {
		super.pause();
		stage.clear();
	}

	@Override
	public void act(float delta) {
		spriteBg.setPosition(spriteBg.getX() - 6f * delta, 0);

		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		hudCamera.update();
		spriteBatch.setProjectionMatrix(hudCamera.combined);

		spriteBatch.begin();
		spriteBg.draw(spriteBatch);
		spriteBatch.end();

		stage.act(delta);
	}

	@Override
	public void backPressed() {
		manager.exit();
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		replaceWithLoader(new ExampleGameScreen());
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
