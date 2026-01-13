package com.sabrepenguin.techreborn.capability;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.energy.IEnergyStorage;

public class CustomEnergyStorage implements IEnergyStorage {
    private final int capacity;
    private final int maxReceive;
    private final int maxExtract;
    private final ItemStack stack;

    public CustomEnergyStorage(ItemStack stack, int capacity, int maxIn, int maxOut) {
        this(stack, capacity, maxIn, maxOut, 0);
    }

    public CustomEnergyStorage(ItemStack stack, int capacity, int maxIn, int maxOut, int energy) {
        this.stack = stack;
        this.capacity = capacity;
        this.maxExtract = maxOut;
        this.maxReceive = maxIn;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        if (!canReceive())
            return 0;

        int energy = getEnergyStored();
        int energyReceived = Math.min(capacity - energy, Math.min(this.maxReceive, maxReceive));
        if (!simulate) {
            this.setEnergyStored(energy + energyReceived);
        }
        return 0;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        if (!canExtract())
            return 0;
        int energy = getEnergyStored();
        int energyExtracted = Math.min(energy, Math.min(this.maxExtract, maxExtract));
        if (!simulate) {
            this.setEnergyStored(energy - energyExtracted);
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

    @Override
    public int getEnergyStored() {
        if (stack.hasTagCompound()) {
            return stack.getTagCompound().getInteger("Energy");
        }
        return 0;
    }

    public void setEnergyStored(int energy) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        stack.getTagCompound().setInteger("Energy", energy);
    }
}
