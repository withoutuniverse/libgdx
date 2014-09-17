package com.without.universe.wu.core.asset;

import com.without.universe.wu.core.CoreManager;
import com.without.universe.wu.core.CoreScreen;
import com.without.universe.wu.util.LogUtil;

public class BaseAsset<T> {

	protected CoreManager coreManager;
	protected String name;

	protected boolean loaded;
	protected Class<T> type;

	public BaseAsset(CoreManager coreManager, String name, Class<T> type) {
		this.coreManager = coreManager;
		this.name = name;
		this.type = type; 
	}
	
	public void load() {
		coreManager.resources.assetManager.load(name, type);
	}

	public T get() {
		return coreManager.resources.assetManager.get(name, type);
	}

	public boolean isLoaded() {
		if (!loaded) {
			loaded = coreManager.resources.isLoaded(name);
			if (loaded && CoreManager.sDebug) {
				LogUtil.d("loaded asset - " + name);
			}
		}
		return loaded;
	}

	public void unload(boolean checkDependencies) {
		loaded = false;
		if (checkDependencies) {
			for (CoreScreen screen : coreManager.screens) {
				BaseAsset<?>[] screenAssets = screen.getAssets();
				for (BaseAsset<?> screenAsset : screenAssets) {
					if (screenAsset != this && name.equals(screenAsset.name)) {
						return; // neededByOtherAsset
					}
				}
			}
		}
		coreManager.resources.unload(name);
		if (CoreManager.sDebug) {
			LogUtil.d("unloaded asset - " + name);
		}
	}

}
