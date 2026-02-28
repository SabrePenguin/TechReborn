package com.sabrepenguin.techreborn.tileentity;

import com.sabrepenguin.techreborn.capability.energy.TEEnergyStorage;
import com.sabrepenguin.techreborn.capability.stackhandler.*;
import com.sabrepenguin.techreborn.util.UpgradeUtils;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class TileEntityIOManager extends TileEntity implements ISideConfigTE, ITickable {
	protected final ItemStackHandler inventory;
	protected final RestrictedItemStackHandler inputs;
	protected final LimitedItemStackHandler output;
	protected final RestrictedItemStackHandler battery;
	protected final MachineIOManager ioManager;
	protected final ItemStackHandler upgrades;


	protected final TEEnergyStorage energyStorage;

	protected boolean shouldRecalculate = false;
	protected boolean refreshRecipe = false;

	protected int cachedRevision = -1;

	protected final int baseCapacity;

	public TileEntityIOManager(int inputSize, int outputSize, int capacity, int maxReceive, int maxOutput) {
		inventory = new ItemStackHandler(inputSize + outputSize + 1) {
			@Override
			protected void onContentsChanged(int slot) {
				if (slot < inputSize) {
					refreshRecipe = true;
				}
			}
		};
		inputs = new RestrictedItemStackHandler(inventory, 0, inputSize);
		output = new LimitedItemStackHandler(new RestrictedItemStackHandler(inventory, inputSize, inputSize + outputSize), SlotAction.OUTPUT);
		battery = new RestrictedItemStackHandler(inventory, inputSize + outputSize);
		ioManager = new MachineIOManager(SideConfig.input(inputs), SideConfig.output(output), SideConfig.bidirectional(battery));

		upgrades = new StackLimitedItemStackHandler(4, 1, () -> shouldRecalculate = true); //TODO: Config slot size

		baseCapacity = capacity;
		energyStorage = new TEEnergyStorage(baseCapacity, maxReceive, maxOutput);
	}

	protected abstract void recalculateCosts();

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		return oldState.getBlock() != newState.getBlock();
	}

	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityEnergy.ENERGY
				|| capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
				|| super.hasCapability(capability, facing);
	}

	@Override
	public @Nullable <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			if (facing == null) {
				return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory);
			}
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(ioManager.getSide(facing));
		} else if (capability == CapabilityEnergy.ENERGY) {

			return CapabilityEnergy.ENERGY.cast(energyStorage);
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		inventory.deserializeNBT(compound.getCompoundTag("inventory"));
		upgrades.deserializeNBT(compound.getCompoundTag("upgrades"));
		if (compound.hasKey("maxEnergy"))
			energyStorage.setMaxEnergy(compound.getInteger("maxEnergy"));
		if (compound.hasKey("energy"))
			energyStorage.setEnergy(compound.getInteger("energy"));
		ioManager.readFromNBT(compound.getCompoundTag("IOManager"));
		super.readFromNBT(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("inventory", inventory.serializeNBT());
		compound.setTag("upgrades", upgrades.serializeNBT());
		compound.setInteger("energy", energyStorage.getEnergyStored());
		if (energyStorage.getMaxEnergyStored() != baseCapacity) {
			compound.setInteger("maxEnergy", energyStorage.getMaxEnergyStored());
		}
		compound.setTag("IOManager", ioManager.writeToNBT());
		return super.writeToNBT(compound);
	}

	// ITickable
	@Override
	public void update() {
		if (world.isRemote) {
			return;
		}

		if (cachedRevision != UpgradeUtils.config_revision) {
			shouldRecalculate = true;
			cachedRevision = UpgradeUtils.config_revision;
		}
		if (shouldRecalculate) {
			shouldRecalculate = false;
			recalculateCosts();
		}

		boolean isDirty = false;
		if (ioManager.performTransfer(world, pos)) {
			isDirty = true;
		}

		{
			ItemStack energyItem = battery.extractItem(0, 1, true);
			if (!energyItem.isEmpty() && energyItem.hasCapability(CapabilityEnergy.ENERGY, null)) {
				IEnergyStorage itemStorage = energyItem.getCapability(CapabilityEnergy.ENERGY, null);
				int simExtract = itemStorage.extractEnergy(energyStorage.getMaxInput(), true);
				int simInsert = energyStorage.receiveEnergy(simExtract, true);
				if (simInsert > 0) {
					ItemStack realEnergyItem = battery.extractItem(0, 1, false);
					IEnergyStorage realStorage = realEnergyItem.getCapability(CapabilityEnergy.ENERGY, null);
					int actualExtract = realStorage.extractEnergy(simInsert, false);
					energyStorage.receiveEnergy(actualExtract, false);
					battery.insertItem(0, realEnergyItem, false);
					isDirty = true;
				}
			}
		}

		if (isDirty) {
			markDirty();
		}
	}

	// ISidedConfigTE
	@Override
	public void rotateSlot(int sideIndex, int handlerIndex, int localSlotIndex) {
		if (ioManager.getSide(sideIndex).rotateSlot(handlerIndex, localSlotIndex)) {
			updateTE();
		}
	}

	@Override
	public void swapSlot(int index, boolean input) {
		ioManager.swapIndex(index, input);
		updateTE();
	}

	@Override
	public void updateTE() {
		markDirty();
		IBlockState state = world.getBlockState(pos);
		world.notifyBlockUpdate(pos, state, state, 3);
	}

	@Override
	public MachineIOManager getManager() {
		return ioManager;
	}

	// IOnSlotChanged
	public void onInputChange(ItemStack newItem, boolean onlyAmountChanged, boolean client, boolean init) {
		if (world.isRemote || init) return;
		if (onlyAmountChanged) {
			refreshRecipe = false;
			return;
		}
		refreshRecipe = true;
		markDirty();
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
