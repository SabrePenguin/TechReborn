package com.sabrepenguin.techreborn.capability.stackhandler;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class StackLimitedItemStackHandler extends ItemStackHandler {
	private final int slotLimit;
	private final Runnable booleanChanger;

	public StackLimitedItemStackHandler(int slots, int slotLimit, Runnable booleanChanger) {
		super(slots);
		this.slotLimit = slotLimit;
		this.booleanChanger = booleanChanger;
	}

	@Override
	public int getSlotLimit(int slot) {
		return slotLimit;
	}

	@Override
	protected void onContentsChanged(int slot) {
		booleanChanger.run();
	}
}
