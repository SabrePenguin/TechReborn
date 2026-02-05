package com.sabrepenguin.techreborn.blocks.machines.tier1;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.TechReborn;
import com.sabrepenguin.techreborn.util.INonStandardLocation;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockAutoCraftingTable extends Block implements INonStandardLocation {
	public BlockAutoCraftingTable() {
		super(Material.IRON);
		this.setCreativeTab(TechReborn.RESOURCE_TAB);
		this.setRegistryName(Tags.MODID, "auto_crafting_table");
		this.setTranslationKey(Tags.MODID + ".auto_crafting_table");
		this.setHardness(2.0f);
		this.setSoundType(SoundType.METAL);
	}

	@Override
	public String getPrefix() {
		return "machines/tier1";
	}
}
