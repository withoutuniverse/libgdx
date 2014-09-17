package com.without.universe.wu.util;

import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class CompressionUtil {
	public static byte[] compress(byte[] data) {
		try {
			Deflater deflater = new Deflater();
			deflater.setInput(data);
			//deflater.setLevel(Deflater.BEST_COMPRESSION);

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);

			deflater.finish();
			byte[] buffer = new byte[1024];
			while (!deflater.finished()) {
				int count = deflater.deflate(buffer);
				outputStream.write(buffer, 0, count);
			}
			deflater.end();
			outputStream.close();
			byte[] output = outputStream.toByteArray();
			return output;
		} catch (Exception ex) {
			return null;
		}
	}

	public static byte[] decompress(byte[] data) {
		try {
			Inflater inflater = new Inflater();
			inflater.setInput(data);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
			byte[] buffer = new byte[1024];
			while (!inflater.finished()) {
				int count = inflater.inflate(buffer);
				outputStream.write(buffer, 0, count);
			}
			outputStream.close();
			byte[] output = outputStream.toByteArray();
			return output;
		} catch (Exception ex) {
			return null;
		}
	}
}