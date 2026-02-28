package com.sabrepenguin.techreborn.capability.stackhandler;

import com.github.bsideup.jabel.Desugar;
import net.minecraftforge.items.IItemHandlerModifiable;

@Desugar
public record SideConfig(IItemHandlerModifiable handler, SlotAction action) {
	public static SideConfig input(IItemHandlerModifiable handler) {
		return new SideConfig(handler, SlotAction.INPUT);
	}

	public static SideConfig output(IItemHandlerModifiable handler) {
		return new SideConfig(handler, SlotAction.OUTPUT);
	}

	public static SideConfig bidirectional(IItemHandlerModifiable handler) {
		return new SideConfig(handler, SlotAction.BIDIRECTIONAL);
	}
}
