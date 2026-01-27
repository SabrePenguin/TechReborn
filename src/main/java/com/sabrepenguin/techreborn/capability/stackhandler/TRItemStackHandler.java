package com.sabrepenguin.techreborn.capability.stackhandler;

import com.sabrepenguin.techreborn.tileentity.IChangedTileEntity;
import net.minecraftforge.items.ItemStackHandler;

public class TRItemStackHandler extends ItemStackHandler {

	private final IChangedTileEntity te;

	public TRItemStackHandler(int slots, IChangedTileEntity te) {
		super(slots);
		this.te = te;
	}
}
