package com.sabrepenguin.techreborn.tileentity.components;

import com.sabrepenguin.techreborn.capability.energy.TEEnergyStorage;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nullable;

public class EnergyComponent implements MachineComponent {

	protected final TEEnergyStorage energyStorage;
	protected final IItemHandlerModifiable battery;

	private final int baseCapacity;
	private final int baseInsert;
	private final int baseExtract;

	public EnergyComponent(int capacity, int maxInsert, int maxExtract) {
		this(capacity, maxInsert, maxExtract, null);
	}

	public EnergyComponent(int capacity, int maxInsert, int maxExtract, @Nullable IItemHandlerModifiable battery) {
		energyStorage = new TEEnergyStorage(capacity, maxInsert, maxExtract);
		this.battery = battery;
		this.baseCapacity = capacity;
		this.baseInsert = maxInsert;
		this.baseExtract = maxExtract;
	}

	public TEEnergyStorage getEnergyStorage() {
		return energyStorage;
	}

	@Override
	public boolean update() {
		if (battery == null) return false;

		ItemStack energyItem = battery.extractItem(0, 1, true);
		if (!energyItem.isEmpty() && energyItem.hasCapability(CapabilityEnergy.ENERGY, null)) {
			IEnergyStorage itemStorage = energyItem.getCapability(CapabilityEnergy.ENERGY, null);
			int simExtract = itemStorage.extractEnergy(energyStorage.getMaxInput(), true);
			int simInsert = energyStorage.receiveEnergy(simExtract, true);
			if (simInsert > 0) {
				ItemStack realEnergyItem = battery.extractItem(0, 1, false);
				IEnergyStorage realStorage = realEnergyItem.getCapability(CapabilityEnergy.ENERGY, null);
				int actualExtract = realStorage.extractEnergy(simInsert, false);
				energyStorage.receiveEnergy(actualExtract, false);
				battery.insertItem(0, realEnergyItem, false);
				return true;
			}
		}

		return false;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		if (compound.hasKey("maxEnergy"))
			energyStorage.setMaxEnergy(compound.getInteger("maxEnergy"));
		if (compound.hasKey("energy"))
			energyStorage.setEnergy(compound.getInteger("energy"));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("energy", energyStorage.getEnergyStored());
		if (energyStorage.getMaxEnergyStored() != baseCapacity) {
			compound.setInteger("maxEnergy", energyStorage.getMaxEnergyStored());
		} else {
			compound.setInteger("maxEnergy", baseCapacity);
		}
		return compound;
	}

	public void updateEnergy(float transferMultiplier, int capacity) {
		energyStorage.setMaxExtract((int) (baseExtract * transferMultiplier));
		energyStorage.setMaxReceive((int) (baseInsert * transferMultiplier));
		energyStorage.setMaxEnergy(capacity);
	}
}
