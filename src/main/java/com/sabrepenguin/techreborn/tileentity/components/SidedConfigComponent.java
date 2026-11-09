package com.sabrepenguin.techreborn.tileentity.components;

import com.sabrepenguin.techreborn.capability.ISlotChange;
import com.sabrepenguin.techreborn.tileentity.manager.IManager;

public class SidedConfigComponent<T extends IManager<W>, W extends ISlotChange> {
	protected final T iManager;
	protected final Runnable updateTE;

	public SidedConfigComponent(T iManager, Runnable updateTE) {
		this.iManager = iManager;
		this.updateTE = updateTE;
	}

	public void rotateSlot(int sideIndex, int handlerIndex, int localSlotIndex) {
		if (iManager.getSide(sideIndex).rotateSlot(handlerIndex, localSlotIndex)) {
			updateTE();
		}
	}

	public void swapSlot(int index, boolean input) {
		iManager.swapIndex(index, input);
		updateTE();
	}

	protected void updateTE() {
		updateTE.run();
	}

	public IManager<W> getManager() {
		return iManager;
	}
}
