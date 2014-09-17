package com.without.universe.wu.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

/**
 * @author mralliance button with one image and two states: original sprite and
 *         rotated and scaled sprite
 */
public class UniversalButton extends ImageButton {

	private static final int UNPRESSED = 1;
	private static final int PRESSED = 2;

	private static final float PRESSED_SCALE = 1.05f;
	private static final float PRESSED_ANGLE = 3f;

	private static Drawable createDrawable(TextureRegion region, int type, float pressedScale, float pressedAngle) {
		Sprite sprite = new Sprite(region);
		if (type == PRESSED) {
			sprite.setScale(pressedScale);
			sprite.setRotation(pressedAngle);
		}
		SpriteDrawable spriteDrawable = new SpriteDrawable(sprite);
		return spriteDrawable;
	}

	private TextureRegion region;

	public UniversalButton(TextureRegion region) {
		super(createDrawable(region, UNPRESSED, PRESSED_SCALE, PRESSED_ANGLE),
				createDrawable(region, PRESSED, PRESSED_SCALE, PRESSED_ANGLE));
		this.region = region;
	}

	public void addOnStage(String name, Stage stage, float x, float y, EventListener listener) {
		setName(name);
		setPosition(x, y);
		addListener(listener);
		stage.addActor(this);
	}

	public UniversalButton(TextureRegion region, float pressedScale, float pressedAngle) {
		super(createDrawable(region, UNPRESSED, pressedScale, pressedAngle), createDrawable(region, PRESSED, pressedScale, pressedAngle));

		setTouchable(Touchable.enabled);

		addListener(new InputListener() {
			@Override
			public void touchDragged(InputEvent event, float x, float y, int pointer) {
				Gdx.app.log("", "touchDragged");
				super.touchDragged(event, x, y, pointer);
			}
		});
	}

	/**
	 * @param region
	 *            replace old region on this (for ex. - sound button changing)
	 */
	public void setRegion(TextureRegion region) {
		((SpriteDrawable) getStyle().imageUp).getSprite().setRegion(region);
		((SpriteDrawable) getStyle().imageDown).getSprite().setRegion(region);
	}

	public TextureRegion getRegion() {
		return region;
	}

}
