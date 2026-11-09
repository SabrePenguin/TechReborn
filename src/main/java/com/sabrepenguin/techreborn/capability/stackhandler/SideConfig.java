package com.sabrepenguin.techreborn.capability.stackhandler;

import com.github.bsideup.jabel.Desugar;

@Desugar
public record SideConfig<T>(T handler, SlotAction action) {
	public static <T> SideConfig<T> input(T handler) {
		return new SideConfig<>(handler, SlotAction.INPUT);
	}

	public static <T> SideConfig<T> output(T handler) {
		return new SideConfig<>(handler, SlotAction.OUTPUT);
	}

	public static <T> SideConfig<T> bidirectional(T handler) {
		return new SideConfig<>(handler, SlotAction.BIDIRECTIONAL);
	}
}
