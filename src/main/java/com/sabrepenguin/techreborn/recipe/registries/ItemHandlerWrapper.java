package com.sabrepenguin.techreborn.recipe.registries;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ItemHandlerWrapper extends InventoryCrafting {
	private final IItemHandler handler;

	public ItemHandlerWrapper(IItemHandler handler) {
		super(new Container() {
			@Override
			public boolean canInteractWith(EntityPlayer playerIn) {
				return true;
			}
		}, 3, 3);
		this.handler = handler;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		ItemStack stack = handler.getStackInSlot(index);
		return stack.isEmpty() ? ItemStack.EMPTY : stack.copy();
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return ItemStack.EMPTY;
	}

	public ItemStack removeStackFromSlot(int index) {
		return ItemStack.EMPTY;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
	}

	@Override
	public void clear() {
	}

	@Override
	public boolean isEmpty() {
		for (int i = 0; i < handler.getSlots(); i++) {
			if (!handler.getStackInSlot(i).isEmpty())
				return false;
		}
		return true;
	}
}
