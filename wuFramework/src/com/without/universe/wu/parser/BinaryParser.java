package com.without.universe.wu.parser;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public interface BinaryParser {
	void parseBinary(DataInputStream stream);
	void fillBinary(DataOutputStream stream);
}
