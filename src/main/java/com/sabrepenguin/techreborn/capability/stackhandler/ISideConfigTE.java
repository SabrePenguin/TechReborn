package com.sabrepenguin.techreborn.capability.stackhandler;

import com.sabrepenguin.techreborn.tileentity.MachineIOManager;
import com.sabrepenguin.techreborn.tileentity.components.SidedConfigComponent;

public interface ISideConfigTE {
	default void rotateSlot(int sideIndex, int handlerIndex, int localSlotIndex) {}
	default void swapSlot(int index, boolean input) {}
	default void updateTE() {}
	default SidedConfigComponent getSidedConfigComponent() {
		return null;
	}
	MachineIOManager getManager();
}
