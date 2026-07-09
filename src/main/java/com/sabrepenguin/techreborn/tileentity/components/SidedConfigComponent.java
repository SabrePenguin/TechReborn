package com.sabrepenguin.techreborn.tileentity.components;

import com.sabrepenguin.techreborn.tileentity.IOManager;

public class SidedConfigComponent {
	protected final ItemIOComponent ioComponent;
	protected final Runnable updateTE;

	public SidedConfigComponent(ItemIOComponent ioComponent, Runnable updateTE) {
		this.ioComponent = ioComponent;
		this.updateTE = updateTE;
	}

	public void rotateSlot(int sideIndex, int handlerIndex, int localSlotIndex) {
		if (ioComponent.getManager().getSide(sideIndex).rotateSlot(handlerIndex, localSlotIndex)) {
			updateTE();
		}
	}

	public void swapSlot(int index, boolean input) {
		ioComponent.getManager().swapIndex(index, input);
		updateTE();
	}

	protected void updateTE() {
		updateTE.run();
	}

	public IOManager getManager() {
		return ioComponent.ioManager;
	}
}
