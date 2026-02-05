package com.sabrepenguin.techreborn.blocks.machines.tier1;

import com.sabrepenguin.techreborn.blocks.machines.HorizontalMachineBlock;

public class BlockRollingMachine extends HorizontalMachineBlock {
	public BlockRollingMachine() {
		super("rolling_machine");
	}

	@Override
	public String getPrefix() {
		return "machines/tier1";
	}
}
