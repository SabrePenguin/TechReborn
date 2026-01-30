package com.sabrepenguin.techreborn.items;

import com.sabrepenguin.techreborn.TechReborn;
import net.minecraft.item.Item;

public abstract class ItemBase extends Item {

    public ItemBase() {
        setCreativeTab(TechReborn.RESOURCE_TAB);
    }

    public void registerOredict() {}
}
