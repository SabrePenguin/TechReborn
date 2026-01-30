package com.sabrepenguin.techreborn.util.models;

import net.minecraft.block.properties.IProperty;

public interface IPropertyBlockstate {
	default IProperty<?>[] getProperties() {
		return new IProperty[0];
	}
	default IProperty<?>[] getIgnoredProperties() {
		return new IProperty[0];
	}
}
