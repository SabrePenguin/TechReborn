package com.sabrepenguin.techreborn.items.tools;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.config.TechRebornConfig;
import net.minecraft.util.ResourceLocation;

public class ItemChainsawDiamond extends ItemChainsaw {
	public ItemChainsawDiamond() {
		super(ToolMaterial.DIAMOND, "diamondchainsaw", 250, 1000, TechRebornConfig.itemConfig.chainsaws.diamondChainsawMaxEnergy, 1f);
	}



	@Override
	public boolean hasResourceLocation() {
		return true;
	}

	@Override
	public ResourceLocation getResourceLocation() {
		return new ResourceLocation(Tags.MODID, "diamond_chainsaw");
	}
}
