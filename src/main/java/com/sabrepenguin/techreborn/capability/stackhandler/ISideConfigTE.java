package com.sabrepenguin.techreborn.capability.stackhandler;

public interface ISideConfigTE {
	void rotateSlot(int sideIndex, int handlerIndex, int localSlotIndex);
	void swapSlot(int index, boolean input);
}
