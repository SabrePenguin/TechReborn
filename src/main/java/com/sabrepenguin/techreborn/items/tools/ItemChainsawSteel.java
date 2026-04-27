package com.sabrepenguin.techreborn.items.tools;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.config.TechRebornConfig;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;

public class ItemChainsawSteel extends ItemChainsaw {
	public ItemChainsawSteel() {
		super(ToolMaterial.IRON, "ironchainsaw", 50, 100, TechRebornConfig.itemConfig.chainsaws.ironChainsawMaxEnergy, 0.5f);
	}

	@Override
	public boolean hasResourceLocation() {
		return true;
	}

	@Override
	public ResourceLocation getResourceLocation() {
		return new ResourceLocation(Tags.MODID, "steel_chainsaw");
	}

	@Override
	public boolean canHarvestBlock(IBlockState blockIn) {
		return Items.IRON_AXE.canHarvestBlock(blockIn);
	}
}
