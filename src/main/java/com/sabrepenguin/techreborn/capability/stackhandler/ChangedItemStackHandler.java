package com.sabrepenguin.techreborn.capability.stackhandler;

import com.sabrepenguin.techreborn.tileentity.IChangedTileEntity;
import net.minecraftforge.items.ItemStackHandler;

public class ChangedItemStackHandler extends ItemStackHandler {

	private final IChangedTileEntity te;

	public ChangedItemStackHandler(int size, IChangedTileEntity tileEntity) {
		super(size);
		this.te = tileEntity;
	}

	@Override
	protected void onContentsChanged(int slot) {
		te.onSlotCountChanged(slot);
	}
}
