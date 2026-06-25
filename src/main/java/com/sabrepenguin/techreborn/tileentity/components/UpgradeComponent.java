package com.sabrepenguin.techreborn.tileentity.components;

import com.sabrepenguin.techreborn.capability.stackhandler.StackLimitedItemStackHandler;
import com.sabrepenguin.techreborn.config.TechRebornConfig;
import com.sabrepenguin.techreborn.util.UpgradeUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class UpgradeComponent implements MachineComponent {
	protected final ItemStackHandler upgrades;
	protected final int baseCapacity;
	protected final EnergyComponent energyComponent;
	@Nullable
	protected final RecipeComponent recipeComponent;
	protected int cachedRevision = -1;

	private float processMultiplier;
	private float energyMultiplier;
	private int capacity;

	private boolean shouldRecalculate = false;

	public UpgradeComponent(int baseCapacity, EnergyComponent energyComponent) {
		this(baseCapacity, energyComponent, null);
	}

	public UpgradeComponent(int baseCapacity, EnergyComponent energyComponent, @Nullable RecipeComponent recipeComponent) {
		this.upgrades = new StackLimitedItemStackHandler(4, 1, () -> shouldRecalculate = true);
		this.baseCapacity = baseCapacity;
		this.energyComponent = energyComponent;
		this.recipeComponent = recipeComponent;
		processMultiplier = 1;
		energyMultiplier = 1;
		capacity = baseCapacity;
	}

	@Override
	public boolean update() {
		boolean recalced = false;
		if (cachedRevision != TechRebornConfig.getConfigRevision()) {
			cachedRevision = TechRebornConfig.getConfigRevision();
			shouldRecalculate = true;
		}

		if (shouldRecalculate) {
			shouldRecalculate = false;
			recalced = true;
			recalculate();
		}

		return recalced;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		upgrades.deserializeNBT(compound.getCompoundTag("upgrades"));
		recalculate();
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("upgrades", upgrades.serializeNBT());
		return compound;
	}

	protected void recalculate() {
		processMultiplier = UpgradeUtils.getProcessingTimeMultiplier(upgrades);
		energyMultiplier = UpgradeUtils.getEnergyTransferMultiplier(upgrades);
		capacity = UpgradeUtils.getTotalEnergyStorageIncrease(upgrades, baseCapacity);

		if (energyComponent != null) {
			energyComponent.updateEnergy(energyMultiplier, capacity);
		}
		if (recipeComponent != null) {
			float energyCostMultiplier = UpgradeUtils.getTotalCostMultiplier(upgrades);
			recipeComponent.updateMultipliers(processMultiplier, energyCostMultiplier);
		}
	}

	public int getCapacity() {
		return capacity;
	}

	public IItemHandlerModifiable getUpgrades() {
		return upgrades;
	}

	public void onUpgradeChange(ItemStack newItem, boolean onlyAmountChanged, boolean client, boolean init) {
		if (client || init || onlyAmountChanged) {
			shouldRecalculate = false;
			return;
		}
		shouldRecalculate = true;
	}
}
