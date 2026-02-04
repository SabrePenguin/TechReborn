package com.sabrepenguin.techreborn.blocks.machines.tier3;

import com.sabrepenguin.techreborn.blocks.machines.HorizontalMachineBlock;

public class BlockFluidReplicator extends HorizontalMachineBlock {
	public BlockFluidReplicator() {
		super("fluid_replicator");
	}

	@Override
	public String getPrefix() {
		return "machines/tier3";
	}
}
