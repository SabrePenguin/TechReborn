package com.sabrepenguin.techreborn.tileentity.components;

import com.cleanroommc.modularui.value.sync.DoubleSyncValue;
import com.sabrepenguin.techreborn.capability.energy.TEEnergyStorage;
import com.sabrepenguin.techreborn.recipe.recipe.ITRRecipe;
import com.sabrepenguin.techreborn.recipe.registries.ITRRegistry;
import com.sabrepenguin.techreborn.recipe.utils.RecipeUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class StandardRecipeComponent implements MachineComponent, RecipeComponent {

	protected final IItemHandlerModifiable inputs;
	protected final IItemHandlerModifiable internalOutputs;
	protected final TEEnergyStorage energyStorage;
	@Nullable
	protected final Consumer<Boolean> stateUpdater;

	protected int processTime;
	protected int totalProcessTime;
	protected int energyCost;

	protected float cachedProcessMultiplier;
	protected float cachedEnergyMultiplier;
	protected int cachedProcessTime;
	protected int cachedEnergyCost;

	protected final ITRRegistry registry;
	protected ITRRecipe cachedRecipe;

	protected boolean isActive;
	protected boolean refreshRecipe;

	public StandardRecipeComponent(ITRRegistry registry, IItemHandlerModifiable inputs, IItemHandlerModifiable internalOutput,
								   TEEnergyStorage storage, @Nullable Consumer<Boolean> stateUpdate) {
		this.registry = registry;
		isActive = false;
		this.inputs = inputs;
		this.internalOutputs = internalOutput;
		this.energyStorage = storage;
		this.stateUpdater = stateUpdate;
		cachedEnergyMultiplier = 1;
		cachedProcessMultiplier = 1;
		cachedRecipe = null;
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
		if (refreshRecipe) {
			refreshRecipe();
		}
		boolean active = false;
		if (cachedRecipe != null) {
			if (canProcess()) {
				if (energyStorage.internalExtract(cachedEnergyCost, true) == cachedEnergyCost) {
					energyStorage.internalExtract(cachedEnergyCost, false);
					processTime++;
					active = true;
				}
				if (processTime >= cachedProcessTime) {
					processItem();
					processTime = 0;
					isDirty = true;
				}
			}
		} else if (processTime > 0) {
			processTime = 0;
			isDirty = true;
		}

		if (stateUpdater != null && active != isActive) {
			isActive = active;
			isDirty = true;
			updateState();
		}
		return isDirty;
	}

	@SuppressWarnings("ConstantConditions")
	protected void updateState() {
		stateUpdater.accept(isActive);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		processTime = compound.getInteger("Processed");
		isActive = compound.getBoolean("IsActive");
		cachedRecipe = registry.getRecipe(inputs);
		if (cachedRecipe != null) {
			totalProcessTime = cachedRecipe.getRecipeTime();
			cachedProcessTime = (int) (totalProcessTime * cachedProcessMultiplier);
			energyCost = cachedRecipe.getEnergyCost();
			cachedEnergyCost = (int) (energyCost * cachedEnergyMultiplier);
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("Processed", processTime);
		compound.setBoolean("IsActive", isActive);
		return compound;
	}

	private void refreshRecipe() {
		refreshRecipe = false;
		if (cachedRecipe != null) {
			if (cachedRecipe.matches(inputs))
				return;
		}
		ITRRecipe newRecipe = registry.getRecipe(inputs);
		if (cachedRecipe != newRecipe) {
			cachedRecipe = newRecipe;
			if (cachedRecipe != null) {
				totalProcessTime = cachedRecipe.getRecipeTime();
				cachedProcessTime = (int) (totalProcessTime * cachedProcessMultiplier);
				energyCost = cachedRecipe.getEnergyCost();
				cachedEnergyCost = (int) (energyCost * cachedEnergyMultiplier);
			}
			processTime = 0;
		}
	}

	protected boolean canProcess() {
		if (cachedRecipe == null)
			return false;
		ItemStack output = this.internalOutputs.getStackInSlot(0);
		ItemStack recipeOutput = cachedRecipe.getOutput().get(0);
		if (!cachedRecipe.matches(inputs)) return false;
		if (output.isEmpty()) return true;
		if (!output.isItemEqual(recipeOutput)) return false;
		int res = output.getCount() + recipeOutput.getCount();
		return res <= this.internalOutputs.getSlotLimit(0) && res <= output.getMaxStackSize();
	}

	protected boolean canOutputFit() {
		List<ItemStack> cachedOutput = cachedRecipe.getOutput();
		for (int i = 0; i < cachedOutput.size(); i++) {
			ItemStack currentSlotStack = internalOutputs.getStackInSlot(i);
			ItemStack cachedOutputStack = cachedOutput.get(i);
			if (currentSlotStack.isEmpty())
				continue;
			if (currentSlotStack.isItemEqual(cachedOutputStack)) {
				if (currentSlotStack.hasTagCompound() && cachedOutputStack.hasTagCompound()) {
					if (!currentSlotStack.getTagCompound().equals(cachedOutputStack.getTagCompound())) {
						return false;
					}
				} else if (currentSlotStack.hasTagCompound() || cachedOutputStack.hasTagCompound()) {
					return false;
				}
				continue;
			}
			return false;
		}
		return true;
	}

	protected void processItem() {
		if (!canOutputFit()) {
			return;
		}
		List<ItemStack> cachedOutput = cachedRecipe.getOutput();
		for (int i = 0; i < cachedOutput.size(); i++) {
			ItemStack cachedOutputStack = cachedOutput.get(i).copy();
			internalOutputs.insertItem(i, cachedOutputStack, false);
		}
		RecipeUtils.applyRecipeToHandler(inputs, cachedRecipe.getInputs());
	}

	public void informRefreshRecipe() {
		refreshRecipe = true;
	}

	@Override
	public void updateMultipliers(float processMultiplier, float energyMultiplier) {
		this.cachedProcessMultiplier = processMultiplier;
		this.cachedEnergyMultiplier = energyMultiplier;
		this.cachedProcessTime = (int) (this.totalProcessTime * processMultiplier);
		this.cachedEnergyCost = (int) (this.energyCost * energyMultiplier);
	}

	public int getProcessTime() {
		return processTime;
	}

	public DoubleSyncValue getProgress() {
		return new DoubleSyncValue(() -> (double) processTime / cachedProcessTime,
				value -> processTime = (int) (value * cachedProcessTime));
	}

	public ITRRecipe getCachedRecipe() {
		return cachedRecipe;
	}

	public ITRRegistry getRegistry() {
		return registry;
	}
}
