package com.sabrepenguin.techreborn.tileentity.common;

import com.sabrepenguin.techreborn.capability.stackhandler.StackLimitedItemStackHandler;
import com.sabrepenguin.techreborn.config.TechRebornConfig;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class TileEntityUpgradeableIOManager extends TileEntityIOManager {
	protected final ItemStackHandler upgrades;

	protected boolean shouldRecalculate = false;

	protected int cachedRevision = -1;

	public TileEntityUpgradeableIOManager(int inputSize, int outputSize, int capacity, int maxReceive, int maxOutput) {
		this(inputSize, outputSize, capacity, maxReceive, maxOutput, false);
	}

	public TileEntityUpgradeableIOManager(int inputSize, int outputSize, int capacity, int maxReceive, int maxOutput, boolean singleInput) {
		super(inputSize, outputSize, capacity, maxReceive, maxOutput, singleInput);
		upgrades = new StackLimitedItemStackHandler(4, 1, () -> shouldRecalculate = true); //TODO: Config slot size
	}

	protected abstract void recalculateCosts();

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		upgrades.deserializeNBT(compound.getCompoundTag("upgrades"));
		super.readFromNBT(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("upgrades", upgrades.serializeNBT());
		return super.writeToNBT(compound);
	}

	@Override
	public void update() {
		if (world.isRemote) {
			return;
		}
		if (cachedRevision != TechRebornConfig.getConfigRevision()) {
			shouldRecalculate = true;
			cachedRevision = TechRebornConfig.getConfigRevision();
		}
		if (shouldRecalculate) {
			shouldRecalculate = false;
			recalculateCosts();
		}
		super.update();
	}

	public void onUpgradeChange(ItemStack newItem, boolean onlyAmountChanged, boolean client, boolean init) {
		if (world.isRemote || init || onlyAmountChanged) {
			shouldRecalculate = false;
			return;
		}
		shouldRecalculate = true;
		markDirty();
	}
}
