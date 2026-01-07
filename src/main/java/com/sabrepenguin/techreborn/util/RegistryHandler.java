package com.sabrepenguin.techreborn.util;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.blocks.TRBlocks;
import com.sabrepenguin.techreborn.items.ItemBase;
import com.sabrepenguin.techreborn.items.TRItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Arrays;

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
        Arrays.stream(TRBlocks.getBlocks())
                .map(block -> new ItemBlock(block).setRegistryName(block.getRegistryName()))
                .forEach(event.getRegistry()::register);
    }

    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(TRBlocks.getBlocks());
    }
}
