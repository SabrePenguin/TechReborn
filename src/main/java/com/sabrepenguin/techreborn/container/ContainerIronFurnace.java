package com.sabrepenguin.techreborn.container;

import com.sabrepenguin.techreborn.tileentity.tier0.TileEntityIronFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class ContainerIronFurnace extends Container {
	private final TileEntityIronFurnace tileEntityIronFurnace;
	private int cookTime;
	private int totalCookTime;
	private int burnTime;
	private int currentBurnTime;

	public ContainerIronFurnace(InventoryPlayer playerInventory, TileEntityIronFurnace furnace) {
		IItemHandler inventory = furnace.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		this.tileEntityIronFurnace = furnace;
		this.addSlotToContainer(new SlotItemHandler(inventory, 0, 56, 17) {
			@Override
			public boolean isItemValid(@NotNull ItemStack stack) {
				IItemHandler handler = this.getItemHandler();
				if (stack.isEmpty() || !handler.isItemValid(0, stack))
					return false;
				ItemStack remainder = handler.insertItem(0, stack, true);
				return remainder.getCount() < stack.getCount();
			}
		});
		this.addSlotToContainer(new SlotItemHandler(inventory, 1, 56, 53));
		this.addSlotToContainer(new SlotItemHandler(inventory, 2, 116, 35) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return false;
			}
		});

		for (int y = 0; y < 3; ++y)
		{
			for (int x = 0; x < 9; ++x)
			{
				this.addSlotToContainer(new Slot(playerInventory, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
			}
		}

		for (int h = 0; h < 9; ++h)
		{
			this.addSlotToContainer(new Slot(playerInventory, h, 8 + h * 18, 142));
		}

	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return !tileEntityIronFurnace.isInvalid() && playerIn.getDistanceSq(tileEntityIronFurnace.getPos()) <= 64.0;
	}

	@Override
	public void addListener(IContainerListener listener) {
		super.addListener(listener);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (IContainerListener listener: listeners) {
			if (burnTime != tileEntityIronFurnace.getBurnTime()) {
				listener.sendWindowProperty(this, 0, tileEntityIronFurnace.getBurnTime());
			}
			if (cookTime != tileEntityIronFurnace.getCookTime()) {
				listener.sendWindowProperty(this, 1, tileEntityIronFurnace.getCookTime());
			}
			if (totalCookTime != tileEntityIronFurnace.getTotalCookTime()) {
				listener.sendWindowProperty(this, 2, tileEntityIronFurnace.getTotalCookTime());
			}
			if (currentBurnTime != tileEntityIronFurnace.getCurrentBurnTime()) {
				listener.sendWindowProperty(this, 3, tileEntityIronFurnace.getCurrentBurnTime());
			}
		}

		cookTime = tileEntityIronFurnace.getCookTime();
		burnTime = tileEntityIronFurnace.getBurnTime();
		totalCookTime = tileEntityIronFurnace.getTotalCookTime();
		currentBurnTime = tileEntityIronFurnace.getCurrentBurnTime();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) {
		switch (id) {
			case 0:
				tileEntityIronFurnace.setBurnTime(data);
				break;
			case 1:
				tileEntityIronFurnace.setCookTime(data);
				break;
			case 2:
				tileEntityIronFurnace.setTotalCookTime(data);
				break;
			case 3:
				tileEntityIronFurnace.setCurrentBurnTime(data);
				break;
		}
	}

	@Override
	public @NotNull ItemStack transferStackInSlot(@NotNull EntityPlayer playerIn, int index) {
		ItemStack original = ItemStack.EMPTY;
		Slot slot = inventorySlots.get(index);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemInSlot = slot.getStack();
			original = itemInSlot.copy();
			if (index == 2) { // Output
				if (!this.mergeItemStack(itemInSlot, 3, 39, true)) {
					return ItemStack.EMPTY;
				}
				slot.onSlotChange(itemInSlot, original);
			}
			else if (index != 1 && index != 0) {
				if (!FurnaceRecipes.instance().getSmeltingResult(itemInSlot).isEmpty()) {
					if (!this.mergeItemStack(itemInSlot, 0, 1, false)) {
						return ItemStack.EMPTY;
					}
				}
				else if (TileEntityFurnace.isItemFuel(itemInSlot)) {
					if (!this.mergeItemStack(itemInSlot, 1, 2, false)) {
						return ItemStack.EMPTY;
					}
				}
				else if (index >= 3 && index < 30) {
					if (!this.mergeItemStack(itemInSlot, 30, 39, false)) {
						return ItemStack.EMPTY;
					}
				}
				else if (index >= 30 && index < 39 && !this.mergeItemStack(itemInSlot, 3, 30, false)) {
					return ItemStack.EMPTY;
				}
			}
			else if (!this.mergeItemStack(itemInSlot, 3, 39, false)) {
				return ItemStack.EMPTY;
			}

			if (itemInSlot.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			}
			else {
				slot.onSlotChanged();
			}

			if (itemInSlot.getCount() == original.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(playerIn, itemInSlot);
		}
		return original;
	}
}
