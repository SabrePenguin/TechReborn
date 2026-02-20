package com.sabrepenguin.techreborn.capability;

import net.minecraft.util.math.MathHelper;
import net.minecraftforge.energy.EnergyStorage;

public class NbtEnergyStorage extends EnergyStorage implements IEnergyInformation {

	public NbtEnergyStorage(int capacity) {
		super(capacity);
	}

	public NbtEnergyStorage(int capacity, int maxTransfer) {
		super(capacity, maxTransfer);
	}

	public NbtEnergyStorage(int capacity, int maxReceive, int maxExtract) {
		super(capacity, maxReceive, maxExtract);
	}

	public NbtEnergyStorage(int capacity, int maxReceive, int maxExtract, int energy) {
		super(capacity, maxReceive, maxExtract, energy);
	}

	public void setEnergy(int energy) {
		energy = MathHelper.clamp(energy, 0, capacity);
		this.energy = energy;
	}

	public int getMaxInput() {
		return this.maxReceive;
	}

	public int getMaxOutput() {
		return this.maxExtract;
	}
}
