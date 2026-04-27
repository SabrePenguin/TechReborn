package com.sabrepenguin.techreborn.items.tools;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.config.TechRebornConfig;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;

public class ItemChainsawDiamond extends ItemChainsaw {
	public ItemChainsawDiamond() {
		super(ToolMaterial.IRON, "diamondchainsaw", 250, 1000, TechRebornConfig.itemConfig.chainsaws.diamondChainsawMaxEnergy, 1f);
	}



	@Override
	public boolean hasResourceLocation() {
		return true;
	}

	@Override
	public ResourceLocation getResourceLocation() {
		return new ResourceLocation(Tags.MODID, "diamond_chainsaw");
	}

	@Override
	public boolean canHarvestBlock(IBlockState blockIn) {
		return Items.DIAMOND_AXE.canHarvestBlock(blockIn);
	}
}
