package com.sabrepenguin.techreborn.capability.stackhandler;

import com.github.bsideup.jabel.Desugar;
import net.minecraftforge.items.IItemHandlerModifiable;

@Desugar
public record SideConfig(IItemHandlerModifiable handler, SlotAction action) {

	public enum SlotAction {
		INPUT,
		OUTPUT,
		BIDIRECTIONAL
	}
}
