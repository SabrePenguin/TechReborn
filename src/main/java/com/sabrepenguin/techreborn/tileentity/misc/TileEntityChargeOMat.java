package com.sabrepenguin.techreborn.tileentity.misc;

import com.cleanroommc.modularui.api.IGuiHolder;
import com.cleanroommc.modularui.api.drawable.IKey;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.utils.Alignment;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.widgets.slot.ItemSlot;
import com.cleanroommc.modularui.widgets.slot.ModularSlot;
import com.cleanroommc.modularui.widgets.slot.SlotGroup;
import com.sabrepenguin.techreborn.blocks.machines.BlockHorizontalMachine;
import com.sabrepenguin.techreborn.capability.energy.TEEnergyStorage;
import com.sabrepenguin.techreborn.capability.stackhandler.ISideConfigTE;
import com.sabrepenguin.techreborn.capability.stackhandler.SideConfig;
import com.sabrepenguin.techreborn.capability.stackhandler.SlotAction;
import com.sabrepenguin.techreborn.config.TechRebornConfig;
import com.sabrepenguin.techreborn.gui.PowerDisplayWidget;
import com.sabrepenguin.techreborn.gui.SlotPosition;
import com.sabrepenguin.techreborn.gui.TRGuis;
import com.sabrepenguin.techreborn.tileentity.MachineIOManager;
import com.sabrepenguin.techreborn.util.InventoryUtils;
import com.sabrepenguin.techreborn.util.ItemStackUtils;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Supplier;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class TileEntityChargeOMat extends TileEntity implements ISideConfigTE, ITickable, IGuiHolder<PosGuiData> {
	protected final ItemStackHandler inventory;
	protected final MachineIOManager ioManager;

	protected final TEEnergyStorage energyStorage;

	protected boolean isActive = false;

	public TileEntityChargeOMat() {
		inventory = new ItemStackHandler(6);
		ioManager = new MachineIOManager(SideConfig.bidirectional(inventory));
		energyStorage = new TEEnergyStorage(TechRebornConfig.machineConfig.chargeOMat.maxEnergy, TechRebornConfig.machineConfig.chargeOMat.maxInput);
		energyStorage.setCanExtract(false);
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		return oldState.getBlock() != newState.getBlock();
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		inventory.deserializeNBT(compound.getCompoundTag("inventory"));
		if (compound.hasKey("energy"))
			energyStorage.setEnergy(compound.getInteger("energy"));
		ioManager.readFromNBT(compound.getCompoundTag("IOManager"));
		isActive = compound.getBoolean("IsActive");
		super.readFromNBT(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("inventory", inventory.serializeNBT());
		compound.setInteger("energy", energyStorage.getEnergyStored());
		compound.setTag("IOManager", ioManager.writeToNBT());
		compound.setBoolean("IsActive", isActive);
		return super.writeToNBT(compound);
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}

	@Override
	public @Nullable SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(pos, 3, this.getUpdateTag());
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

	// ITickable

	@Override
	public void update() {
		if (world.isRemote) {
			return;
		}

		boolean isDirty = false;
		if (ioManager.performTransfer(world, pos)) {
			isDirty = true;
		}
		boolean active = false;
		for (int slot = 0; slot < inventory.getSlots(); slot++) {
			ItemStack simulated = inventory.getStackInSlot(slot);
			if (!simulated.isEmpty()) {
				ItemStack stack = inventory.extractItem(slot, inventory.getSlotLimit(slot), false);
				active = ItemStackUtils.chargeItem(energyStorage, stack);
				inventory.insertItem(slot, stack, false);
			}
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

	private void updateState(boolean state) {
		IBlockState currentState = world.getBlockState(pos);
		world.setBlockState(pos, currentState.withProperty(BlockHorizontalMachine.ACTIVE, state), 3);
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


	// IGuiHolder

	@Override
	public ModularPanel buildUI(PosGuiData data, PanelSyncManager syncManager, UISettings settings) {
		String lang = "tile.techreborn.charge_o_mat.name";
		ModularPanel panel = ModularPanel.defaultPanel(IKey.lang(lang).get());
		syncManager.registerSlotGroup(new SlotGroup("charging", 6, 100, true));

		Supplier<EnumFacing> getFacing = () -> getWorld().getBlockState(getPos()).getValue(BlockHorizontal.FACING);
		TRGuis.setupConfigPanel(panel, syncManager, getPos(), ioManager, getFacing,
				new SlotPosition(SlotAction.BIDIRECTIONAL, 61, 20, 0, 0),
				new SlotPosition(SlotAction.BIDIRECTIONAL, 61, 40, 0, 1),
				new SlotPosition(SlotAction.BIDIRECTIONAL, 61, 60, 0, 2),
				new SlotPosition(SlotAction.BIDIRECTIONAL, 97, 20, 0, 3),
				new SlotPosition(SlotAction.BIDIRECTIONAL, 97, 40, 0, 4),
				new SlotPosition(SlotAction.BIDIRECTIONAL, 97, 60, 0, 5)
		);
		panel.bindPlayerInventory()
				.child(IKey.lang(lang)
						.asWidget()
						.align(Alignment.TopCenter)
						.top(7))
				.child(PowerDisplayWidget.fromEnergyStorage(syncManager, energyStorage)
						.horizontalCenter()
						.top(23));

		panel.child(new ItemSlot().pos(61, 20)
						.slot(new ModularSlot(inventory, 0)
								.slotGroup("charging")
								.filter(InventoryUtils.IS_POWER_ITEM)))
				.child(new ItemSlot().pos(61, 40)
						.slot(new ModularSlot(inventory, 1)
								.slotGroup("charging")
								.filter(InventoryUtils.IS_POWER_ITEM)))
				.child(new ItemSlot().pos(61, 60)
						.slot(new ModularSlot(inventory, 2)
								.slotGroup("charging")
								.filter(InventoryUtils.IS_POWER_ITEM)))
				.child(new ItemSlot().pos(97, 20)
						.slot(new ModularSlot(inventory, 3)
								.slotGroup("charging")
								.filter(InventoryUtils.IS_POWER_ITEM)))
				.child(new ItemSlot().pos(97, 40)
						.slot(new ModularSlot(inventory, 4)
								.slotGroup("charging")
								.filter(InventoryUtils.IS_POWER_ITEM)))
				.child(new ItemSlot().pos(97, 60)
						.slot(new ModularSlot(inventory, 5)
								.slotGroup("charging")
								.filter(InventoryUtils.IS_POWER_ITEM)));
		return panel;
	}
}
