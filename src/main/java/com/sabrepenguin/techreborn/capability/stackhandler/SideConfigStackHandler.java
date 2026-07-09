package com.sabrepenguin.techreborn.capability.stackhandler;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SideConfigStackHandler extends CombinedInvWrapper {

	private final SlotAction[] handlerDirection;
	private final SlotAction[] slotDirection;

	public SideConfigStackHandler(SideConfig... configs) {
		super(getHandlers(configs));
		this.handlerDirection = new SlotAction[configs.length];
		for(int i = 0; i < configs.length; i++) {
			this.handlerDirection[i] = configs[i].action();
		}

		int totalSlots = this.getSlots();
		this.slotDirection = new SlotAction[totalSlots];
		for(int i = 0; i < totalSlots; i++) {
			this.slotDirection[i] = SlotAction.DISABLED;
		}
	}

	private static IItemHandlerModifiable[] getHandlers(SideConfig... configs) {
		IItemHandlerModifiable[] handlers = new IItemHandlerModifiable[configs.length];
		for (int i = 0; i < configs.length; i++) {
			handlers[i] = configs[i].handler();
		}
		return handlers;
	}

	public boolean rotateSlot(int handlerIndex, int slot) {
		if (slot < 0 || slot >= getSlots()) return false;
		this.slotDirection[slot] = this.handlerDirection[handlerIndex].rotate(this.slotDirection[slot]);
		return true;
	}

	public SlotAction getSlotAction(int slot) {
		if (slot < 0 || slot >= getSlots()) return SlotAction.DISABLED;
		return this.slotDirection[slot];
	}

	public SlotAction getHandlerAction(int slot) {
		if (slot < 0 || slot >= getSlots()) return SlotAction.DISABLED;
		return this.handlerDirection[slot];
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		if (slotDirection[slot] != SlotAction.INPUT) {
			return stack;
		}
		return super.insertItem(slot, stack, simulate);
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		if (slotDirection[slot] != SlotAction.INPUT) {
			return ItemStack.EMPTY;
		}
		return super.extractItem(slot, amount, simulate);
	}

	public NBTTagCompound writeToNbt() {
		NBTTagCompound compound = new NBTTagCompound();
		NBTTagList list = new NBTTagList();
		for (SlotAction action: this.slotDirection) {
			list.appendTag(new NBTTagByte(action.getByte()));
		}
		compound.setTag("SlotConfig", list);
		return compound;
	}

	public void readFromNbt(NBTTagCompound compound) {
		NBTTagList list = compound.getTagList("SlotConfig", 1);
		for (int i = 0; i < list.tagCount(); i++) {
			if (i < slotDirection.length) {
				slotDirection[i] = SlotAction.getByIndex(((NBTTagByte) list.get(i)).getByte());
			}
		}
	}

	public boolean runTransfer(World world, BlockPos pos, EnumFacing facing, boolean[] autoInput, boolean[] autoOutput) {
		TileEntity adjacentTE = world.getTileEntity(pos.offset(facing));
		if (adjacentTE == null || !adjacentTE.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing.getOpposite()))
			return false;
		IItemHandler adjacentInventory = adjacentTE.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing.getOpposite());
		if (adjacentInventory == null) // Theoretically never null
			return false;
		boolean changedInventory = false;

		for (int slot = 0; slot < getSlots(); slot++) {
			boolean autoIn = autoInput[slot];
			boolean autoOut = autoOutput[slot];
			SlotAction action = slotDirection[slot];

			if (autoIn && action == SlotAction.INPUT) {
				for (int adjSlot = 0; adjSlot < adjacentInventory.getSlots(); adjSlot++) {
					ItemStack adjStack = adjacentInventory.getStackInSlot(adjSlot);
					if (!adjStack.isEmpty()) {
						ItemStack extractedStack = adjacentInventory.extractItem(adjSlot, adjStack.getMaxStackSize(), true);
						if (!extractedStack.isEmpty()) {
							ItemStack remainder = super.insertItem(slot, extractedStack, true);
							int amountPulled = extractedStack.getCount() - remainder.getCount();
							if (amountPulled > 0) {
								adjacentInventory.extractItem(adjSlot, amountPulled, false);
								super.insertItem(slot, extractedStack.copy().splitStack(amountPulled), false);
								changedInventory = true;
								break;
							}
						}
					}
				}
			}
			if (autoOut && action == SlotAction.OUTPUT) {
				ItemStack stackInSlot = super.getStackInSlot(slot);
				if (!stackInSlot.isEmpty()) {
					ItemStack extracted = super.extractItem(slot, stackInSlot.getMaxStackSize(), true);
					if (!extracted.isEmpty()) {
						ItemStack remainder = ItemHandlerHelper.insertItemStacked(adjacentInventory, extracted, false);
						int amountPushed = extracted.getCount() - remainder.getCount();
						if (amountPushed > 0) {
							super.extractItem(slot, amountPushed, false);
							changedInventory = true;
						}
					}
				}
			}
		}

		return changedInventory;
	}
}
