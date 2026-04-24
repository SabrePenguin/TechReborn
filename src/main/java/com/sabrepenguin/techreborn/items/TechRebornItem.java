package com.sabrepenguin.techreborn.items;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.TechReborn;
import com.sabrepenguin.techreborn.util.INonStandardLocation;
import net.minecraft.item.Item;

public class TechRebornItem extends Item implements INonStandardLocation {
    private final String prefix;
    public TechRebornItem(String registryName, String translationKey, String prefix) {
		this.setCreativeTab(TechReborn.RESOURCE_TAB);
        this.prefix = prefix;
        this.setRegistryName(Tags.MODID, registryName);
        this.setTranslationKey(Tags.MODID + "." + translationKey);
    }

	public TechRebornItem(String name, String prefix) {
		ItemHelper.registerItem(this, name);
		this.prefix = prefix;
	}

    @Override
    public String getPrefix() {
        return this.prefix;
    }
}
