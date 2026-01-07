package com.sabrepenguin.techreborn.util;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.blocks.TRBlocks;
import com.sabrepenguin.techreborn.items.TRItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;

@Mod.EventBusSubscriber(modid = Tags.MODID)
public class RegistryHandler {
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(TRItems.getItems());
        Arrays.stream(TRBlocks.getBlocks())
                .map(block -> new ItemBlock(block).setRegistryName(block.getRegistryName()))
                .forEach(event.getRegistry()::register);
    }

    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(TRBlocks.getBlocks());
    }
}
