package com.sabrepenguin.techreborn.blocks;

import com.sabrepenguin.techreborn.TechReborn;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockBase extends Block {
    public BlockBase(Material material) {
        super(material);
        setCreativeTab(TechReborn.RESOURCE_TAB);
    }

    public String[] getTypes() {
        return new String[]{};
    }

    public String getPrefix() {
        return "";
    }

    public String getPostfix() {
        return "";
    }

    public void registerOredict() {}
}
