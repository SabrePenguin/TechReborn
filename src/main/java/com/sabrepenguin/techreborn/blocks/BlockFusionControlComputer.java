package com.sabrepenguin.techreborn.blocks;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.TechReborn;
import com.sabrepenguin.techreborn.blocks.machines.HorizontalMachineBlock;

public class BlockFusionControlComputer extends HorizontalMachineBlock {
	public BlockFusionControlComputer() {
		super();
		this.setCreativeTab(TechReborn.RESOURCE_TAB);
		this.setRegistryName(Tags.MODID, "fusion_control_computer");
		this.setTranslationKey(Tags.MODID + ".fusion_control_computer");
	}

	@Override
	public String getPrefix() {
		return "machines/generators";
	}
}
