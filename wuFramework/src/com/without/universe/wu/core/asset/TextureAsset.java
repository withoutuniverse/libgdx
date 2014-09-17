package com.without.universe.wu.core.asset;

import com.badlogic.gdx.graphics.Texture;
import com.without.universe.wu.core.CoreManager;

public class TextureAsset extends BaseAsset<Texture> {

	public TextureAsset(CoreManager coreManager, String name) {
		super(coreManager, name, Texture.class);
	}

}
