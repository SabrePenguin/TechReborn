package com.sabrepenguin.techreborn.capability.energy;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.energy.IEnergyStorage;

public class SettableEnergyStorage implements IEnergyStorage {
	protected final ItemStack stack;
	protected final int capacity;
	protected final int maxReceive;
	protected final int maxExtract;

	public SettableEnergyStorage(ItemStack itemStack, int capacity, int maxReceive, int maxExtract) {
		this(itemStack, capacity, maxReceive, maxExtract, 0);
	}

	public SettableEnergyStorage(ItemStack stack, int capacity, int maxReceive, int maxExtract, int energy) {
		this.capacity = capacity;
		this.maxReceive = maxReceive;
		this.maxExtract = maxExtract;
		this.stack = stack;
		if (energy != 0)
			writeToNbt(energy);
	}

	public void setEnergy(int energy) {
		energy = Math.min(this.capacity, Math.max(0, energy));
		writeToNbt(energy);
	}

	@SuppressWarnings("ConstantConditions")
	private void writeToNbt(int energy) {
		if (!stack.hasTagCompound()) {
			NBTTagCompound compound = new NBTTagCompound();
			stack.setTagCompound(compound);
		}
		stack.getTagCompound().setInteger("energy", energy);
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		if (!canReceive())
			return 0;
		int energy = getEnergyStored();
		int energyReceived = Math.min(capacity - energy, Math.min(this.maxReceive, maxReceive));
		if (!simulate && energyReceived > 0)
			setEnergy(energy + energyReceived);
		return energyReceived;
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		if (!canExtract())
			return 0;
		int energy = getEnergyStored();
		int energyExtracted = Math.min(energy, Math.min(this.maxExtract, maxExtract));
		if (!simulate && energyExtracted > 0)
			setEnergy(energy - energyExtracted);
		return energyExtracted;
	}

	@Override
	@SuppressWarnings("ConstantConditions")
	public int getEnergyStored() {
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("energy")) {
			return stack.getTagCompound().getInteger("energy");
		}
		return 0;
	}

	@Override
	public int getMaxEnergyStored() {
		return capacity;
	}

	@Override
	public boolean canExtract() {
		return maxExtract > 0;
	}

	@Override
	public boolean canReceive() {
		return maxReceive > 0;
	}
}
