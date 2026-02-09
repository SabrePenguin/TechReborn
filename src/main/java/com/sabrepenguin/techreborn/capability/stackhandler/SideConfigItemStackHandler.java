package com.sabrepenguin.techreborn.capability.stackhandler;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This ItemStackHandler wraps multiple ItemStacks as one. Individual ItemStackHandlers are passed into the builder,
 * which then defaults to adding all slots to the available slots. Internally the handlers are organized as follows:
 * <br>
 * IntArrayList inputSlots: Maps available slots (ie. slot 0 -> actual slot 1, slot 1 -> actual slot 2, ....) <br>
 * IntArrayList inputSlotLength: Individual lengths of each input slot handler. ie. if all slots are available, then
 * the value at an index is equal to the number of slots in the handler. <br>
 * List&ltIItemHandlerModifiable&gt inputHandler: Set of all inputHandlers <br>
 * IntArrayList outputSlots: See inputSlots <br>
 * IItemHandlerModifiable outputHandler: The outputHandler. Only one is available due to the lack of different
 * requirements.<br>
 * Current logical issue: inputsSlots need to be a list of lists
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SideConfigItemStackHandler implements IItemHandlerModifiable {
	private final IntArrayList inputSlots;
	private final IntArrayList inputSlotLength;
	private final List<IItemHandlerModifiable> inputHandler;
	private final IntArrayList outputSlots;
	private final IItemHandlerModifiable outputHandler;

	SideConfigItemStackHandler(
			IntArrayList inputSlots,
			List<IItemHandlerModifiable> inputHandler,
			IntArrayList inputSlotLengths,
			IntArrayList outputSlots,
			IItemHandlerModifiable outputHandler
	) {
		this.inputSlots = inputSlots;
		this.inputHandler = inputHandler;
		this.inputSlotLength = inputSlotLengths;
		this.outputSlots = outputSlots;
		this.outputHandler = outputHandler;
	}

	public void addSlot(IItemHandlerModifiable handler, int slot) {

	}

	public void removeSlot(IItemHandlerModifiable handler, int slot) {

	}

	/**
	 * The actual equation for the slot. I should probably write a proof at some point.
	 */
	private int getActualSlot(int slot) {
		int total = 0;
		// The current handlers slot. At total 0, slot 7-0 gives slot 7. If slot handler 0 has size 3, this means
		// that i is 3. The loop resets, but total is now 3. If slot handler 1 has size 5, then slot 7-3 gives actual
		// slot 4, the last slot in it.
		// If the slot were instead 8, this would result instead in actual slot 5. The total is thus 8. If all inputs
		// are emptied, then we return the slot minus the total at 8 - 8 for actual slot 0.
		for(Integer handlerSize: inputSlotLength) {
			int actualSlot = slot - total;
			if (actualSlot < handlerSize)
				return actualSlot;
			total += handlerSize;
		}
		return slot - total;
	}

	/**
	 * Gets the handler from the slot
	 * @param slot Slot in the range of this
	 * @return The actual IItemHandlerModifiable that contains the slot
	 */
	private IItemHandlerModifiable actualHandler(int slot) {
		int total = 0;
		for(int i = 0; i < inputSlotLength.size(); i++) {
			int handlerSize = inputSlotLength.getInt(i);
			if (slot - total < handlerSize) {
				return inputHandler.get(i);
			}
			total += handlerSize;
		}
		return outputHandler;
	}

	@Override
	public void setStackInSlot(int slot, ItemStack stack) {
		actualHandler(slot).setStackInSlot(getActualSlot(slot), stack);
	}

	@Override
	public int getSlots() {
		return inputSlots.size() + outputSlots.size();
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return actualHandler(slot).getStackInSlot(getActualSlot(slot));
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		return actualHandler(slot).insertItem(getActualSlot(slot), stack, simulate);
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		return actualHandler(slot).extractItem(getActualSlot(slot), amount, simulate);
	}

	@Override
	public int getSlotLimit(int slot) {
		return actualHandler(slot).getSlotLimit(getActualSlot(slot));
	}

	public static class Builder {
		List<IItemHandlerModifiable> inputs;
		IItemHandlerModifiable output;

		public Builder addItemHandler(IItemHandlerModifiable handler) {
			this.inputs.add(handler);
			return this;
		}

		public Builder addOutput(IItemHandlerModifiable handler) {
			output = handler;
			return this;
		}

		public SideConfigItemStackHandler build() {
			IntArrayList inputLengths = new IntArrayList(inputs.size());
			int length = 0;
			for (IItemHandlerModifiable handler: inputs) {
				inputLengths.add(handler.getSlots());
				length += handler.getSlots();
			}
			return new SideConfigItemStackHandler(
					new IntArrayList(IntStream.range(0, length).boxed().collect(Collectors.toList())),
					inputs,
					inputLengths,
					new IntArrayList(IntStream.range(0, output.getSlots()).boxed().collect(Collectors.toList())),
					output
			);
		}
	}
}
