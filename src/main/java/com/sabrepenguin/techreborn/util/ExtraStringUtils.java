package com.sabrepenguin.techreborn.util;

import com.ibm.icu.text.CompactDecimalFormat;
import com.ibm.icu.util.ULocale;

public class ExtraStringUtils {
	public static final CompactDecimalFormat FORMAT = CompactDecimalFormat.getInstance(ULocale.getDefault(), CompactDecimalFormat.CompactStyle.SHORT);

	public static String capitalizeByUnderscore(String name) {
		String[] words = name.split("_");
		StringBuilder builder = new StringBuilder();
		for(String word: words) {
			builder.append(org.apache.commons.lang3.StringUtils.capitalize(word));
		}
		return builder.toString();
	}
	public static String numberToCompactNumber(long number) {
		return FORMAT.format(number);
	}
}
