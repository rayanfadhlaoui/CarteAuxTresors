package com.rayanfadhlaoui.domain.treasureMap.model.utils;

public class StringUtils {

	private static final String EMPTY = "";
	private static final String WHITESPACE = "\\s+";
	public static final String LINE_BREAK = System.getProperty("line.separator");

	public static String removeWhiteSpace(String value) {
		if (value != null) {
			value = value.replaceAll(WHITESPACE, EMPTY);
		}
		return value;
	}

}
