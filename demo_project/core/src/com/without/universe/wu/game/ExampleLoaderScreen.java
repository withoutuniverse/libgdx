package com.without.universe.wu.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.without.universe.wu.core.CoreManager;
import com.without.universe.wu.core.CoreScreen;
import com.without.universe.wu.core.LoaderScreen;
import com.without.universe.wu.core.asset.BaseAsset;
import com.without.universe.wu.core.asset.TextureAtlasAsset;
import com.without.universe.wu.util.LogUtil;

public class ExampleLoaderScreen extends LoaderScreen {

	// for screen size = 32x20 (1280x800)
	private static final String LOADER_ATLAS = "gfx/loader.atlascrypt";
	private static final String IMAGE_LOGO = "logo";
	private static final String IMAGE_BLACK_SQUARE = "black_square";
	private static final String IMAGE_WHITE_SQUARE = "white_square";

	private final int actorsX = 16;
	private final int actorsY = 10;
	private final int actorsWhite = 9;

	private final float fadeDuration = 0.8f;
	private final float delay = 0.025f;

	private Image[] blackActors;
	private Image[] whiteActors;
	private Image logo;

	private TextureAtlasAsset loaderAtlasAsset;

	private int currentActorsInAction;

	public ExampleLoaderScreen() {
	}

	@Override
	public LoaderScreen createLoaderScreen() {
		return null;
	}

	@Override
	public void replaceWithLoader(CoreScreen screen) {
	}

	@Override
	public void create(CoreManager parent) {
		super.create(parent);

		loaderAtlasAsset = new TextureAtlasAsset(parent, LOADER_ATLAS);
		assets = new BaseAsset[] { /*new TextureAsset(parent, "gfx/loader.png"),*/ loaderAtlasAsset };

		if (CoreManager.sDebug) {
			LogUtil.d("ExampleLoaderScreen = " + this);
		}
	}

	@Override
	public void act(float delta) {
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void assetsReady() {
		super.assetsReady();

		TextureRegion blackSquareTexture = loaderAtlasAsset.get().findRegion(IMAGE_BLACK_SQUARE);
		TextureRegion whiteSquareTexture = loaderAtlasAsset.get().findRegion(IMAGE_WHITE_SQUARE);

		float blackTextureSize = 2f;

		blackActors = new Image[actorsX * actorsY];
		for (int i = 0; i < actorsX; ++i) {
			for (int j = actorsY - 1; j >= 0; --j) {
				Image actor = new Image(blackSquareTexture);
				actor.setSize(2f, 2f);
				actor.setOrigin(0f, blackTextureSize * 0.5f);
				actor.getColor().a = 0f;
				actor.setScale(0f);
				actor.setPosition(i * blackTextureSize, j * blackTextureSize);
				actor.addAction(Actions.sequence(
						Actions.delay(
								delay * i,
								Actions.parallel(Actions.scaleTo(1.05f, 1.05f, fadeDuration),
										Actions.fadeIn(fadeDuration))), Actions.run(new Runnable() {
							@Override
							public void run() {
								currentActorsInAction--;
							}
						})));
				blackActors[i * actorsY + j] = actor;
				stage.addActor(actor);
			}
		}

		whiteActors = new Image[actorsWhite];
		for (int i = 0; i < actorsWhite; ++i) {
			Image actor = new Image(whiteSquareTexture);
			actor.setSize(0.7f, 0.7f);
			actor.getColor().a = 0f;
			actor.setOrigin(0.25f, 0.25f);
			whiteActors[i] = actor;
			stage.addActor(actor);
		}

		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				whiteActors[j * 3 + i].setPosition((28 + i), (5 - j));
			}
		}

		currentActorsInAction = actorsX * actorsY;

		logo = new Image(loaderAtlasAsset.get().findRegion(IMAGE_LOGO));
		logo.setSize(20, 2);
		logo.setPosition(3, 16);
		logo.getColor().a = 0f;
		stage.addActor(logo);
		logo.addAction(Actions.fadeIn(1f));
	}

	@Override
	public void pause() {
		super.pause();
		stage.clear();
		blackActors = null;
		whiteActors = null;
		logo = null;
	}

	@Override
	protected boolean isShowedScreen() {
		return (currentActorsInAction < 5);
	}

	@Override
	protected void updateProgress() {
		float progress = manager.resources.getProgress();

		for (int i = 0; i < progress * 9; ++i) {
			if (i >= 9) {
				break;
			}

			if (!"007".equals(whiteActors[i].getName())) {
				whiteActors[i].setName("007");
				whiteActors[i].addAction(Actions.fadeIn(fadeDuration));
			}

		}

	}

	@Override
	protected boolean isHidedScreen() {
		return (currentActorsInAction < 5);
	}

	@Override
	protected void onAssetsLoaded() {
		super.onAssetsLoaded();

		currentActorsInAction = actorsX * actorsY;
		for (int i = 0; i < actorsX; ++i) {
			for (int j = actorsY - 1; j >= 0; --j) {
				Image actor = blackActors[i * actorsY + j];
				actor.addAction(Actions.sequence(
						Actions.delay(delay * i,
								Actions.parallel(Actions.scaleTo(0f, 0f, fadeDuration), Actions.fadeOut(fadeDuration))),
						Actions.run(new Runnable() {
							@Override
							public void run() {
								currentActorsInAction--;
							}
						})));
			}
		}

		for (int i = 0; i < 9; ++i) {
			whiteActors[i].addAction(Actions.fadeOut(fadeDuration));
		}

		logo.addAction(Actions.fadeOut(1f));
	}
}
