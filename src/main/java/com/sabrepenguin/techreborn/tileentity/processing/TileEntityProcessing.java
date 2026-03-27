package com.sabrepenguin.techreborn.tileentity.processing;

import com.sabrepenguin.techreborn.blocks.machines.BlockHorizontalMachine;
import com.sabrepenguin.techreborn.recipe.registries.BasicRegistry;
import com.sabrepenguin.techreborn.recipe.ITRRecipe;
import com.sabrepenguin.techreborn.recipe.utils.RecipeUtils;
import com.sabrepenguin.techreborn.tileentity.TileEntityIOManager;
import com.sabrepenguin.techreborn.util.UpgradeUtils;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TileEntityProcessing extends TileEntityIOManager {

	private final int maxReceive;

	protected int totalProcessTime;
	private float cachedProcessMultiplier;
	private int energyCost;
	private float cachedEnergyMultiplier;
	protected int cachedProcessTime;
	private int cachedEnergyCost;

	private final BasicRegistry registry;
	private ITRRecipe cachedRecipe = null;
	protected int processTime = 0;
	private boolean isActive = false;

	public TileEntityProcessing(int inputSize, int outputSize, int feCapacity, int maxInput, BasicRegistry recipeRegistry) {
		this(inputSize, outputSize, feCapacity, maxInput, recipeRegistry, false);
	}

	public TileEntityProcessing(int inputSize, int outputSize, int feCapacity, int maxInput, BasicRegistry recipeRegistry, boolean singleInput) {
		super(inputSize, outputSize, feCapacity, maxInput, 0, singleInput);
		this.maxReceive = maxInput;
		totalProcessTime = 0;
		cachedProcessMultiplier = 1.0f;
		energyCost = 24;
		cachedEnergyMultiplier = 1.0f;
		cachedProcessTime = totalProcessTime;
		cachedEnergyCost = energyCost;
		this.registry = recipeRegistry;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		processTime = compound.getInteger("Processed");
		isActive = compound.getBoolean("IsActive");
		cachedRecipe = registry.getRecipe(inputs);
		if (cachedRecipe != null) {
			totalProcessTime = cachedRecipe.getRecipeTime();
			energyCost = cachedRecipe.getEnergyCost();
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("Processed", processTime);
		compound.setBoolean("IsActive", isActive);
		return super.writeToNBT(compound);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		boolean previousState = isActive;
		super.onDataPacket(net, pkt);
		this.readFromNBT(pkt.getNbtCompound());
		if (world != null && this.world.isRemote && previousState != isActive) {
			world.markBlockRangeForRenderUpdate(pos, pos);
		}
	}

	private void refreshRecipe() {
		refreshRecipe = false;
		if (cachedRecipe != null) {
			if (RecipeUtils.checkRecipeValid(inputs, cachedRecipe.getInputs()))
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

	private boolean canProcess() {
		if (cachedRecipe == null)
			return false;
		ItemStack output = this.output.getStackInSlot(0);
		ItemStack recipeOutput = cachedRecipe.getOutput().get(0);
		if (output.isEmpty()) return true;
		if (!output.isItemEqual(recipeOutput)) return false;
		if (!RecipeUtils.checkRecipeValid(inputs, cachedRecipe.getInputs())) return false;
		int res = output.getCount() + recipeOutput.getCount();
		return res <= this.output.getSlotLimit(0) && res <= output.getMaxStackSize();
	}

	private void processItem() {
		ItemStack outputItem = output.getStackInSlot(0);
		ItemStack cachedOutput = cachedRecipe.getOutput().get(0);
		if (outputItem.isEmpty()) {
			_internalOutput.insertItem(0, cachedOutput.copy(), false);
		}
		else if (outputItem.isItemEqual(cachedOutput)) {
			ItemStack outputCopy = cachedOutput.copy();
			ItemStack remainder = _internalOutput.insertItem(0, outputCopy, true);
			if (remainder.isEmpty()) {
				_internalOutput.insertItem(0, outputCopy, false);
			} else {
				// Not enough room in output
				return;
			}
		} else {
			// Output does not match
			return;
		}
		RecipeUtils.applyRecipeToHandler(inputs, cachedRecipe.getInputs());
	}

	private void updateState(boolean state) {
		IBlockState currentState = world.getBlockState(pos);
		world.setBlockState(pos, currentState.withProperty(BlockHorizontalMachine.ACTIVE, state), 3);
	}

	// ITickable
	@Override
	public void update() {
		if (world.isRemote)
			return;
		super.update();

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

		if (active != isActive) {
			isActive = active;
			updateState(active);
			isDirty = true;
		}

		if (isDirty) {
			markDirty();
		}
	}

	// TileEntityIOManager
	@Override
	protected void recalculateCosts() {
		// Processing
		this.cachedProcessMultiplier = UpgradeUtils.getProcessingTimeMultiplier(upgrades);
		this.cachedProcessTime = (int) (cachedProcessMultiplier * totalProcessTime);
		this.cachedEnergyMultiplier = UpgradeUtils.getTotalCostMultiplier(upgrades);
		this.cachedEnergyCost = (int) (cachedEnergyMultiplier * energyCost);

		// Input, Output, Capacity
		this.energyStorage.setMaxEnergy(UpgradeUtils.getTotalEnergyStorageIncrease(upgrades, baseCapacity));

		float newTransfer = UpgradeUtils.getEnergyTransferMultiplier(upgrades);
		this.energyStorage.setMaxReceive((int) (maxReceive * newTransfer));
		this.energyStorage.setMaxExtract(cachedEnergyCost);
	}
}
