package com.sabrepenguin.techreborn.gui;

import com.github.bsideup.jabel.Desugar;
import com.sabrepenguin.techreborn.capability.stackhandler.SlotAction;

@Desugar
public record SlotPosition(SlotAction action, int x, int y, int handlerSlot, int slot, int size) {
	public SlotPosition(SlotAction action, int x, int y, int handlerSlot, int slot) {
		this(action, x, y, handlerSlot, slot, 18);
	}

	public static SlotPosition BigSlot(SlotAction action, int x, int y, int handlerSlot, int slot) {
		return new SlotPosition(action, x, y, handlerSlot, slot, 26);
	}
}
