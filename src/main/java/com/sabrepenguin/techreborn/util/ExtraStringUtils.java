package com.sabrepenguin.techreborn.util;

public class ExtraStringUtils {
	public static String capitalizeByUnderscore(String name) {
		String[] words = name.split("_");
		StringBuilder builder = new StringBuilder();
		for(String word: words) {
			builder.append(org.apache.commons.lang3.StringUtils.capitalize(word));
		}
		return builder.toString();
	}
}
