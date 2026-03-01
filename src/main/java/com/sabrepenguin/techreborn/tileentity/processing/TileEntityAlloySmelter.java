package com.sabrepenguin.techreborn.tileentity.processing;

import com.cleanroommc.modularui.api.IGuiHolder;
import com.cleanroommc.modularui.api.IPanelHandler;
import com.cleanroommc.modularui.api.drawable.IKey;
import com.cleanroommc.modularui.drawable.GuiTextures;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.integration.jei.ModularUIJeiPlugin;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.utils.Alignment;
import com.cleanroommc.modularui.value.sync.DoubleSyncValue;
import com.cleanroommc.modularui.value.sync.IntSyncValue;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.widgets.ButtonWidget;
import com.cleanroommc.modularui.widgets.ProgressWidget;
import com.cleanroommc.modularui.widgets.slot.ItemSlot;
import com.cleanroommc.modularui.widgets.slot.ModularSlot;
import com.cleanroommc.modularui.widgets.slot.SlotGroup;
import com.sabrepenguin.techreborn.blocks.machines.BlockHorizontalMachine;
import com.sabrepenguin.techreborn.capability.stackhandler.*;
import com.sabrepenguin.techreborn.gui.*;
import com.sabrepenguin.techreborn.jei.TRRecipePlugin;
import com.sabrepenguin.techreborn.recipe.AlloyRecipe;
import com.sabrepenguin.techreborn.recipe.RegistryHandler;
import com.sabrepenguin.techreborn.recipe.utils.RecipeUtils;
import com.sabrepenguin.techreborn.tileentity.TileEntityIOManager;
import com.sabrepenguin.techreborn.util.UpgradeUtils;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.Collections;
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TileEntityAlloySmelter extends TileEntityIOManager implements IGuiHolder<PosGuiData> {

	private AlloyRecipe cachedRecipe = null;
	private int processTime = 0;
	private boolean isActive = false;

	// TODO: Config
	private static int energyCost = 24;
	private static int maxReceive = 128;
	private static int baseCapacity = 4000;

	private int totalProcessTime;
	private float cachedProcessMultiplier;
	private int cachedProcessTime;
	private int cachedEnergyCost;

	public TileEntityAlloySmelter() {
		super(2, 1, baseCapacity, maxReceive, energyCost);
		totalProcessTime = 0;
		cachedProcessMultiplier = 0;
		cachedProcessTime = totalProcessTime;
		cachedEnergyCost = energyCost;
	}

	@Override
	protected void recalculateCosts() {
		// Processing
		this.cachedProcessMultiplier = UpgradeUtils.getProcessingTimeMultiplier(upgrades);
		this.cachedProcessTime = (int) (cachedProcessMultiplier * totalProcessTime);
		this.cachedEnergyCost = UpgradeUtils.getTotalCostMultiplier(upgrades, energyCost);

		// Input, Output, Capacity
		this.energyStorage.setMaxEnergy(UpgradeUtils.getTotalEnergyStorageIncrease(upgrades, baseCapacity));

		float newTransfer = UpgradeUtils.getEnergyTransferMultiplier(upgrades);
		this.energyStorage.setMaxReceive((int) (maxReceive * newTransfer));
		this.energyStorage.setMaxExtract((int) (energyCost * newTransfer));
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
			if (cachedRecipe != null) {
				totalProcessTime = cachedRecipe.getRecipeTime();
				cachedProcessTime = (int) (totalProcessTime * cachedProcessMultiplier);
			}
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
		ItemStack cachedOutput = cachedRecipe.getOutput();
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

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		processTime = compound.getInteger("Processed");
		isActive = compound.getBoolean("IsActive");
		cachedRecipe = RegistryHandler.instance().getAlloyRegistry().getRecipe(Arrays.asList(
				inputs.getStackInSlot(0),
				inputs.getStackInSlot(1)
		));
		if (cachedRecipe != null) {
			totalProcessTime = cachedRecipe.getRecipeTime();
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("Processed", processTime);
		compound.setBoolean("IsActive", isActive);
		return super.writeToNBT(compound);
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

	// IGuiHolder
	@Override
	public ModularPanel buildUI(PosGuiData data, PanelSyncManager syncManager, UISettings settings) {
		ModularPanel panel = ModularPanel.defaultPanel("alloy_smelter");
		syncManager.registerSlotGroup(new SlotGroup("inputs", 2, 100, true))
				.registerSlotGroup(new SlotGroup("upgrades", 4, 50, true));
		IntSyncValue capacity = new IntSyncValue(energyStorage::getMaxEnergyStored, val -> {});
		IntSyncValue maxReceive = new IntSyncValue(energyStorage::getMaxInput, val -> {});
		IntSyncValue energyStored = new IntSyncValue(energyStorage::getEnergyStored, val -> {});
		syncManager.syncValue("capacity", capacity)
				.syncValue("maxReceive", maxReceive)
				.syncValue("energyStored", energyStored);

		Supplier<EnumFacing> getFacing = () -> getWorld().getBlockState(getPos()).getValue(BlockHorizontal.FACING);
		IPanelHandler panelHandler = syncManager.syncedPanel("config", true,
				(syncManager1, syncHandler) ->
						TRGuis.createConfigPanel(syncManager1, syncHandler, this.getPos(), panel.getArea(),
								this.ioManager,
								getFacing,
								new SlotPosition(SlotAction.INPUT, 47, 39, 0, 0),
								new SlotPosition(SlotAction.INPUT, 65, 39, 0, 1),
								new SlotPosition(SlotAction.OUTPUT, 116, 39, 1, 0),
								new SlotPosition(SlotAction.BIDIRECTIONAL, 7, 59, 2, 0)));
		TRGuis.addConfigPanel(panel, panelHandler);

		DoubleSyncValue progress = new DoubleSyncValue(() -> (double) processTime / cachedProcessTime,
				value -> processTime = (int) (value * cachedProcessTime));

		panel.bindPlayerInventory()
				.child(IKey.lang("tile.techreborn.alloy_smelter.name")
						.asWidget()
						.align(Alignment.TopCenter)
						.top(7))
				.child(new ProgressWidget()
						.size(20)
						.pos(54, 39)
						.texture(GuiTextures.PROGRESS_ARROW, 20)
						.value(progress))
				.child(new ButtonWidget<>()
						.size(20, 15).pos(54, 41)
						.tooltip(tooltip -> {
							tooltip.addLine(IKey.str("Open JEI"));
						}).invisible().onMousePressed(mouseButton -> {
							if (ModularUIJeiPlugin.getRuntime() != null) {
								ModularUIJeiPlugin.getRuntime().getRecipesGui().showCategories(Collections.singletonList(TRRecipePlugin.ALLOY_UID));
								return true;
							}
							return false;
						}))
				.child(new ProgressWidget()
						.size(20)
						.pos(104, 39)
						.texture(TRGuiTextures.LEFT_PROGRESS_ARROW, 20)
						.direction(ProgressWidget.Direction.LEFT)
						.value(progress))
				.child(new ButtonWidget<>()
						.size(20, 15).pos(54, 41)
						.tooltip(tooltip -> {
							tooltip.addLine(IKey.str("Open JEI"));
						}).invisible().onMousePressed(mouseButton -> {
							if (ModularUIJeiPlugin.getRuntime() != null) {
								ModularUIJeiPlugin.getRuntime().getRecipesGui().showCategories(Collections.singletonList(TRRecipePlugin.ALLOY_UID));
								return true;
							}
							return false;
						}))
				.child(new PowerDisplayWidget()
						.pos(9, 6)
						.capacity(capacity)
						.maxReceive(maxReceive)
						.energy(energyStored));

		panel.child(new ItemSlot().pos(34, 39)
						.slot(new ModularSlot(inputs, 0)
								.slotGroup("inputs")
								.changeListener(this::onInputChange)))
				.child(new ItemSlot().pos(126, 39)
						.slot(new ModularSlot(inputs, 1)
								.slotGroup("inputs")
								.changeListener(this::onInputChange)))
				.child(new LargeItemSlot().pos(80, 39)
						.slot(new ModularSlot(output, 0)
								.accessibility(false, true))
						.size(24))
				.child(new ItemSlot().pos(7, 59)
						.slot(new ModularSlot(battery, 0)));

		panel.child(TRGuis.createUpdateTab(upgrades, "upgrades", this::onUpgradeChange).leftRelOffset(1f, 1));
		return panel;
	}
}
