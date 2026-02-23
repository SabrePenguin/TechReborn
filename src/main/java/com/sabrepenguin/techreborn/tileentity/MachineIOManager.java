package com.sabrepenguin.techreborn.tileentity;

import com.sabrepenguin.techreborn.capability.stackhandler.SideConfig;
import com.sabrepenguin.techreborn.capability.stackhandler.SideConfigItemStackHandler;
import com.sabrepenguin.techreborn.capability.stackhandler.SlotAction;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class MachineIOManager {
	private final SideConfigItemStackHandler[] sides;

	private final boolean[] autoInput;
	private final boolean[] autoOutput;

	private int cycle;

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
		cycle = 0;
	}

	public SideConfigItemStackHandler getSide(EnumFacing facing) {
		return sides[facing.getIndex()];
	}

	public SideConfigItemStackHandler getSide(int facing) {
		return sides[facing];
	}

	public SlotAction getHandlerActionType(int handlerIndex) {
		return sides[0].getHandlerAction(handlerIndex);
	}

	public int getRealIndex(int handlerIndex, int realIndex) {
		return sides[0].getRealSlot(handlerIndex, realIndex);
	}

	public void swapIndex(int index, boolean input) {
		if (input) {
			autoInput[index] = !autoInput[index];
		} else {
			autoOutput[index] = !autoOutput[index];
		}
	}

	public boolean getCurrentValue(int index, boolean input) {
		return input ? autoInput[index] : autoOutput[index];
	}

	public boolean performTransfer(World world, BlockPos pos) {
		if (cycle++ < 20)
			return false;
		cycle = 0;
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

	public NBTTagCompound writeToNBT() {
		NBTTagCompound compound = new NBTTagCompound();
		NBTTagList list = new NBTTagList();
		for (SideConfigItemStackHandler side : sides) {
			list.appendTag(side.writeToNbt());
		}
		compound.setTag("Slots", list);
		compound.setTag("Input", compactBits(autoInput));
		compound.setTag("Output", compactBits(autoOutput));
		return compound;
	}

	private NBTTagIntArray compactBits(boolean[] array) {
		int bit = 0;
		int index = 0;
		int value = 0;
		int[] storedData = new int[(array.length + 31) / 32];
		for (boolean b : array) {
			if (b) {
				value |= (1 << bit);
			}
			bit++;
			if (bit == 32) {
				storedData[index] = value;
				value = 0;
				bit = 0;
				index++;
			}
		}
		if (bit > 0) {
			storedData[index] = value;
		}
		return new NBTTagIntArray(storedData);
	}

	public void readFromNBT(NBTTagCompound compound) {
		NBTTagList list = compound.getTagList("Slots", 10);
		for(int i = 0; i < list.tagCount(); i++) {
			sides[i].readFromNbt(list.getCompoundTagAt(i));
		}
		expandBits(compound.getIntArray("Input"), autoInput);
		expandBits(compound.getIntArray("Output"), autoOutput);
	}

	private void expandBits(int[] data, boolean[] array) {
		for(int i = 0; i < array.length; i++) {
			int index = i / 32;
			int bit = i % 32;
			array[i] = (data[index] & (1 << bit)) != 0;
		}
	}
}
