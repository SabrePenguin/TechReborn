package com.sabrepenguin.techreborn.capability.energy;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.energy.CapabilityEnergy;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PoweredItemCapabilityProvider implements ICapabilityProvider {
	SettableEnergyStorage storage;

	public PoweredItemCapabilityProvider(ItemStack stack, int capacity, int maxReceive, int maxExtract) {
		this.storage = new SettableEnergyStorage(stack, capacity, maxReceive, maxExtract);
	}

	@Override
	public boolean hasCapability(@NotNull Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityEnergy.ENERGY;
	}

	@Override
	public @Nullable <T> T getCapability(@NotNull Capability<T> capability, @Nullable EnumFacing facing) {
		if (capability == CapabilityEnergy.ENERGY) {
			return CapabilityEnergy.ENERGY.cast(this.storage);
		}
		return null;
	}
}
