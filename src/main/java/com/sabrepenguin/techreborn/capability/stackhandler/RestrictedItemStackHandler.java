package com.sabrepenguin.techreborn.capability.stackhandler;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.jetbrains.annotations.NotNull;

public class RestrictedItemStackHandler implements IItemHandler {

	private final IItemHandler inventory;
	private final int minSlot;
	private final int size;

	public RestrictedItemStackHandler(IItemHandler inventory, int slot) {
		this(inventory, slot, slot+1);
	}

	/**
	 * Creates a stack handler that limits views into the given inventory.
	 * <br>
	 * Note: Access should NOT be treated as offset, but normal.
	 *
	 * <pre><code>
	 * RestrictedItemStackHandler example = new RestrictedItemStackHandler(example, 0, 2); // gives two slots, 0 and 1.
	 * RestrictedItemStackHandler example2 = new RestrictedItemStackHandler(example, 2, 3); // gives only one slot, 2.
	 * example.getStackInSlot(3); // Will return EMPTY
	 * example2.getStackInSlot(0); // Will return example's stack in slot 2
	 * </code></pre>
	 *
	 * @param inventory The target ItemStackHandler
	 * @param minSlot The minimum slot (inclusive)
	 * @param maxSlot The maximum slot (exclusive)
	 */
	public RestrictedItemStackHandler(IItemHandler inventory, int minSlot, int maxSlot) {
		this.inventory = inventory;
		this.minSlot = minSlot;
		this.size = maxSlot - minSlot;
	}

	@Override
	public int getSlots() {
		return size;
	}

	@Override
	public @NotNull ItemStack getStackInSlot(int slot) {
		if (slot < 0 || slot >= size) return ItemStack.EMPTY;
		return inventory.getStackInSlot(minSlot + slot);
	}

	@Override
	public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
		if (slot < 0 || slot >= size) return stack;
		return inventory.insertItem(minSlot + slot, stack, simulate);
	}

	@Override
	public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
		if (slot < 0 || slot >= size) return ItemStack.EMPTY;
		return inventory.extractItem(minSlot + slot, amount, simulate);
	}

	@Override
	public int getSlotLimit(int slot) {
		return inventory.getSlotLimit(minSlot + slot);
	}
}
