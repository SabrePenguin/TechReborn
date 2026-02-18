package com.sabrepenguin.techreborn.capability.stackhandler;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SideConfigItemStackHandler implements IItemHandlerModifiable {

	private final ObjectArrayList<IItemHandlerModifiable> handlers;
	/** An array that matches the handlers object size. ie. [4, 3, 1] for a handler of size 4, 3, and one of 1 */
	private final int[] handlerOffsets;
	/** An integer array containing sets of ints matching the handler's slot, specifically made for fast indexing
	 * of the handler.
	 * ie. [0, 0, 0, 0, 1, 1, 1, 2] represents a set of handlers. 1 has 4 slots (0-3),
	 * 2 has 3 slots (4-6), and handler 3 has 1 slot (7). Accessing the index will return the matching handler.*/
	private final IntArrayList handlerIndices;
	/** An integer array containing the slots for the handler it matches. Maps the entire handler for remapping.
	 * ie. [0, 1, 2, 3, 0, 1, 2, 0]*/
	private final IntArrayList localHandlerIndices;
	/** Maps the enabled slots from the localHandlerIndices. Used for tracking and updating the activeSlots*/
	private final boolean[] enabled;
	/** The actual slot retrieval. Contains the actual length, and each integer maps to the handlerIndices +
	 * localHandlerIndices arrays*/
	private final IntArrayList activeSlots;
	/** The set of handlers to push and pull. */
	private final SlotAction[] handlerDirection;

	public SideConfigItemStackHandler(SideConfig... handlers) {
		this.handlers = new ObjectArrayList<>(handlers.length);
		this.handlerDirection = new SlotAction[handlers.length];
		for (int i = 0; i < handlers.length; i++) {
			this.handlers.add(handlers[i].handler());
			handlerDirection[i] = handlers[i].action();
		}
		handlerOffsets = new int[this.handlers.size()];

		int totalSlots = 0;
		for (int i = 0; i < this.handlers.size(); i++) {
			handlerOffsets[i] = totalSlots;
			totalSlots += this.handlers.get(i).getSlots();
		}
		this.handlerIndices = new IntArrayList(totalSlots);
		this.localHandlerIndices = new IntArrayList(totalSlots);
		this.enabled = new boolean[totalSlots];

		int index = 0;
		for (int handlerIndex = 0; handlerIndex < this.handlers.size(); handlerIndex++) {
			IItemHandlerModifiable handler = this.handlers.get(handlerIndex);
			int handlerSize = handler.getSlots();

			for (int s = 0; s < handlerSize; s++) {
				handlerIndices.add(handlerIndex);
				localHandlerIndices.add(s);
				this.enabled[index] = true;
				index++;
			}
		}

		activeSlots = new IntArrayList(totalSlots);
		rebuildActiveSlots();
	}

	private void rebuildActiveSlots() {
		activeSlots.clear();
		for (int i = 0; i < handlerIndices.size(); i++) {
			if (enabled[i]) {
				activeSlots.add(i);
			}
		}
	}

	public boolean setSlotEnabled(int handlerIndex, int localSlotIndex, boolean enabled) {
		if (handlerIndex < 0 || handlerIndex >= handlers.size()) return false;
		int index = handlerOffsets[handlerIndex] + localSlotIndex;
		int startOfNextHandler = (handlerIndex + 1 < handlerOffsets.length) ?
				handlerOffsets[handlerIndex + 1] : handlerIndices.size();
		if (index >= startOfNextHandler) return false;
		if (this.enabled[index] != enabled) {
			this.enabled[index] = enabled;
			rebuildActiveSlots();
			return true;
		}
		return false;
	}

	public boolean getSlotEnabled(int handlerIndex, int localSlotIndex) {
		if (handlerIndex < 0 || handlerIndex >= handlers.size()) return false;
		int index = handlerOffsets[handlerIndex] + localSlotIndex;
		int startOfNextHandler = (handlerIndex + 1 < handlerOffsets.length) ?
				handlerOffsets[handlerIndex + 1] : handlerIndices.size();
		if (index >= startOfNextHandler) return false;
		return enabled[index];
	}

	@Override
	public void setStackInSlot(int slot, ItemStack stack) {
		validateSlotIndex(slot);
		int index = activeSlots.getInt(slot);
		handlers.get(handlerIndices.getInt(index)).setStackInSlot(localHandlerIndices.getInt(index), stack);
	}

	@Override
	public int getSlots() {
		return activeSlots.size();
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		validateSlotIndex(slot);
		int index = activeSlots.getInt(slot);
		return handlers.get(handlerIndices.getInt(index)).getStackInSlot(localHandlerIndices.getInt(index));
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		validateSlotIndex(slot);
		int index = activeSlots.getInt(slot);
		int handlerIndex = handlerIndices.getInt(index);
		if (handlerDirection[handlerIndex] == SlotAction.OUTPUT) {
			return stack;
		}
		return handlers.get(handlerIndex).insertItem(localHandlerIndices.getInt(index), stack, simulate);
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		validateSlotIndex(slot);
		int index = activeSlots.getInt(slot);
		int handlerIndex = handlerIndices.getInt(index);
		if (handlerDirection[handlerIndex] == SlotAction.INPUT) {
			return ItemStack.EMPTY;
		}
		return handlers.get(handlerIndex).extractItem(localHandlerIndices.getInt(index), amount, simulate);
	}

	@Override
	public int getSlotLimit(int slot) {
		validateSlotIndex(slot);
		int index = activeSlots.getInt(slot);
		return handlers.get(handlerIndices.getInt(index)).getSlotLimit(localHandlerIndices.getInt(index));
	}

	private void validateSlotIndex(int slot) {
		if (slot < 0 || slot >= activeSlots.size())
			throw new RuntimeException("Slot " + slot + " not in valid range - [0," + activeSlots.size() + ")");
	}

	public static SideConfigItemStackHandler[] createSides(SideConfig... handlers) {
		SideConfigItemStackHandler[] sides = new SideConfigItemStackHandler[6];
		for (int i = 0; i < 6; i++) {
			sides[i] = new SideConfigItemStackHandler(handlers);
		}
		return sides;
	}

	public NBTTagCompound writeToNbt() {
		NBTTagCompound compound = new NBTTagCompound();
		NBTTagList list = new NBTTagList();
		for (boolean b : enabled) {
			list.appendTag(new NBTTagByte(b ? (byte) 1 : (byte) 0));
		}
		compound.setTag("slotConfig", list);
		return compound;
	}

	public static NBTTagList writeToNbt(SideConfigItemStackHandler[] sides) {
		NBTTagList list = new NBTTagList();
		for (SideConfigItemStackHandler side : sides) {
			list.appendTag(side.writeToNbt());
		}
		return list;
	}

	public void readFromNbt(NBTTagCompound compound) {
		NBTTagList list = compound.getTagList("slotConfig", 1);
		for (int i = 0; i < list.tagCount(); i++) {
			enabled[i] = ((NBTTagByte) list.get(i)).getByte() != (byte) 0;
		}
		this.rebuildActiveSlots();
	}

	public static void readFromNbt(SideConfigItemStackHandler[] sides, NBTTagList list) {
		for(int i = 0; i < list.tagCount(); i++) {
			sides[i].readFromNbt(list.getCompoundTagAt(i));
		}
	}
}
