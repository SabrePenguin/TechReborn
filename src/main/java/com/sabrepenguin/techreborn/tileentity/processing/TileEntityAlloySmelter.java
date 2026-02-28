package com.sabrepenguin.techreborn.tileentity.processing;

import com.sabrepenguin.techreborn.blocks.machines.BlockHorizontalMachine;
import com.sabrepenguin.techreborn.capability.stackhandler.*;
import com.sabrepenguin.techreborn.recipe.AlloyRecipe;
import com.sabrepenguin.techreborn.recipe.RegistryHandler;
import com.sabrepenguin.techreborn.recipe.utils.RecipeUtils;
import com.sabrepenguin.techreborn.tileentity.TileEntityIOManager;
import com.sabrepenguin.techreborn.util.UpgradeUtils;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TileEntityAlloySmelter extends TileEntityIOManager {

	private AlloyRecipe cachedRecipe = null;
	private int processTime = 0;
	private boolean isActive = false;

	// TODO: Config
	private static int totalProcessTime = 200;
	private static int energyCost = 24;
	private static int maxReceive = 128;
	private static int baseCapacity = 4000;

	private int cachedProcessTime;
	private int cachedEnergyCost;

	public TileEntityAlloySmelter() {
		super(2, 1, baseCapacity, maxReceive, 0);
		cachedProcessTime = totalProcessTime;
		cachedEnergyCost = energyCost;
	}

	@Override
	protected void recalculateCosts() {
		// Processing
		this.cachedProcessTime = UpgradeUtils.getProcessingTimeMultiplier(upgrades, totalProcessTime);
		this.cachedEnergyCost = UpgradeUtils.getTotalCostMultiplier(upgrades, energyCost);

		// Input, Output, Capacity
		this.energyStorage.setMaxEnergy(UpgradeUtils.getTotalEnergyStorageIncrease(upgrades, baseCapacity));

		float newTransfer = UpgradeUtils.getEnergyTransferMultiplier(upgrades);
		this.energyStorage.setMaxReceive((int) (maxReceive * newTransfer));
	}

	private void updateState(boolean state) {
		IBlockState currentState = world.getBlockState(pos);
		world.setBlockState(pos, currentState.withProperty(BlockHorizontalMachine.ACTIVE, state), 3);
	}

	private void refreshRecipe() {
		refreshRecipe = false;
		if (cachedRecipe != null) {
			if (RecipeUtils.checkRecipeValid(inputs, cachedRecipe.getInputs()))
				return;
		}
		AlloyRecipe newRecipe = RegistryHandler.instance().getAlloyRegistry().getRecipe(inputs);
		if (cachedRecipe != newRecipe) {
			cachedRecipe = newRecipe;
			processTime = 0;
		}
	}

	private boolean canProcess() {
		if (cachedRecipe == null)
			return false;
		ItemStack output = this.output.getStackInSlot(0);
		ItemStack recipeOutput = cachedRecipe.getOutput();
		if (output.isEmpty()) return true;
		if (!output.isItemEqual(recipeOutput)) return false;
		if (!RecipeUtils.checkRecipeValid(inputs, cachedRecipe.getInputs())) return false;
		int res = output.getCount() + recipeOutput.getCount();
		return res <= this.output.getSlotLimit(0) && res <= output.getMaxStackSize();
	}

	private void processItem() {
		ItemStack outputItem = output.getStackInSlot(0);
		if (outputItem.isEmpty() || outputItem.isItemEqual(cachedRecipe.getOutput().copy()))
			inventory.insertItem(3, cachedRecipe.getOutput().copy(), false);
		RecipeUtils.applyRecipeToHandler(inputs, cachedRecipe.getInputs());
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		processTime = compound.getInteger("processed");
		isActive = compound.getBoolean("active");
		cachedRecipe = RegistryHandler.instance().getAlloyRegistry().getRecipe(Arrays.asList(
				inputs.getStackInSlot(0),
				inputs.getStackInSlot(1)
		));
		super.readFromNBT(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("processed", processTime);
		compound.setBoolean("isActive", isActive);
		return super.writeToNBT(compound);
	}

	// ITickable
	@Override
	public void update() {
		if (world.isRemote)
			return;
		super.update();

		boolean isDirty = false;
		ItemStack leftInput = inputs.getStackInSlot(0);
		ItemStack rightInput = inputs.getStackInSlot(1);

		if (refreshRecipe) {
			refreshRecipe();
		}

		boolean active = false;
		if (cachedRecipe != null && canProcess()) {
			if (energyStorage.internalExtract(cachedEnergyCost, true) == cachedEnergyCost) {
				energyStorage.internalExtract(cachedEnergyCost, false);
				processTime++;
				active = true;
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
		if (!leftInput.isEmpty() && !rightInput.isEmpty()) {
			active = true;
		} else if (processTime > 0) {
			processTime = 0;
			isDirty = true;
		}

		if (active != isActive) {
			isActive = active;
			updateState(active);
			isDirty = true;
		}

		if (isDirty) {
			markDirty();
		}
	}
}
