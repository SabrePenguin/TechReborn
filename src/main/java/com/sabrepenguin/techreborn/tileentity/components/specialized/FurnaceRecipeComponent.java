package com.sabrepenguin.techreborn.tileentity.components.specialized;

import com.cleanroommc.modularui.value.sync.DoubleSyncValue;
import com.sabrepenguin.techreborn.tileentity.components.MachineComponent;
import com.sabrepenguin.techreborn.tileentity.components.RecipeComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class FurnaceRecipeComponent implements MachineComponent, RecipeComponent {

	protected final IItemHandlerModifiable inputs;
	protected final IItemHandlerModifiable internalOutputs;
	protected final Consumer<Boolean> stateUpdater;

	protected final int fuelSlot;

	protected int processTime;
	protected int totalProcessTime;
	protected int burnTime;
	protected int totalBurnTime;

	@Nonnull
	protected ItemStack cachedRecipe;

	protected boolean refreshRecipe;
	protected boolean burning;

	public FurnaceRecipeComponent(IItemHandlerModifiable inputs, IItemHandlerModifiable internalOutput,
							   int fuelSlot, Consumer<Boolean> stateUpdate, int totalProcessTime) {
		this.inputs = inputs;
		this.internalOutputs = internalOutput;
		this.stateUpdater = stateUpdate;
		this.fuelSlot = fuelSlot;
		cachedRecipe = ItemStack.EMPTY;
		burnTime = 0;
		totalBurnTime = 0;
		this.totalProcessTime = totalProcessTime;
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

		if (!cachedRecipe.isEmpty()) {
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
		ItemStack in = inputs.getStackInSlot(0);
		if (!in.isEmpty()) {
			cachedRecipe = FurnaceRecipes.instance().getSmeltingResult(inputs.getStackInSlot(0));
		}
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
		ItemStack in = inputs.getStackInSlot(0);
		if (!cachedRecipe.isEmpty()) {
			if (cachedRecipe.isItemEqual(FurnaceRecipes.instance().getSmeltingResult(in)))
				return;
		}
		ItemStack newRecipe;
		if (!in.isEmpty()) {
			newRecipe = FurnaceRecipes.instance().getSmeltingResult(in);
		} else {
			newRecipe = ItemStack.EMPTY;
		}
		if (!cachedRecipe.isItemEqual(newRecipe)) {
			cachedRecipe = newRecipe;
			processTime = 0;
		}
	}

	protected boolean canProcess() {
		if (cachedRecipe.isEmpty())
			return false;
		ItemStack output = this.internalOutputs.getStackInSlot(0);
		ItemStack recipeOutput = cachedRecipe;
		if (output.isEmpty()) return true;
		if (!output.isItemEqual(recipeOutput)) return false;
		int res = output.getCount() + recipeOutput.getCount();
		return res <= this.internalOutputs.getSlotLimit(0) && res <= output.getMaxStackSize();
	}

	protected boolean canOutputFit() {
		ItemStack currentSlotStack = internalOutputs.getStackInSlot(0);
		ItemStack cachedOutputStack = cachedRecipe;
		if (currentSlotStack.isEmpty())
			return true;
		if (currentSlotStack.isItemEqual(cachedOutputStack)) {
			if (currentSlotStack.hasTagCompound() && cachedOutputStack.hasTagCompound()) {
				if (!currentSlotStack.getTagCompound().equals(cachedOutputStack.getTagCompound())) {
					return false;
				}
			} else if (currentSlotStack.hasTagCompound() || cachedOutputStack.hasTagCompound()) {
				return false;
			}
			return true;
		}
		return false;
	}

	protected void processItem() {
		if (!canOutputFit()) {
			return;
		}
		ItemStack cachedOutputStack = cachedRecipe;
		internalOutputs.insertItem(0, cachedOutputStack, false);
		inputs.extractItem(0, 1, false);
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
}
