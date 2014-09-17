package com.without.universe.wu.encryption;

import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;

public final class CryptFileResolver implements FileHandleResolver {

	private FileHandleResolver fileHandleResolver;

	public CryptFileResolver(FileHandleResolver fileHandleResolver) {
		this.fileHandleResolver = fileHandleResolver;
	}

	@Override
	public FileHandle resolve(String fileName) {
		return new CryptFileHandle(fileHandleResolver.resolve(fileName));
	}

}