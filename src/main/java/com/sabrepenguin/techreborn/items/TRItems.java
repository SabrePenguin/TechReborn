package com.sabrepenguin.techreborn.items;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.items.materials.ItemDust;
import com.sabrepenguin.techreborn.items.materials.ItemDustSmall;
import com.sabrepenguin.techreborn.items.materials.ItemIngot;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Tags.MODID)
public class TRItems {

    public static final Item ingot = null;
    public static final Item dust = null;
    public static final Item smalldust = null;

    public static Item[] getItems() {
        final Item[] items = {
                new ItemIngot(),
                new ItemDust(),
                new ItemDustSmall(),
        };

        return items;
    }
}
