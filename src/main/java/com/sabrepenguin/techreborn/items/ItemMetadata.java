package com.sabrepenguin.techreborn.items;

import com.github.bsideup.jabel.Desugar;
import org.apache.commons.lang3.StringUtils;

@Desugar
public record ItemMetadata(int meta, String name) {
    public String capitalize() {
        String[] words = name.split("_");
        StringBuilder builder = new StringBuilder();
        for(String word: words) {
            builder.append(StringUtils.capitalize(word));
        }
        return builder.toString();
    }
}
