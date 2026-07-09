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
import com.sabrepenguin.techreborn.capability.stackhandler.ISideConfigTE;
import com.sabrepenguin.techreborn.capability.stackhandler.SlotAction;
import com.sabrepenguin.techreborn.config.TechRebornConfig;
import com.sabrepenguin.techreborn.gui.PowerDisplayWidget;
import com.sabrepenguin.techreborn.gui.SlotPosition;
import com.sabrepenguin.techreborn.gui.TRGuis;
import com.sabrepenguin.techreborn.tileentity.IOManager;
import com.sabrepenguin.techreborn.tileentity.components.SidedConfigComponent;
import com.sabrepenguin.techreborn.tileentity.components.specialized.ChargingComponent;
import com.sabrepenguin.techreborn.tileentity.components.EnergyComponent;
import com.sabrepenguin.techreborn.tileentity.components.ItemIOComponent;
import com.sabrepenguin.techreborn.util.InventoryUtils;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
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
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Supplier;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class TileEntityChargeOMat extends TileEntity implements ISideConfigTE, ITickable, IGuiHolder<PosGuiData> {
	protected final ItemIOComponent ioComponent;
	protected final EnergyComponent energyComponent;
	protected final ChargingComponent chargingComponent;
	protected final SidedConfigComponent configComponent;

	public TileEntityChargeOMat() {
		ioComponent = new ItemIOComponent.ItemIOComponentBuilder(this).bidirectionalSize(6).build();
		energyComponent = new EnergyComponent(TechRebornConfig.machineConfig.chargeOMat.maxEnergy, TechRebornConfig.machineConfig.chargeOMat.maxInput,
				TechRebornConfig.machineConfig.chargeOMat.maxInput).setUnextractable();
		chargingComponent = new ChargingComponent(ioComponent.getInputs(), energyComponent.getEnergyStorage(), this::updateState);
		configComponent = new SidedConfigComponent(ioComponent, this::updateTE);
	}

	private void updateState(boolean state) {
		IBlockState currentState = world.getBlockState(pos);
		world.setBlockState(pos, currentState.withProperty(BlockHorizontalMachine.ACTIVE, state), 3);
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		return oldState.getBlock() != newState.getBlock();
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		ioComponent.readFromNBT(compound);
		energyComponent.readFromNBT(compound);
		chargingComponent.readFromNBT(compound);
		super.readFromNBT(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		ioComponent.writeToNBT(compound);
		energyComponent.writeToNBT(compound);
		chargingComponent.writeToNBT(compound);
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
		super.onDataPacket(net, pkt);
		this.readFromNBT(pkt.getNbtCompound());
		chargingComponent.onUpdatePacket(world, pos, pkt.getNbtCompound());
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
				return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(ioComponent.getInputs());
			}
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(ioComponent.getManager().getSide(facing));
		} else if (capability == CapabilityEnergy.ENERGY) {
			return CapabilityEnergy.ENERGY.cast(energyComponent.getEnergyStorage());
		}
		return super.getCapability(capability, facing);
	}

	// ITickable

	@Override
	public void update() {
		if (world.isRemote) {
			return;
		}

		boolean updated = ioComponent.update() | energyComponent.update() | chargingComponent.update();

		if (updated) {
			markDirty();
		}
	}

	// ISidedConfigTE

	@Override
	public void updateTE() {
		markDirty();
		IBlockState state = world.getBlockState(pos);
		world.notifyBlockUpdate(pos, state, state, 3);
	}

	@Override
	public IOManager getManager() {
		return ioComponent.getManager();
	}

	@Override
	public SidedConfigComponent getSidedConfigComponent() {
		return configComponent;
	}

	// IGuiHolder

	@Override
	public ModularPanel buildUI(PosGuiData data, PanelSyncManager syncManager, UISettings settings) {
		String lang = "tile.techreborn.charge_o_mat.name";
		ModularPanel panel = ModularPanel.defaultPanel(IKey.lang(lang).get());
		syncManager.registerSlotGroup(new SlotGroup("charging", 6, 100, true));

		Supplier<EnumFacing> getFacing = () -> getWorld().getBlockState(getPos()).getValue(BlockHorizontal.FACING);
		TRGuis.setupConfigPanel(panel, syncManager, getPos(), ioComponent.getManager(), getFacing,
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
				.child(PowerDisplayWidget.fromEnergyStorage(syncManager, energyComponent.getEnergyStorage())
						.horizontalCenter()
						.top(23));

		panel.child(new ItemSlot().pos(61, 20)
						.slot(new ModularSlot(ioComponent.getInputs(), 0)
								.slotGroup("charging")
								.filter(InventoryUtils.IS_POWER_ITEM)))
				.child(new ItemSlot().pos(61, 40)
						.slot(new ModularSlot(ioComponent.getInputs(), 1)
								.slotGroup("charging")
								.filter(InventoryUtils.IS_POWER_ITEM)))
				.child(new ItemSlot().pos(61, 60)
						.slot(new ModularSlot(ioComponent.getInputs(), 2)
								.slotGroup("charging")
								.filter(InventoryUtils.IS_POWER_ITEM)))
				.child(new ItemSlot().pos(97, 20)
						.slot(new ModularSlot(ioComponent.getInputs(), 3)
								.slotGroup("charging")
								.filter(InventoryUtils.IS_POWER_ITEM)))
				.child(new ItemSlot().pos(97, 40)
						.slot(new ModularSlot(ioComponent.getInputs(), 4)
								.slotGroup("charging")
								.filter(InventoryUtils.IS_POWER_ITEM)))
				.child(new ItemSlot().pos(97, 60)
						.slot(new ModularSlot(ioComponent.getInputs(), 5)
								.slotGroup("charging")
								.filter(InventoryUtils.IS_POWER_ITEM)));
		return panel;
	}
}
