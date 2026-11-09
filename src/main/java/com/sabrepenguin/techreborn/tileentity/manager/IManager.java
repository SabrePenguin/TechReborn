package com.sabrepenguin.techreborn.tileentity.manager;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IManager<T> {
    T getSide(EnumFacing facing);
    T getSide(int facing);
    void swapIndex(int index, boolean input);
    boolean getCurrentValue(int index, boolean input);
    boolean performTransfer(World world, BlockPos pos);
    NBTTagCompound writeToNBT();
    void readFromNBT(NBTTagCompound compound);
}
