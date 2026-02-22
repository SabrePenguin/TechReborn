package com.sabrepenguin.techreborn.tileentity;

import com.sabrepenguin.techreborn.capability.stackhandler.SideConfig;
import com.sabrepenguin.techreborn.capability.stackhandler.SideConfigItemStackHandler;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MachineIOManager {
	private final SideConfigItemStackHandler[] sides;

	private final boolean[] autoInput;
	private final boolean[] autoOutput;

	public MachineIOManager(SideConfig... handlers) {
		sides = new SideConfigItemStackHandler[6];
		for (int i = 0; i < 6; i++) {
			sides[i] = new SideConfigItemStackHandler(handlers);
		}
		int total = 0;
		for (SideConfig handler: handlers) {
			total += handler.handler().getSlots();
		}

		autoInput = new boolean[total];
		autoOutput = new boolean[total];
	}

	public SideConfigItemStackHandler getSide(EnumFacing facing) {
		return sides[facing.getIndex()];
	}

	public SideConfigItemStackHandler getSide(int facing) {
		return sides[facing];
	}

	private boolean performTransfer(World world, BlockPos pos) {
		boolean changed = false;
		for (EnumFacing facing: EnumFacing.values()) {
			SideConfigItemStackHandler side = sides[facing.getIndex()];
			if (side == null) continue;
			if (side.runTransfer(world, pos, facing, autoInput, autoOutput)) {
				changed = true;
			}
		}
		return changed;
	}

	public NBTTagList writeToNBT() {
		NBTTagList list = new NBTTagList();
		for (SideConfigItemStackHandler side : sides) {
			list.appendTag(side.writeToNbt());
		}
		return list;
	}

	public void readFromNBT(NBTTagList list) {
		for(int i = 0; i < list.tagCount(); i++) {
			sides[i].readFromNbt(list.getCompoundTagAt(i));
		}
	}
}
