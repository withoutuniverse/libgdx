package com.without.universe.wu.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class HorizontalParallax {

	private TextureRegion region;
	private float speed;
	private float currentX;
	private float y;
	private float width;

	public HorizontalParallax(TextureRegion region, float y, float screenWidth) {
		this.region = region;
		this.y = y;
		this.width = screenWidth;
	}

	public float getY() {
		return y;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public void gameCycle(float delta) {
		currentX -= speed * delta;
		if (currentX < -region.getRegionWidth()) {
			currentX += region.getRegionWidth();
		}
	}

	public void draw(SpriteBatch batch) {
		float tempX = currentX;
		while (tempX < width) {
			batch.draw(region, tempX, y);
			tempX += region.getRegionWidth() / 2;
		}
	}

}
