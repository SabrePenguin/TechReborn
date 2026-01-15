package com.sabrepenguin.techreborn.util;

import net.minecraft.block.properties.IProperty;

public interface INonStandardLocation {
    default String getPrefix() {
        return "";
    }
    default String getPostfix() {
        return "";
    }
    default IProperty<?>[] getProperties() {
        return new IProperty[0];
    }
    default IProperty<?>[] getIgnoredProperties() {
        return new IProperty[0];
    }
}
