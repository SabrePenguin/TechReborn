package com.sabrepenguin.techreborn.blocks;

import com.sabrepenguin.techreborn.Tags;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@GameRegistry.ObjectHolder(Tags.MODID)
public class TRBlocks {
    public static final Block storage = null;

    public static Block[] getBlocks() {
        final Block[] blocks = {
                new BlockStorage()
        };

        return blocks;
    }
    public static List<Block> getAllBlocks() {
        List<Block> allBlocks = new ArrayList<>();
        Collections.addAll(allBlocks, TRBlocks.storage);
        return allBlocks;
    }
}
