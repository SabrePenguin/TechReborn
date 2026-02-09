package com.sabrepenguin.techreborn.blocks;

import com.sabrepenguin.techreborn.blocks.machines.HorizontalMachineBlock;

public class BlockFusionControlComputer extends HorizontalMachineBlock {
	public BlockFusionControlComputer() {
		super("fusion_control_computer");
	}

	@Override
	public String getPrefix() {
		return "machines/generators";
	}
}
