package com.sabrepenguin.techreborn.items;

import com.sabrepenguin.techreborn.TechReborn;
import net.minecraft.item.Item;

public abstract class ItemBase extends Item {

    public ItemBase() {
        setNoRepair();
        setCreativeTab(TechReborn.RESOURCE_TAB);
    }

    public String[] getTypes() {
        return new String[]{};
    }

    public String getPrefix() {
        return "";
    }

    public String getPostfix() {
        return "";
    }

    public void registerOredict() {}
}
