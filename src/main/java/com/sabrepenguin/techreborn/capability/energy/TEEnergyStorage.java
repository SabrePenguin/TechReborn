package com.sabrepenguin.techreborn.capability.energy;

import com.sabrepenguin.techreborn.capability.IEnergyInformation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.energy.EnergyStorage;

public class TEEnergyStorage extends EnergyStorage implements IEnergyInformation {

	protected boolean canExtract;
	protected boolean canReceive;

	public TEEnergyStorage(int capacity) {
		this(capacity, capacity, capacity, 0);
	}

	public TEEnergyStorage(int capacity, int maxTransfer) {
		this(capacity, maxTransfer, maxTransfer, 0);
	}

	public TEEnergyStorage(int capacity, int maxReceive, int maxExtract) {
		this(capacity, maxReceive, maxExtract, 0);
	}

	public TEEnergyStorage(int capacity, int maxReceive, int maxExtract, int energy) {
		super(capacity, maxReceive, maxExtract, energy);
		this.canExtract = maxExtract > 0;
		this.canReceive = maxReceive > 0;
	}

	public void setCanExtract(boolean extract) {
		this.canExtract = extract;
	}

	public void setCanReceive(boolean receive) {
		this.canReceive = receive;
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

	@Override
	public boolean canExtract()
	{
		return this.maxExtract > 0 && canExtract;
	}

	@Override
	public boolean canReceive() {
		return this.maxReceive > 0 && canReceive;
	}

	public int internalExtract(int maxExtract, boolean simulate) {
		int energyExtracted = Math.min(energy, Math.min(this.maxExtract, maxExtract));
		if (!simulate)
			energy -= energyExtracted;
		return energyExtracted;
	}

	public int internalReceive(int maxReceive, boolean simulate) {
		int energyReceived = Math.min(capacity - energy, Math.min(this.maxReceive, maxReceive));
		if (!simulate)
			energy += energyReceived;
		return energyReceived;
	}
}
