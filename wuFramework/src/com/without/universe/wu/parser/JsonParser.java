package com.without.universe.wu.parser;

import org.json.simple.JSONObject;

public interface JsonParser {
	void fillJson(JSONObject json);
	void parseJson(JSONObject json);
}
