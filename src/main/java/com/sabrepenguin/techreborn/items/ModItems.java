package com.sabrepenguin.techreborn.items;

import com.sabrepenguin.techreborn.Tags;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Tags.MODID)
public class ModItems {
    public static final Item ingot = null;

    public static Item[] getItems() {
        final Item[] items = {
                new ItemBase("ingot")
        };

        return items;
    }
}
