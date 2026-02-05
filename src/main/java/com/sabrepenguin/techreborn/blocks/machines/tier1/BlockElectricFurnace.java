package com.sabrepenguin.techreborn.blocks.machines.tier1;

import com.sabrepenguin.techreborn.blocks.machines.HorizontalMachineBlock;

public class BlockElectricFurnace extends HorizontalMachineBlock {
	public BlockElectricFurnace() {
		super("electric_furnace");
	}

	@Override
	public String getPrefix() {
		return "machines/tier1";
	}
}
