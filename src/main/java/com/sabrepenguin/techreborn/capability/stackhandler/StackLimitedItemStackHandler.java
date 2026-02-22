package com.sabrepenguin.techreborn.capability.stackhandler;

import net.minecraftforge.items.ItemStackHandler;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class StackLimitedItemStackHandler extends ItemStackHandler {
	private final int[] slotLimits;

	public StackLimitedItemStackHandler(int slots, int... slotLimit) {
		super(slots);
		if (slotLimit.length != slots)
			throw new IllegalArgumentException("Not the right amount of slots");
		this.slotLimits = slotLimit;
	}

	public StackLimitedItemStackHandler(int slots, List<Pair<Integer, Integer>> slotLimits) {
		super(slots);
		int total = 0;
		for (Pair<Integer, Integer> s: slotLimits) {
			total += s.getLeft();
		}
		if (total != slots)
			throw new IllegalArgumentException("Total calculated slot count " + total + " does not equal number of slots given " + slots);
		this.slotLimits = new int[slots];
		int index = 0;
		for (Pair<Integer, Integer> s: slotLimits) {
			for(int i = 0; i < s.getLeft(); i++) {
				this.slotLimits[index] = s.getRight();
				index++;
			}
		}
	}

	@Override
	public int getSlotLimit(int slot) {
		return slotLimits[slot];
	}
}
