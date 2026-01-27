package com.sabrepenguin.techreborn.capability.stackhandler;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class LimitedItemStackHandler implements IItemHandlerModifiable {

	private final List<SlotType> slots;
	private final IItemHandlerModifiable itemHandler;

	public LimitedItemStackHandler(IItemHandlerModifiable itemHandler, SlotType... slots) {
		this.itemHandler = itemHandler;
		this.slots = Arrays.asList(slots);
	}

	@Override
	public int getSlots() {
		return itemHandler.getSlots();
	}

	@Override
	public @NotNull ItemStack getStackInSlot(int slot) {
		return itemHandler.getStackInSlot(slot);
	}

	@Override
	public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
		if (!isItemValid(slot, stack)) return stack;
		return itemHandler.insertItem(slot, stack, simulate);
	}

	@Override
	public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
		return itemHandler.extractItem(slot, amount, simulate);
	}

	@Override
	public int getSlotLimit(int slot) {
		return itemHandler.getSlotLimit(slot);
	}

	@Override
	public boolean isItemValid(int slot, @NotNull ItemStack stack) {
		if (slot < 0 || slot >= slots.size()) return true;
		return switch (slots.get(slot)) {
			case ANY -> true;
			case OUTPUT -> false;
		};
	}

	@Override
	public void setStackInSlot(int slot, @NotNull ItemStack stack) {
		itemHandler.setStackInSlot(slot, stack);
	}
}
