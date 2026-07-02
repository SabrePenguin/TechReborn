package com.sabrepenguin.techreborn.tileentity.misc;

import com.cleanroommc.modularui.api.IGuiHolder;
import com.cleanroommc.modularui.api.drawable.IKey;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.utils.Alignment;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.widgets.ProgressWidget;
import com.cleanroommc.modularui.widgets.slot.ItemSlot;
import com.cleanroommc.modularui.widgets.slot.ModularSlot;
import com.cleanroommc.modularui.widgets.slot.SlotGroup;
import com.sabrepenguin.techreborn.blocks.machines.BlockHorizontalMachine;
import com.sabrepenguin.techreborn.capability.stackhandler.ISideConfigTE;
import com.sabrepenguin.techreborn.capability.stackhandler.SlotAction;
import com.sabrepenguin.techreborn.config.TechRebornConfig;
import com.sabrepenguin.techreborn.gui.*;
import com.sabrepenguin.techreborn.tileentity.MachineIOManager;
import com.sabrepenguin.techreborn.tileentity.components.EnergyComponent;
import com.sabrepenguin.techreborn.tileentity.components.ItemIOComponent;
import com.sabrepenguin.techreborn.tileentity.components.specialized.MatterRecipeComponent;
import com.sabrepenguin.techreborn.util.InventoryUtils;
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
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TileEntityMatterFabricator extends TileEntity implements IGuiHolder<PosGuiData>, ITickable, ISideConfigTE {

	protected final ItemIOComponent ioComponent;
	protected final EnergyComponent energyComponent;
	protected final MatterRecipeComponent recipeComponent;

	public TileEntityMatterFabricator() {
		ioComponent = new ItemIOComponent(this, 6, 5, true, this::refreshSlot);
		energyComponent = new EnergyComponent(TechRebornConfig.machineConfig.matterFabricator.maxEnergy,
				TechRebornConfig.machineConfig.matterFabricator.maxInput, Integer.MAX_VALUE, ioComponent.getBatterySlot())
				.setUnextractable();
		recipeComponent = new MatterRecipeComponent(ioComponent.getInputs(), ioComponent.getInternalOutputs(), energyComponent.getEnergyStorage(),
				this::updateState, TechRebornConfig.machineConfig.matterFabricator.fabricationRate);
	}

	protected void refreshSlot(int slot) {
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
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
				|| capability == CapabilityEnergy.ENERGY
				|| super.hasCapability(capability, facing);
	}

	@Override
	public @Nullable <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			if (facing == null) {
				return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(ioComponent.getInventory());
			}
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(ioComponent.getManager().getSide(facing));
		} else if (capability == CapabilityEnergy.ENERGY) {
			return CapabilityEnergy.ENERGY.cast(energyComponent.getEnergyStorage());
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		ioComponent.readFromNBT(compound);
		energyComponent.readFromNBT(compound);
		recipeComponent.readFromNBT(compound);
		super.readFromNBT(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		ioComponent.writeToNBT(compound);
		energyComponent.writeToNBT(compound);
		recipeComponent.writeToNBT(compound);
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
		recipeComponent.onUpdatePacket(world, pos, pkt.getNbtCompound());
	}

	@Override
	public void update() {
		if (world.isRemote) return;

		boolean updated = ioComponent.update() | energyComponent.update() | recipeComponent.update();

		if (updated) {
			this.markDirty();
		}
	}

	@Override
	public void rotateSlot(int sideIndex, int handlerIndex, int localSlotIndex) {
		if (ioComponent.getManager().getSide(sideIndex).rotateSlot(localSlotIndex)) {
			updateTE();
		}
	}

	@Override
	public void swapSlot(int index, boolean input) {
		ioComponent.getManager().swapIndex(index, input);
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
		return null; //TODO
	}

	@Override
	public ModularPanel buildUI(PosGuiData data, PanelSyncManager syncManager, UISettings settings) {
		String lang = "tile.techreborn.matter_fabricator.name";
		ModularPanel panel = ModularPanel.defaultPanel(IKey.lang(lang).get(), 176, 176);
		syncManager.registerSlotGroup(new SlotGroup("inputs", 3, 100, true))
				.registerSlotGroup(new SlotGroup("upgrades", 4, 50, true))
				.registerSlotGroup(new SlotGroup("power", 1, 50, true));

		Supplier<EnumFacing> getFacing = () -> getWorld().getBlockState(getPos()).getValue(BlockHorizontal.FACING);
		TRGuis.setupConfigPanel(panel, syncManager, getPos(), ioComponent.getManager(), getFacing,
				new SlotPosition(SlotAction.INPUT, 30, 20, 0, 0),
				new SlotPosition(SlotAction.INPUT, 50, 20, 0, 1),
				new SlotPosition(SlotAction.INPUT, 70, 20, 0, 2),
				new SlotPosition(SlotAction.INPUT, 90, 20, 0, 3),
				new SlotPosition(SlotAction.INPUT, 110, 20, 0, 4),
				new SlotPosition(SlotAction.INPUT, 130, 20, 0, 5),
				new SlotPosition(SlotAction.OUTPUT, 40, 66, 1, 0),
				new SlotPosition(SlotAction.OUTPUT, 60, 66, 1, 1),
				new SlotPosition(SlotAction.OUTPUT, 80, 66, 1, 2),
				new SlotPosition(SlotAction.OUTPUT, 100, 66, 1, 3),
				new SlotPosition(SlotAction.OUTPUT, 120, 66, 1, 4),
				new SlotPosition(SlotAction.BIDIRECTIONAL, 7, 69, 2, 0)
		);

		panel.bindPlayerInventory()
				.child(IKey.lang(lang)
						.asWidget()
						.align(Alignment.TopCenter)
						.top(7))
				.child(new ProgressWidget()
						.size(20)
						.pos(79, 41)
						.texture(TRGuiTextures.DOWN_PROGRESS_ARROW, 20)
						.direction(ProgressWidget.Direction.DOWN)
						.value(recipeComponent.getProgress()))
				.child(PowerDisplayWidget.fromEnergyStorage(syncManager, energyComponent.getEnergyStorage())
						.pos(9, 16))
				.child(new RectangleWidget(129, 129, 129).size(102, 22)
						.pos(38, 64));

		panel.child(new ItemSlot().pos(30, 20).slot(new ModularSlot(ioComponent.getInputs(), 0)
						.slotGroup("inputs").changeListener(this::onInputChange).filter(InventoryUtils.IS_UPGRADE.negate())))
				.child(new ItemSlot().pos(50, 20).slot(new ModularSlot(ioComponent.getInputs(), 1)
								.slotGroup("inputs").changeListener(this::onInputChange).filter(InventoryUtils.IS_UPGRADE.negate())))
				.child(new ItemSlot().pos(70, 20).slot(new ModularSlot(ioComponent.getInputs(), 2)
								.slotGroup("inputs").changeListener(this::onInputChange).filter(InventoryUtils.IS_UPGRADE.negate())))
				.child(new ItemSlot().pos(90, 20).slot(new ModularSlot(ioComponent.getInputs(), 3)
								.slotGroup("inputs").changeListener(this::onInputChange).filter(InventoryUtils.IS_UPGRADE.negate())))
				.child(new ItemSlot().pos(110, 20).slot(new ModularSlot(ioComponent.getInputs(), 4)
								.slotGroup("inputs").changeListener(this::onInputChange).filter(InventoryUtils.IS_UPGRADE.negate())))
				.child(new ItemSlot().pos(130, 20).slot(new ModularSlot(ioComponent.getInputs(), 5)
								.slotGroup("inputs").changeListener(this::onInputChange).filter(InventoryUtils.IS_UPGRADE.negate())))
				.child(new ItemSlot().pos(40, 66).slot(new ModularSlot(ioComponent.getOutputs(), 0).accessibility(false, true)))
				.child(new ItemSlot().pos(60, 66).slot(new ModularSlot(ioComponent.getOutputs(), 1).accessibility(false, true)))
				.child(new ItemSlot().pos(80, 66).slot(new ModularSlot(ioComponent.getOutputs(), 2).accessibility(false, true)))
				.child(new ItemSlot().pos(100, 66).slot(new ModularSlot(ioComponent.getOutputs(), 3).accessibility(false, true)))
				.child(new ItemSlot().pos(120, 66).slot(new ModularSlot(ioComponent.getOutputs(), 4).accessibility(false, true)))
				.child(new ItemSlot().pos(7, 69)
						.slot(new ModularSlot(ioComponent.getBatterySlot(), 0)
								.slotGroup("power")
								.filter(InventoryUtils.IS_POWER_ITEM)));
		return panel;
	}
	public void onInputChange(ItemStack newItem, boolean onlyAmountChanged, boolean client, boolean init) {
		if (client || init) return;
		if (onlyAmountChanged) {
			refreshSlot(0);
			return;
		}
		refreshSlot(0);
		markDirty();
	}
}
