package com.without.universe.wu;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class UniversalAnimation extends Animation {

	private float delta;

	public UniversalAnimation(float frameDuration, TextureRegion[] keyFrames) {
		super(frameDuration, keyFrames);
		setPlayMode(PlayMode.LOOP);
	}

	public TextureRegion getFrame(float delta) {
		this.delta += delta;
		return getKeyFrame(this.delta);
	}

}
