package com.sabrepenguin.techreborn.util;

import com.github.bsideup.jabel.Desugar;
import org.apache.commons.lang3.StringUtils;

@Desugar
public record MetadataHelper(int meta, String name) {
    public static final String META_PLACEHOLDER = "PLACEHOLDER_ITEM";

    public String capitalize() {
        String[] words = name.split("_");
        StringBuilder builder = new StringBuilder();
        for(String word: words) {
            builder.append(StringUtils.capitalize(word));
        }
        return builder.toString();
    }
}
