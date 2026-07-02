package com.sabrepenguin.techreborn.tileentity.components.specialized;

import com.cleanroommc.modularui.value.sync.DoubleSyncValue;
import com.sabrepenguin.techreborn.capability.energy.TEEnergyStorage;
import com.sabrepenguin.techreborn.tileentity.components.MachineComponent;
import com.sabrepenguin.techreborn.tileentity.components.RecipeComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandlerModifiable;

import java.util.Random;
import java.util.function.Consumer;

public class RandomRecipeComponent implements MachineComponent, RecipeComponent {
	private static final Random random = new Random();
	protected final int chance;
	protected static ItemStack recipeOutput;
	protected final IItemHandlerModifiable inputs;
	protected final IItemHandlerModifiable internalOutputs;
	protected final TEEnergyStorage energyStorage;
	protected final Consumer<Boolean> stateUpdater;

	protected int processTime;
	protected int totalProcessTime;
	protected int energyCost;

	protected float cachedEnergyMultiplier;

	protected boolean isActive;
	protected boolean refreshRecipe;

	public RandomRecipeComponent(IItemHandlerModifiable inputs, IItemHandlerModifiable internalOutput,
								 TEEnergyStorage storage, Consumer<Boolean> stateUpdate, ItemStack output,
								 int chance) {
		isActive = false;
		this.inputs = inputs;
		this.internalOutputs = internalOutput;
		this.energyStorage = storage;
		this.stateUpdater = stateUpdate;
		cachedEnergyMultiplier = 1;
		recipeOutput = output;
		this.chance = chance;
		energyCost = 1;
		totalProcessTime = 1;
	}

	public void onUpdatePacket(World world, BlockPos pos, NBTTagCompound compound) {
		boolean previousState = isActive;
		readFromNBT(compound);
		if (world != null && world.isRemote && previousState != isActive) {
			world.markBlockRangeForRenderUpdate(pos, pos);
		}
	}

	@SuppressWarnings("ConstantConditions")
	protected void updateState() {
		stateUpdater.accept(isActive);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("Processed", processTime);
		compound.setBoolean("IsActive", isActive);
		return compound;
	}

	public void informRefreshRecipe() {
		refreshRecipe = true;
	}

	@Override
	public void updateMultipliers(float processMultiplier, float energyMultiplier) {
		this.cachedEnergyMultiplier = energyMultiplier;
	}

	public int getProcessTime() {
		return processTime;
	}

	public DoubleSyncValue getProgress() {
		return new DoubleSyncValue(() -> (double) processTime / totalProcessTime,
				value -> processTime = (int) (value * totalProcessTime));
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		processTime = compound.getInteger("Processed");
		isActive = compound.getBoolean("IsActive");
	}

	@Override
	public boolean update() {
		boolean isDirty = false;
		boolean active = false;
		if (canProcess()) {
			if (energyStorage.internalExtract(energyCost, true) == energyCost) {
				energyStorage.internalExtract(energyCost, false);
				processTime++;
				active = true;
			}
			if (processTime >= totalProcessTime) {
				processItem();
				processTime = 0;
				isDirty = true;
			}
		}
		if (processTime > 0) {
			processTime = 0;
			isDirty = true;
		}

		if (active != isActive) {
			isActive = active;
			isDirty = true;
			updateState();
		}
		return isDirty;
	}

	protected boolean canProcess() {
		ItemStack input = this.inputs.getStackInSlot(0);
		if (input.isEmpty()) return false;
		ItemStack output = this.internalOutputs.getStackInSlot(0);
		if (output.isEmpty()) return true;
		if (!output.isItemEqual(recipeOutput)) return false;
		int res = output.getCount() + recipeOutput.getCount();
		return res <= this.internalOutputs.getSlotLimit(0) && res <= output.getMaxStackSize();
	}

	protected void processItem() {
		if (internalOutputs.getStackInSlot(0).getCount() >= internalOutputs.getStackInSlot(0).getMaxStackSize())
			return;
		if (random.nextInt(chance) == 0) {
			this.internalOutputs.insertItem(0, recipeOutput.copy(), false);
		}
		inputs.extractItem(0, 1, false);
	}
}
