package com.sabrepenguin.techreborn.blocks.machines.tier3;

import com.sabrepenguin.techreborn.blocks.machines.HorizontalMachineBlock;

public class BlockChunkLoader extends HorizontalMachineBlock {
	public BlockChunkLoader() {
		super("chunk_loader");
	}

	@Override
	public String getPrefix() {
		return "machines/tier3";
	}
}
