package com.sabrepenguin.techreborn.items;

import com.sabrepenguin.techreborn.TechReborn;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.Collection;

public abstract class ItemBase extends Item {

    public ItemBase() {
        setCreativeTab(TechReborn.RESOURCE_TAB);
    }

    public String[] getTypes() {
        return new String[]{};
    }

    public Collection<MetadataItem> getItems() {
        return new ArrayList<>();
    }

    public String getPrefix() {
        return "";
    }

    public String getPostfix() {
        return "";
    }

    public void registerOredict() {}
}
