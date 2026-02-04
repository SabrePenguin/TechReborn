package com.sabrepenguin.techreborn.blocks;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.TechReborn;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;

public class BlockRefinedIronFence extends BlockFence {
	public BlockRefinedIronFence() {
		super(Material.IRON, Material.IRON.getMaterialMapColor());
		this.setRegistryName(Tags.MODID, "refined_iron_fence");
		this.setTranslationKey(Tags.MODID + ".refined_iron_fence");
		this.setCreativeTab(TechReborn.RESOURCE_TAB);
		this.setHardness(2.0f);
		this.setHarvestLevel("pickaxe", 2);
	}
}
