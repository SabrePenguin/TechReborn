package com.sabrepenguin.techreborn.blocks;

import com.sabrepenguin.techreborn.TechReborn;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public abstract class BlockBase extends Block {
    public BlockBase(Material material) {
        super(material);
        setCreativeTab(TechReborn.RESOURCE_TAB);
    }

    public void registerOredict() {}
}
