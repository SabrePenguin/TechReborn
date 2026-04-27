package com.sabrepenguin.techreborn.util;

import com.sabrepenguin.techreborn.capability.energy.SettableEnergyStorage;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
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

	public static void getFullAndEmptySubItems(Item item, NonNullList<ItemStack> items, int capacity) {
		ItemStack empty = new ItemStack(item);
		setEnergy(empty, 0);
		items.add(empty);
		ItemStack full = new ItemStack(item);
		if (full.hasCapability(CapabilityEnergy.ENERGY, null)) {
			IEnergyStorage storage = full.getCapability(CapabilityEnergy.ENERGY, null);
			if (storage != null) {
				setEnergy(full, capacity);
				items.add(full);
			}
		}
	}

	public static void setEnergy(ItemStack stack, int energy) {
		if (stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
			IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
			if (storage instanceof SettableEnergyStorage energyStorage) {
				energyStorage.setEnergy(energy);
			}
		}
	}

	public static boolean checkIsActive(ItemStack stack) {
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("active")) {
			return stack.getTagCompound().getBoolean("active");
		}
		return false;
	}

	public static void setActive(ItemStack stack, boolean active) {
		if (stack.hasTagCompound()) {
			stack.getTagCompound().setBoolean("active", active);
			return;
		}
		NBTTagCompound compound = new NBTTagCompound();
		compound.setBoolean("active", active);
		stack.setTagCompound(compound);
	}

}
