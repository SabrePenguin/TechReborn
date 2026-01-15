package com.sabrepenguin.techreborn.blocks;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.blocks.machines.IronFurnace;
import com.sabrepenguin.techreborn.blocks.meta.BlockStorage;
import com.sabrepenguin.techreborn.blocks.meta.BlockStorage2;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@GameRegistry.ObjectHolder(Tags.MODID)
public class TRBlocks {
    public static final Block storage = null;
    public static final Block storage2 = null;
    public static final IronFurnace iron_furnace = null;

    public static Block[] getBlocks() {
        final Block[] blocks = {
                new BlockStorage(),
                new BlockStorage2(),
                new IronFurnace(),
        };

        return blocks;
    }
    public static List<Block> getAllBlocks() {
        List<Block> allBlocks = new ArrayList<>();
        Collections.addAll(allBlocks,
                TRBlocks.storage,
                TRBlocks.storage2,
                TRBlocks.iron_furnace
        );
        return allBlocks;
    }
}
