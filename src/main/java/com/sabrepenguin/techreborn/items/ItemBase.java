package com.sabrepenguin.techreborn.items;

import com.sabrepenguin.techreborn.Tags;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBase extends Item {

    public ItemBase(String name) {
        super();
        setTranslationKey("techreborn." + name);
        setRegistryName(Tags.MODID, name);
        setCreativeTab(CreativeTabs.MATERIALS);
    }
}
