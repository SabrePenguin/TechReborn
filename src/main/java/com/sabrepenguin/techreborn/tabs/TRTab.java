package com.sabrepenguin.techreborn.tabs;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.items.TRItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class TRTab extends CreativeTabs {
    public TRTab(String name) {
        super(Tags.MODID + "." + name);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(TRItems.ingot);
    }
}
