package com.sabrepenguin.techreborn.blocks;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.TechReborn;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockBase extends Block {
    public BlockBase(Material material, String translationKey, String registryName) {
        this(material, SoundType.STONE, translationKey, registryName);
    }

    public BlockBase(Material material, SoundType sound, String translationKey, String registryName) {
        super(material);
        setTranslationKey("techreborn." + translationKey);
        setRegistryName(Tags.MODID, registryName);
        setCreativeTab(TechReborn.RESOURCE_TAB);
        setSoundType(sound);
    }
}
