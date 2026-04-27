package com.sabrepenguin.techreborn.items.battery;

import com.sabrepenguin.techreborn.capability.energy.PoweredItemCapabilityProvider;
import com.sabrepenguin.techreborn.capability.energy.SettableEnergyStorage;
import com.sabrepenguin.techreborn.items.ItemHelper;
import com.sabrepenguin.techreborn.util.INonStandardLocation;
import com.sabrepenguin.techreborn.util.ItemStackUtils;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ItemBattery extends Item implements INonStandardLocation {
	protected final int capacity;
	protected final int maxTransfer;

	@SuppressWarnings("ConstantConditions")
	public ItemBattery(String name, int capacity, int maxTransfer) {
		ItemHelper.registerUnstackable(this, name);
		this.setHasSubtypes(true);
		this.capacity = capacity;
		this.maxTransfer = maxTransfer;
		this.addPropertyOverride(new ResourceLocation("techreborn:empty_power"), (stack, worldIn, entityIn) -> {
			if (!stack.isEmpty() && stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
				IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
				return storage.getEnergyStored() != 0 ? 0 : 1;
			}
			return 0;
		});
	}

	public void setEnergy(ItemStack stack, int energy) {
		if (stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
			IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
			if (storage instanceof SettableEnergyStorage energyStorage) {
				energyStorage.setEnergy(energy);
			}
		}
	}

	@Override
	public String getPrefix() {
		return "battery";
	}

	// Item

	@Override
	public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
		return new PoweredItemCapabilityProvider(stack, capacity, maxTransfer, maxTransfer);
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab)) {
			ItemStackUtils.getFullAndEmptySubItems(this, items, capacity);
		}
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		return ItemStackUtils.getItemStackDurabilityForDisplay(stack);
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return true;
	}
}
