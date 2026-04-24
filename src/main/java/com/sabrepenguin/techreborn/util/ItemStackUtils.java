package com.sabrepenguin.techreborn.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class ItemStackUtils {

	public static double getItemStackDurabilityForDisplay(ItemStack stack) {
		if (stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
			IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
			if (storage != null) {
				double maxAmount = storage.getMaxEnergyStored();
				double energyDif = maxAmount - storage.getEnergyStored();
				return energyDif/maxAmount;
			}
		}
		return 0.0f;
	}
}
