package com.without.universe.wu.encryption;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.OutputStream;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.files.FileHandle;

final class CryptFileHandle extends FileHandle {
	private FileHandle fileHandle;

	CryptFileHandle(FileHandle fileHandle) {
		this.fileHandle = fileHandle;
	}

	@Override
	public InputStream read() {
		return new CryptInputStream(fileHandle.read());
	}

	@Override
	public OutputStream write(boolean append) {
		throw new NotImplementedException();
	}

	@Override
	public String name() {
		return fileHandle.name();
	}

	@Override
	public String extension() {
		return fileHandle.extension();
	}

	@Override
	public String toString() {
		return fileHandle.toString();
	}

	@Override
	public FileHandle child(String name) {
		return fileHandle.child(name);
	}

	@Override
	public void copyTo(FileHandle dest) {
		fileHandle.copyTo(dest);
	}

	@Override
	public boolean delete() {
		return fileHandle.delete();
	}

	@Override
	public boolean deleteDirectory() {
		return fileHandle.deleteDirectory();
	}

	@Override
	public void emptyDirectory() {
		fileHandle.emptyDirectory();
	}

	@Override
	public void emptyDirectory(boolean preserveTree) {
		fileHandle.emptyDirectory(preserveTree);
	}

	@Override
	public boolean equals(Object obj) {
		return fileHandle.equals(obj);
	}

	@Override
	public int hashCode() {
		return fileHandle.hashCode();
	}

	@Override
	public boolean exists() {
		return fileHandle.exists();
	}

	@Override
	public File file() {
		return fileHandle.file();
	}

	@Override
	public boolean isDirectory() {
		return fileHandle.isDirectory();
	}

	@Override
	public long lastModified() {
		return fileHandle.lastModified();
	}

	@Override
	public long length() {
		return fileHandle.length();
	}

	@Override
	public FileHandle[] list() {
		return fileHandle.list();
	}

	@Override
	public FileHandle[] list(FileFilter filter) {
		return fileHandle.list(filter);
	}

	@Override
	public FileHandle[] list(FilenameFilter filter) {
		return fileHandle.list(filter);
	}

	@Override
	public FileHandle[] list(String suffix) {
		return fileHandle.list(suffix);
	}

	@Override
	public void mkdirs() {
		fileHandle.mkdirs();
	}

	@Override
	public void moveTo(FileHandle dest) {
		fileHandle.moveTo(dest);
	}

	@Override
	public String nameWithoutExtension() {
		return fileHandle.nameWithoutExtension();
	}

	@Override
	public FileHandle parent() {
		return fileHandle.parent();
	}

	@Override
	public String path() {
		return fileHandle.path();
	}

	@Override
	public String pathWithoutExtension() {
		return fileHandle.pathWithoutExtension();
	}

	@Override
	public FileHandle sibling(String name) {
		return fileHandle.sibling(name);
	}

	@Override
	public FileType type() {
		return fileHandle.type();
	}

}