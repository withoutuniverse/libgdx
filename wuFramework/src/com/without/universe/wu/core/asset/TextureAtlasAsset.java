package com.without.universe.wu.core.asset;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.without.universe.wu.core.CoreManager;

public class TextureAtlasAsset extends BaseAsset<TextureAtlas> {

	public TextureAtlasAsset(CoreManager coreManager, String name) {
		super(coreManager, name, TextureAtlas.class);
	}

}
