package com.sabrepenguin.techreborn.blocks.machines;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.util.INonStandardLocation;

public class BlockComputerCube extends HorizontalMachineBlock implements INonStandardLocation {
	public BlockComputerCube() {
		super();
		this.setRegistryName(Tags.MODID, "computercube");
		this.setTranslationKey(Tags.MODID + ".computercube");
	}

	@Override
	public String getPrefix() {
		return "machines/tier2/";
	}
}
