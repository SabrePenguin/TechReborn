package com.sabrepenguin.techreborn.util;

import net.minecraft.block.properties.IProperty;
import net.minecraft.util.ResourceLocation;

public interface INonStandardLocation {
    default String getPrefix() {
        return "";
    }
    default String getPostfix() {
        return "";
    }
	default boolean hasResourceLocation() {return false;}
	default ResourceLocation getResourceLocation() {return null;}
    default IProperty<?>[] getProperties() {
        return new IProperty[0];
    }
    default IProperty<?>[] getIgnoredProperties() {
        return new IProperty[0];
    }
}
