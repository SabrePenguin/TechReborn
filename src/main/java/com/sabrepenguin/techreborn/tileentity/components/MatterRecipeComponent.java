package com.sabrepenguin.techreborn.tileentity.components;

import com.cleanroommc.modularui.value.sync.DoubleSyncValue;
import com.sabrepenguin.techreborn.capability.energy.TEEnergyStorage;
import com.sabrepenguin.techreborn.config.TechRebornConfig;
import com.sabrepenguin.techreborn.recipe.RegistryHandler;
import com.sabrepenguin.techreborn.recipe.registries.MatterFabricatorRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandlerModifiable;

import java.util.function.Consumer;

public class MatterRecipeComponent implements MachineComponent, RecipeComponent {
	private static final MatterFabricatorRegistry registry = RegistryHandler.instance().getMatterFabricatorRegistry();

	protected final IItemHandlerModifiable inputs;
	protected final IItemHandlerModifiable outputs;
	protected final TEEnergyStorage energyStorage;
	protected final Consumer<Boolean> stateUpdater;

	protected final int fabricationRate;
	protected final int totalFabrication;
	protected int processed;
	protected boolean isActive;

	public MatterRecipeComponent(IItemHandlerModifiable inputs, IItemHandlerModifiable internalOutputs,
								 TEEnergyStorage energyStorage, Consumer<Boolean> stateUpdate, int fabricationRate) {
		this.inputs = inputs;
		this.outputs = internalOutputs;
		this.energyStorage = energyStorage;
		this.stateUpdater = stateUpdate;
		this.fabricationRate = fabricationRate;
		totalFabrication = TechRebornConfig.machineConfig.matterFabricator.fabricationRate;
		processed = 0;
	}

	public void onUpdatePacket(World world, BlockPos pos, NBTTagCompound compound) {
		boolean previousState = isActive;
		readFromNBT(compound);
		if (world != null && world.isRemote && previousState != isActive) {
			world.markBlockRangeForRenderUpdate(pos, pos);
		}
	}

	@Override
	public boolean update() {
		boolean isDirty = false;
		boolean active = false;
		if (processed < fabricationRate) {
			for (int i = 0; i < inputs.getSlots(); i++) {
				ItemStack fakeItem = inputs.extractItem(i, 1, true);
				if (!fakeItem.isEmpty()) {
					final int amp = registry.getItemValue(fakeItem);
					if (amp > 0) {
						final int feNeed = amp * TechRebornConfig.machineConfig.matterFabricator.energyPerAmp;
						int usedEnergy = energyStorage.internalExtract(feNeed, true);
						if (usedEnergy >= feNeed) {
							inputs.extractItem(i, 1, false);
							energyStorage.internalExtract(feNeed, false);
							processed += amp;
							active = true;
							isDirty = true;
						}
					}
				}
			}
		}
		int slotToOutput = canProcess();
		if (processed >= TechRebornConfig.machineConfig.matterFabricator.fabricationRate && slotToOutput >= 0) {
			processed -= TechRebornConfig.machineConfig.matterFabricator.fabricationRate;
			outputs.insertItem(slotToOutput, registry.getOutput(), false);
			isDirty = true;
			active = true;
		}

		if (stateUpdater != null && active != isActive) {
			isActive = active;
			isDirty = true;
			updateState();
		}
		return isDirty;
	}

	protected void updateState() {
		stateUpdater.accept(isActive);
	}

	private int canProcess() {
		for (int i = 0; i < outputs.getSlots(); i++) {
			ItemStack out = outputs.getStackInSlot(i);
			if (out.isEmpty())
				return i;
			if (!out.isItemEqual(registry.getOutput()))
				continue;
			int result = out.getCount() + 1;
			if (result <= outputs.getSlotLimit(i) && result <= registry.getOutput().getMaxStackSize())
				return i;
		}
		return -1;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		processed = compound.getInteger("Processed");
		isActive = compound.getBoolean("IsActive");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("Processed", processed);
		compound.setBoolean("IsActive", isActive);
		return compound;
	}

	@Override
	public void updateMultipliers(float processMultiplier, float energyMultiplier) {
	}

	public DoubleSyncValue getProgress() {
		return new DoubleSyncValue(() -> (double) processed / totalFabrication,
				value -> processed = (int) (value * totalFabrication));
	}
}
