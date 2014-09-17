package com.without.universe.wu.core.asset;

import com.badlogic.gdx.audio.Music;
import com.without.universe.wu.core.CoreManager;

public class MusicOggAsset extends BaseAsset<Music> {

	public MusicOggAsset(CoreManager coreManager, String name) {
		super(coreManager, name, Music.class);
	}

}
