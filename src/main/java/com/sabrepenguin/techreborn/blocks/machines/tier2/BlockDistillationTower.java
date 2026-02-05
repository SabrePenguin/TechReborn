package com.sabrepenguin.techreborn.blocks.machines.tier2;

import com.sabrepenguin.techreborn.blocks.machines.HorizontalMachineBlock;

public class BlockDistillationTower extends HorizontalMachineBlock {
	public BlockDistillationTower() {
		super("distillation_tower");
	}

	@Override
	public String getPrefix() {
		return "machines/tier2";
	}
}
