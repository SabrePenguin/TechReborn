package com.sabrepenguin.techreborn.capability.stackhandler;

import com.sabrepenguin.techreborn.tileentity.IChangedTileEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public class TRItemStackHandler extends ItemStackHandler {

	private final IChangedTileEntity te;

	public TRItemStackHandler(int slots, IChangedTileEntity te) {
		super(slots);
		this.te = te;
	}

	@Override
	public void setStackInSlot(int slot, @NotNull ItemStack stack) {
		validateSlotIndex(slot);
		ItemStack oldSlot = getStackInSlot(slot);
		this.stacks.set(slot, stack);
		fireOnContentsChanged(slot, oldSlot, getStackInSlot(slot));
	}

	@Override
	public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
		if (stack.isEmpty())
			return ItemStack.EMPTY;

		validateSlotIndex(slot);

		ItemStack existing = this.stacks.get(slot);

		int limit = getStackLimit(slot, stack);

		if (!existing.isEmpty())
		{
			if (!ItemHandlerHelper.canItemStacksStack(stack, existing))
				return stack;

			limit -= existing.getCount();
		}

		if (limit <= 0)
			return stack;

		boolean reachedLimit = stack.getCount() > limit;

		if (!simulate)
		{
			if (existing.isEmpty())
			{
				this.stacks.set(slot, reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, limit) : stack);
				fireOnContentsChanged(slot, ItemStack.EMPTY, getStackInSlot(slot));
			}
			else
			{
				existing.grow(reachedLimit ? limit : stack.getCount());
			}
		}

		return reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, stack.getCount()- limit) : ItemStack.EMPTY;
	}

	@Override
	public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
		if (amount == 0)
			return ItemStack.EMPTY;

		validateSlotIndex(slot);

		ItemStack existing = this.stacks.get(slot);

		if (existing.isEmpty())
			return ItemStack.EMPTY;

		int toExtract = Math.min(amount, existing.getMaxStackSize());

		if (existing.getCount() <= toExtract)
		{
			if (!simulate)
			{
				this.stacks.set(slot, ItemStack.EMPTY);
				fireOnContentsChanged(slot, existing, ItemStack.EMPTY);
			}
			return existing;
		}
		else
		{
			if (!simulate)
			{
				this.stacks.set(slot, ItemHandlerHelper.copyStackWithSize(existing, existing.getCount() - toExtract));
			}

			return ItemHandlerHelper.copyStackWithSize(existing, toExtract);
		}
	}

	protected void fireOnContentsChanged(int slot, ItemStack removedStack, ItemStack addedStack) {
		if (te != null) {
			te.onInputChanged(slot, removedStack, addedStack);
		}
	}
}
