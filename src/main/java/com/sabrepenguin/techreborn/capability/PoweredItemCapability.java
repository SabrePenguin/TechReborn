package com.sabrepenguin.techreborn.capability;

import com.sabrepenguin.techreborn.items.IEnergy;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PoweredItemCapability implements ICapabilityProvider {

    IEnergyStorage energy;

    public PoweredItemCapability(final ItemStack stack, IEnergy energy) {
        this.energy = new CustomEnergyStorage(stack, energy.getCapacity(), energy.getInputTransfer(), energy.getOutputTransfer());
    }

    @Override
    public boolean hasCapability(@NotNull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityEnergy.ENERGY;
    }

    @Override
    public @Nullable <T> T getCapability(@NotNull Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) {
            return CapabilityEnergy.ENERGY.cast(this.energy);
        }
        return null;
    }
}
