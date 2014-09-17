package com.without.universe.wu.util;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.badlogic.gdx.utils.StreamUtils;

public class StreamUtil {

	public static void writeByte(DataOutputStream stream, byte value) {
		try {
			stream.writeByte(value);
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}

	public static void writeInt(DataOutputStream stream, int value) {
		try {
			stream.writeInt(value);
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}

	public static void writeShort(DataOutputStream stream, short value) {
		try {
			stream.writeShort(value);
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}

	public static void writeFloat(DataOutputStream stream, float value) {
		try {
			stream.writeFloat(value);
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}

	public static void writeBoolean(DataOutputStream stream, boolean value) {
		try {
			stream.writeBoolean(value);
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}

	public static byte readByte(DataInputStream stream) {
		try {
			return stream.readByte();
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}

	public static int readInt(DataInputStream stream) {
		try {
			return stream.readInt();
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}

	public static short readShort(DataInputStream stream) {
		try {
			return stream.readShort();
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}

	public static float readFloat(DataInputStream stream) {
		try {
			return stream.readFloat();
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}

	public static boolean readBoolean(DataInputStream stream) {
		try {
			return stream.readBoolean();
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}

	public static void closeQuietly(Closeable stream) {
		StreamUtils.closeQuietly(stream);
	}

}
