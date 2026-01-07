package com.sabrepenguin.techreborn.blocks;

import com.sabrepenguin.techreborn.Tags;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Tags.MODID)
public class TRBlocks {
    public static final Block ingot_block = null;

    public static Block[] getBlocks() {
        final Block[] blocks = {
                new BlockBase(Material.ROCK, "basic_block", "basic_block")
        };

        return blocks;
    }
}
