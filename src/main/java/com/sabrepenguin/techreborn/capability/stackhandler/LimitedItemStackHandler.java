package com.sabrepenguin.techreborn.capability.stackhandler;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class LimitedItemStackHandler implements IItemHandlerModifiable {

	private final List<SlotAction> slots;
	private final IItemHandlerModifiable itemHandler;
	private Predicate<ItemStack> filter;

	public LimitedItemStackHandler(IItemHandlerModifiable itemHandler, SlotAction... slots) {
		this.itemHandler = itemHandler;
		this.slots = Arrays.asList(slots);
	}

	@Override
	public int getSlots() {
		return itemHandler.getSlots();
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return itemHandler.getStackInSlot(slot);
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		if (!isItemValid(slot, stack)) return stack;
		return itemHandler.insertItem(slot, stack, simulate);
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		return itemHandler.extractItem(slot, amount, simulate);
	}

	@Override
	public int getSlotLimit(int slot) {
		return itemHandler.getSlotLimit(slot);
	}

	@Override
	public boolean isItemValid(int slot, ItemStack stack) {
		if (slot < 0 || slot >= slots.size()) return true;
		if (stack.isEmpty()) return true;
		if (!this.filter.test(stack)) return false;
		return switch (slots.get(slot)) {
			case INPUT, BIDIRECTIONAL -> true;
			case OUTPUT, DISABLED -> false;
		};
	}

	@Override
	public void setStackInSlot(int slot, ItemStack stack) {
		itemHandler.setStackInSlot(slot, stack);
	}

	public LimitedItemStackHandler setFilter(@Nullable Predicate<ItemStack> filter) {
		this.filter = filter != null ? filter : stack -> true;
		return this;
	}
}
