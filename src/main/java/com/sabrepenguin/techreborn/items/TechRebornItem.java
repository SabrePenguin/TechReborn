package com.sabrepenguin.techreborn.items;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.util.INonStandardLocation;

public class TechRebornItem extends ItemBase implements INonStandardLocation {
    private final String prefix;
    public TechRebornItem(String registryName, String translationKey, String prefix) {
        this.prefix = prefix;
        this.setRegistryName(Tags.MODID, registryName);
        this.setTranslationKey(Tags.MODID + "." + translationKey);
    }

    public TechRebornItem(String registryName, String translationKey) {
        this(registryName, translationKey, "");
    }

    @Override
    public String getPrefix() {
        return this.prefix;
    }
}
