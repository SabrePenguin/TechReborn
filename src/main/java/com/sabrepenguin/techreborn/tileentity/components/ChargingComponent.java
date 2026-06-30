package com.sabrepenguin.techreborn.tileentity.components;

import com.sabrepenguin.techreborn.capability.energy.TEEnergyStorage;
import com.sabrepenguin.techreborn.util.ItemStackUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandlerModifiable;

import java.util.function.Consumer;

public class ChargingComponent implements MachineComponent {
	protected final IItemHandlerModifiable inputs;
	protected final TEEnergyStorage energyStorage;
	protected final Consumer<Boolean> stateUpdater;

	protected boolean isActive;

	public ChargingComponent(IItemHandlerModifiable inputs, TEEnergyStorage energyStorage, Consumer<Boolean> stateUpdater) {
		this.inputs = inputs;
		this.energyStorage = energyStorage;
		this.stateUpdater = stateUpdater;
		isActive = false;
	}

	public void onUpdatePacket(World world, BlockPos pos, NBTTagCompound compound) {
		boolean previousState = isActive;
		readFromNBT(compound);
		if (world != null && world.isRemote && previousState != isActive) {
			world.markBlockRangeForRenderUpdate(pos, pos);
		}
	}

	@Override
	public boolean update() {
		if (energyStorage.getEnergyStored() <= 0) return false;
		boolean isDirty = false;
		boolean active = false;
		for (int slot = 0; slot < inputs.getSlots(); slot++) {
			ItemStack simulated = inputs.getStackInSlot(slot);
			if (!simulated.isEmpty()) {
				ItemStack stack = inputs.extractItem(slot, inputs.getSlotLimit(slot), false);
				active = ItemStackUtils.chargeItem(energyStorage, stack);
				isDirty = active;
				inputs.insertItem(slot, stack, false);
			}
		}


		if (active != isActive) {
			isActive = active;
			updateState();
			isDirty = true;
		}

		return isDirty;
	}

	protected void updateState() {
		stateUpdater.accept(isActive);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		isActive = compound.getBoolean("IsActive");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setBoolean("IsActive", isActive);
		return compound;
	}
}
