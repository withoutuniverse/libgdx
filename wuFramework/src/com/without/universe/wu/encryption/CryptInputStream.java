package com.without.universe.wu.encryption;

import java.io.IOException;
import java.io.InputStream;

public final class CryptInputStream extends InputStream {

	private long position;

	private InputStream inputStream;

	CryptInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	@Override
	public int read() throws IOException {
		int one;
		if ((one = inputStream.read()) != -1) {
			one = CryptUtil.proceedByte(one, position);
			++position;
		}
		return one;
	}

	@Override
	public int available() throws IOException {
		return inputStream.available();
	}

	@Override
	public void close() throws IOException {
		inputStream.close();
	}

	@Override
	public boolean equals(Object obj) {
		return inputStream.equals(obj);
	}

	@Override
	public int hashCode() {
		return inputStream.hashCode();
	}

	@Override
	public synchronized void mark(int readlimit) {
		inputStream.mark(readlimit);
	}

	@Override
	public boolean markSupported() {
		return inputStream.markSupported();
	}

	@Override
	public synchronized void reset() throws IOException {
		inputStream.reset();
	}

	@Override
	public long skip(long n) throws IOException {
		long skipped = inputStream.skip(n);
		position += skipped;
		return skipped;
	}

	@Override
	public String toString() {
		return inputStream.toString();
	}

}