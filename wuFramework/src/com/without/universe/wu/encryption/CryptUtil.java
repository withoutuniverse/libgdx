package com.without.universe.wu.encryption;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

import com.badlogic.gdx.utils.StreamUtils;

public class CryptUtil {
	private static final Random RANDOM = new Random();
	private static final int[] CRYPT_VALS = { 13, 177, 24, 36, 222, 89, 85, 56 };

	static int proceedByte(int data, long position) {
		return (data ^ CRYPT_VALS[(int) (position % CRYPT_VALS.length)]);
	}

	/**
	 * Encrypt all files in folders and subfolders for OS Windows
	 * 
	 * @param directory
	 * @throws IOException
	 */
	public static void cryptExternalFolder(File directory) throws IOException {
		File[] files = directory.listFiles();
		for (File one : files) {
			if (one.isFile()) {
				cryptExternalFile(one);
			}
			if (one.isDirectory()) {
				cryptExternalFolder(one);
			}
		}
	}

	/**
	 * Encrypt file for OS Windows
	 * 
	 * @param file
	 * @throws IOException
	 */
	public static void cryptExternalFile(File fileIs) throws IOException {
		File fileOs = new File(fileIs.getAbsoluteFile() + "_crypt" + RANDOM.nextDouble());
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new FileInputStream(fileIs);
			os = new FileOutputStream(fileOs);
			int read = 0;
			int position = 0;
			while ((read = is.read()) != -1) {
				read = proceedByte(read, position);
				++position;
				os.write(read);
			}
		} finally {
			StreamUtils.closeQuietly(is);
			StreamUtils.closeQuietly(os);
		}

		if (!fileIs.delete() || !fileOs.renameTo(fileIs)) {
			throw new IOException("can't proceed: " + fileIs);
		}
	}
}
