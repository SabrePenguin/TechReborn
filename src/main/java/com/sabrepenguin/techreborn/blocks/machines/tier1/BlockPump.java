package com.sabrepenguin.techreborn.blocks.machines.tier1;

import com.sabrepenguin.techreborn.blocks.machines.HorizontalMachineBlock;

public class BlockPump extends HorizontalMachineBlock {
	public BlockPump() {
		super("pump");
	}

	@Override
	public String getPrefix() {
		return "machines/tier1";
	}
}
