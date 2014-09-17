package com.without.universe.wu.core;

import java.util.ArrayList;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.without.universe.wu.encryption.CryptFileResolver;
import com.without.universe.wu.encryption.CryptTextureAtlasLoader;
import com.without.universe.wu.util.AssetNameUtil;

public class CoreResources {

	public AssetManager assetManager;

	CoreResources() {
		clear();
	}

	public void unload(String name) {
		if (assetManager.isLoaded(name)) {
			assetManager.unload(name);
		}
	}

	public void clear() {
		if (assetManager != null) {
			assetManager.clear();
		}

		if (CoreManager.sCrypt) {
			FileHandleResolver fileHandleResolver = new CryptFileResolver(new InternalFileHandleResolver());
			assetManager = new AssetManager(fileHandleResolver);
			assetManager.setLoader(TextureAtlas.class, ".atlascrypt", new CryptTextureAtlasLoader(fileHandleResolver));
		} else {
			assetManager = new AssetManager();
		}
	}

	public boolean isLoaded(String name) {
		return assetManager.isLoaded(name);
	}

	public TextureRegion[] findRegions(TextureAtlas atlas, String name, int numsCount) {
		numsCount = numsCount < 2 ? 2 : numsCount;
		ArrayList<TextureRegion> regions = new ArrayList<TextureRegion>();
		int i = 1;
		while (true) {
			String fileName = name + AssetNameUtil.numberToString(i, numsCount);
			TextureRegion textureRegion = atlas.findRegion(fileName);
			if (textureRegion == null) {
				break;
			}
			regions.add(textureRegion);
			i++;
		}
		return regions.toArray(new TextureRegion[regions.size()]);
	}

	public TextureRegion[] findRegions(TextureAtlas atlas, String name) {
		return findRegions(atlas, name, 4);
	}

	public TextureRegion findRegion(TextureAtlas atlas, String name) {
		return atlas.findRegion(name);
	}

	public boolean update(float delta) {
		return assetManager.update();
	}

	public float getProgress() {
		return assetManager.getProgress();
	}
}
