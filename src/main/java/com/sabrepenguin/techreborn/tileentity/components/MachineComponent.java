package com.sabrepenguin.techreborn.tileentity.components;

import net.minecraft.nbt.NBTTagCompound;

public interface MachineComponent {
	/**
	 * Run the actions of the component
	 * @return If the parent should mark dirty
	 */
	default boolean update() {
		return false;
	}
	default void readFromNBT(NBTTagCompound compound) {}
	default NBTTagCompound writeToNBT(NBTTagCompound compound) {
		return compound;
	}
}
