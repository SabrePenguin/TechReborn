package com.sabrepenguin.techreborn.capability.stackhandler;

import com.sabrepenguin.techreborn.tileentity.MachineIOManager;

public interface ISideConfigTE {
	void rotateSlot(int sideIndex, int handlerIndex, int localSlotIndex);
	void swapSlot(int index, boolean input);
	void updateTE();
	MachineIOManager getManager();
}
