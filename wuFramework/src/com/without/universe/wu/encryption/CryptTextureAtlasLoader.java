package com.without.universe.wu.encryption;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

public class CryptTextureAtlasLoader extends TextureAtlasLoader {

	public CryptTextureAtlasLoader(FileHandleResolver resolver) {
		super(resolver);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Array<AssetDescriptor> getDependencies(String fileName, FileHandle atlasFile, TextureAtlasParameter parameter) {
		Array<AssetDescriptor> dependencies = super.getDependencies(fileName, atlasFile, parameter);
		for (AssetDescriptor descriptor : dependencies) {
			if (!(descriptor.file instanceof CryptFileHandle)) {
				descriptor.file = new CryptFileHandle(descriptor.file);
			}
		}
		return dependencies;
	}

}
