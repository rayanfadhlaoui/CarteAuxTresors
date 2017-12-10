package com.rayanfadhlaoui.domain.treasureMap.model.utils;

import java.util.regex.Pattern;

public class TreasureMapPattern {

	private static final String SEPARATOR = "-";
	private static final String LIST_OF_INSTRUCTION = "[ADG]+";
	private static final String DIRECTION = "[ENSO]";
	private static final String AT_LEAST_ONE_DIDIT = "\\d+";
	private static final String NAME = "[a-zA-Z]+";
	private static final String DIGIT_BIGGER_THAN_ZERO = "[1-9]+[0-9]*";
	private static final String ANY_CHARAC = ".*";

	public boolean isMapConfiguration(String line) {
		StringBuilder mapConfigurationRegexBuilder = new StringBuilder("C").append(SEPARATOR)
				.append(DIGIT_BIGGER_THAN_ZERO).append(SEPARATOR)
				.append(DIGIT_BIGGER_THAN_ZERO);
		return Pattern.matches(mapConfigurationRegexBuilder.toString(), line);
	}

	public boolean isMountain(String line) {
		StringBuilder mountainRegexBuilder = new StringBuilder("M").append(SEPARATOR)
				.append(AT_LEAST_ONE_DIDIT).append(SEPARATOR)
				.append(AT_LEAST_ONE_DIDIT);
		return Pattern.matches(mountainRegexBuilder.toString(), line);
	}

	public boolean isTreasure(String line) {
		StringBuilder treasureRegexBuilder = new StringBuilder("T").append(SEPARATOR)
				.append(AT_LEAST_ONE_DIDIT).append(SEPARATOR)
				.append(AT_LEAST_ONE_DIDIT).append(SEPARATOR)
				.append(AT_LEAST_ONE_DIDIT);
		return Pattern.matches(treasureRegexBuilder.toString(), line);
	}

	public boolean isComment(String line) {
		StringBuilder commentRegexBuilder = new StringBuilder("#")
				.append(ANY_CHARAC);
		return Pattern.matches(commentRegexBuilder.toString(), line);
	}

	public boolean isAdventurer(String line) {
		StringBuilder adventurerRegexBuilder = new StringBuilder("A").append(SEPARATOR)
				.append(NAME).append(SEPARATOR)
				.append(AT_LEAST_ONE_DIDIT).append(SEPARATOR)
				.append(AT_LEAST_ONE_DIDIT).append(SEPARATOR)
				.append(DIRECTION).append(SEPARATOR)
				.append(LIST_OF_INSTRUCTION);

		return Pattern.matches(adventurerRegexBuilder.toString(), line);
	}
}
