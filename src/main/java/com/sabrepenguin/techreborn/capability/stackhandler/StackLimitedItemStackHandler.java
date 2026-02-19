package com.sabrepenguin.techreborn.capability.stackhandler;

import net.minecraftforge.items.ItemStackHandler;

public class StackLimitedItemStackHandler extends ItemStackHandler {
	private final int[] slotLimits;

	public StackLimitedItemStackHandler(int slots, int... slotLimit) {
		super(slots);
		if (slotLimit.length != slots)
			throw new IllegalArgumentException("Not the right amount of slots");
		this.slotLimits = slotLimit;
	}

	@Override
	public int getSlotLimit(int slot) {
		return slotLimits[slot];
	}
}
