package com.sabrepenguin.techreborn.util;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.blocks.BlockBase;
import com.sabrepenguin.techreborn.blocks.ItemBlockMeta;
import com.sabrepenguin.techreborn.blocks.TRBlocks;
import com.sabrepenguin.techreborn.items.ItemBase;
import com.sabrepenguin.techreborn.items.TRItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = Tags.MODID)
public class RegistryHandler {
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        for (Item item: TRItems.getItems()) {
            registry.register(item);
            if (item instanceof ItemBase baseItem) {
                baseItem.registerOredict();
            }
        }
        for (Block block: TRBlocks.getAllBlocks()) {
            registry.register(new ItemBlockMeta(block).setRegistryName(block.getRegistryName()));
            if (block instanceof BlockBase baseBlock) {
                baseBlock.registerOredict();
            }
        }
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        for (Block block: TRBlocks.getBlocks()) {
            event.getRegistry().register(block);
        }
    }
}
