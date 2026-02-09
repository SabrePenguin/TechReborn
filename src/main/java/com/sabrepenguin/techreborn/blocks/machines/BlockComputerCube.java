package com.sabrepenguin.techreborn.blocks.machines;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.util.INonStandardLocation;

public class BlockComputerCube extends HorizontalMachineBlock implements INonStandardLocation {
	public BlockComputerCube() {
		super("computercube");
	}

	@Override
	public String getPrefix() {
		return "machines/tier2/";
	}
}
