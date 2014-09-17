package com.without.universe.wu.ui;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

/**
 * @author mralliance ZoomImageButton extends SimpleImageButton and have pulsing
 *         (using of sin) picture
 */
public class UniversalZoomButton extends UniversalButton {

	private Sprite sprite;
	private Sprite spritePressed;
	private float beginScale;
	private float beginScalePressed;
	private float currentScale;
	private float sin = 0f;

	private final float mScaleFactor;

	public UniversalZoomButton(TextureRegion region, float scaleFactor) {
		super(region);
		mScaleFactor = scaleFactor;

		sprite = ((SpriteDrawable) getStyle().imageUp).getSprite();
		beginScale = sprite.getScaleX();

		spritePressed = ((SpriteDrawable) getStyle().imageDown).getSprite();
		beginScalePressed = spritePressed.getScaleX();
	}

	public void addOnStage(String name, Stage stage, float x, float y, EventListener listener) {
		setName(name);
		setPosition(x, y);
		addListener(listener);
		stage.addActor(this);
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		currentScale = beginScale + (float) Math.sin(sin) * mScaleFactor;
		sin += delta * 7;
		sprite.setScale(currentScale + mScaleFactor * 2);
		spritePressed.setScale(currentScale + beginScalePressed - beginScale + mScaleFactor * 2);
	}

}
