package com.sabrepenguin.techreborn.tileentity.components.specialized;

import com.cleanroommc.modularui.value.sync.DoubleSyncValue;
import com.sabrepenguin.techreborn.recipe.recipe.ITRRecipe;
import com.sabrepenguin.techreborn.recipe.registries.ITRRegistry;
import com.sabrepenguin.techreborn.recipe.utils.RecipeUtils;
import com.sabrepenguin.techreborn.tileentity.components.MachineComponent;
import com.sabrepenguin.techreborn.tileentity.components.RecipeComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandlerModifiable;

import java.util.List;
import java.util.function.Consumer;

public class FuelRecipeComponent implements MachineComponent, RecipeComponent {

	protected final IItemHandlerModifiable inputs;
	protected final IItemHandlerModifiable internalOutputs;
	protected final Consumer<Boolean> stateUpdater;

	protected final int fuelSlot;

	protected int processTime;
	protected int totalProcessTime;
	protected int burnTime;
	protected int totalBurnTime;

	protected final ITRRegistry registry;
	protected ITRRecipe cachedRecipe;

	protected boolean refreshRecipe;
	protected boolean burning;

	public FuelRecipeComponent(ITRRegistry registry, IItemHandlerModifiable inputs, IItemHandlerModifiable internalOutput,
								   int fuelSlot, Consumer<Boolean> stateUpdate) {
		this.registry = registry;
		this.inputs = inputs;
		this.internalOutputs = internalOutput;
		this.stateUpdater = stateUpdate;
		this.fuelSlot = fuelSlot;
		cachedRecipe = null;
		burnTime = 0;
		totalBurnTime = 0;
		totalProcessTime = 200;
		burning = false;
	}

	public void onUpdatePacket(World world, BlockPos pos, NBTTagCompound compound) {
		boolean previousState = burning;
		readFromNBT(compound);
		if (world != null && world.isRemote && previousState != burning) {
			world.markBlockRangeForRenderUpdate(pos, pos);
		}
	}

	@Override
	public boolean update() {
		boolean isDirty = false;

		if (refreshRecipe) {
			refreshRecipe();
		}

		if (isBurning()) {
			burnTime--;
		}

		if (cachedRecipe != null) {
			boolean canProcess = canProcess();
			if (!isBurning() && canProcess) {
				ItemStack fuel = inputs.getStackInSlot(fuelSlot);
				this.totalBurnTime = (int) (TileEntityFurnace.getItemBurnTime(fuel) * 1.25);
				this.burnTime = this.totalBurnTime;
				if (this.isBurning()) {
					isDirty = true;
					ItemStack realFuel = inputs.extractItem(fuelSlot, 1, false);
					Item item = realFuel.getItem();
					ItemStack container = item.getContainerItem(realFuel);
					if (!container.isEmpty()) {
						inputs.insertItem(fuelSlot, container, false);
					}
				}
			}
			if (isBurning() && canProcess) {
				processTime++;
				if (processTime >= totalProcessTime) {
					processItem();
					processTime = 0;
					isDirty = true;
				}
			} else {
				processTime = 0;
			}
		} else if (processTime > 0) {
			processTime = 0;
			isDirty = true;
		}

		if (burning != isBurning()) {
			burning = isBurning();
			isDirty = true;
			updateState(isBurning());
		}

		return isDirty;
	}

	protected boolean isBurning() {
		return burnTime > 0;
	}

	protected void updateState(boolean isBurning) {
		stateUpdater.accept(isBurning);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		processTime = compound.getInteger("Processed");
		burnTime = compound.getInteger("BurnTime");
		totalBurnTime = compound.getInteger("TotalBurnTime");
		cachedRecipe = registry.getRecipe(inputs);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("Processed", processTime);
		compound.setInteger("BurnTime", burnTime);
		compound.setInteger("TotalBurnTime", totalBurnTime);
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
		cachedRecipe.apply(inputs);
	}

	public void informRefreshRecipe() {
		refreshRecipe = true;
	}

	@Override
	public void updateMultipliers(float processMultiplier, float energyMultiplier) {
	}

	public int getProcessTime() {
		return processTime;
	}

	public DoubleSyncValue getProgress() {
		return new DoubleSyncValue(() -> (double) processTime / totalProcessTime,
				value -> processTime = (int) (value * totalProcessTime));
	}

	public DoubleSyncValue getFuel() {
		return new DoubleSyncValue(() -> (double) burnTime / totalBurnTime,
				value -> burnTime = (int) (value * totalBurnTime));
	}

	public ITRRecipe getCachedRecipe() {
		return cachedRecipe;
	}

	public ITRRegistry getRegistry() {
		return registry;
	}
}
