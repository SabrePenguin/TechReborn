package com.sabrepenguin.techreborn.tileentity;

import com.sabrepenguin.techreborn.capability.stackhandler.SideConfig;
import com.sabrepenguin.techreborn.capability.stackhandler.SideConfigStackHandler;
import com.sabrepenguin.techreborn.capability.stackhandler.SlotAction;
import com.sabrepenguin.techreborn.tileentity.manager.IManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandlerModifiable;

public class IOManager implements IManager<SideConfigStackHandler> {
	private final SideConfigStackHandler[] sides;

	private final boolean[] autoInput;
	private final boolean[] autoOutput;

	private int cycle;

	@SafeVarargs
    public IOManager(SideConfig<IItemHandlerModifiable>... configs) {
		sides = new SideConfigStackHandler[6];
		for (int i = 0; i < 6; i++) {
			sides[i] = new SideConfigStackHandler(configs);
		}
		int totalSlots = sides[0].getSlots();

		autoInput = new boolean[totalSlots];
		autoOutput = new boolean[totalSlots];
		cycle = 0;
	}

	public SideConfigStackHandler getSide(EnumFacing facing) {
		return sides[facing.getIndex()];
	}

	public SideConfigStackHandler getSide(int facing) {
		return sides[facing];
	}

	public SlotAction getHandlerActionType(int slot) {
		return sides[0].getHandlerAction(slot);
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
			SideConfigStackHandler side = sides[facing.getIndex()];
			if (side == null) continue;
			if (side.runTransfer(world, pos, facing, autoInput, autoOutput)) {
				changed = true;
			}
		}
		return changed;
	}

	@Override
	public NBTTagCompound writeToNBT() {
		NBTTagCompound compound = new NBTTagCompound();
		NBTTagList list = new NBTTagList();
		for (SideConfigStackHandler side : sides) {
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

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		NBTTagList list = compound.getTagList("Slots", 10);
		for(int i = 0; i < list.tagCount(); i++) {
			if (i < sides.length) {
				sides[i].readFromNbt(list.getCompoundTagAt(i));
			}
		}
		expandBits(compound.getIntArray("Input"), autoInput);
		expandBits(compound.getIntArray("Output"), autoOutput);
	}

	private void expandBits(int[] data, boolean[] array) {
		for(int i = 0; i < array.length; i++) {
			int index = i / 32;
			int bit = i % 32;
			if (index < array.length) {
				array[i] = (data[index] & (1 << bit)) != 0;
			}
		}
	}
}
