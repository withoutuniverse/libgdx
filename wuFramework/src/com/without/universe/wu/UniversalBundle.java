package com.without.universe.wu;

import java.util.Hashtable;

public class UniversalBundle {
	private Hashtable<String, Object> hashTable;

	public UniversalBundle() {
		hashTable = new Hashtable<String, Object>();
	}

	public void putBool(String key, boolean value) {
		Boolean result = new Boolean(value);
		hashTable.put(key, result);
	}

	public void putInt(String key, int value) {
		Integer result = new Integer(value);
		hashTable.put(key, result);
	}

	public void putShort(String key, short value) {
		Short result = new Short(value);
		hashTable.put(key, result);
	}

	public void putString(String key, String value) {
		hashTable.put(key, value);
	}

	public void putFloat(String key, float value) {
		hashTable.put(key, value);
	}

	public int getInt(String key) {
		Integer result = (Integer) hashTable.get(key);
		return result.intValue();
	}

	public boolean getBool(String key) {
		Boolean result = (Boolean) hashTable.get(key);
		return result.booleanValue();
	}

	public String getString(String key) {
		String result = (String) hashTable.get(key);
		return result;
	}

	public float getFloat(String key) {
		Float result = (Float) hashTable.get(key);
		return result.floatValue();
	}

	public short getShort(String key) {
		Short result = (Short) hashTable.get(key);
		return result.shortValue();
	}
}
