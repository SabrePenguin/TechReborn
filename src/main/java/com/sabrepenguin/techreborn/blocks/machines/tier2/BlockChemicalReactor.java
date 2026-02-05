package com.sabrepenguin.techreborn.blocks.machines.tier2;

import com.sabrepenguin.techreborn.blocks.machines.HorizontalMachineBlock;

public class BlockChemicalReactor extends HorizontalMachineBlock {
	public BlockChemicalReactor() {
		super("chemical_reactor");
	}

	@Override
	public String getPrefix() {
		return "machines/tier2";
	}
}
