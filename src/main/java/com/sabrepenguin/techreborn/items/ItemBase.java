package com.sabrepenguin.techreborn.items;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.TechReborn;
import net.minecraft.item.Item;

public class ItemBase extends Item {

    public ItemBase(String name) {
        super();
        setTranslationKey("techreborn." + name);
        setRegistryName(Tags.MODID, name);
        setCreativeTab(TechReborn.RESOURCE_TAB);
    }
}
