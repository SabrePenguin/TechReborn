package com.sabrepenguin.techreborn.tileentity.misc;

import com.cleanroommc.modularui.api.IGuiHolder;
import com.cleanroommc.modularui.api.drawable.IKey;
import com.cleanroommc.modularui.drawable.GuiTextures;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.RichTooltip;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.utils.Alignment;
import com.cleanroommc.modularui.value.sync.DoubleSyncValue;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.widgets.ProgressWidget;
import com.cleanroommc.modularui.widgets.slot.ItemSlot;
import com.cleanroommc.modularui.widgets.slot.ModularSlot;
import com.cleanroommc.modularui.widgets.slot.SlotGroup;
import com.sabrepenguin.techreborn.blocks.machines.BlockHorizontalMachine;
import com.sabrepenguin.techreborn.capability.stackhandler.SlotAction;
import com.sabrepenguin.techreborn.config.TechRebornConfig;
import com.sabrepenguin.techreborn.gui.*;
import com.sabrepenguin.techreborn.recipe.RegistryHandler;
import com.sabrepenguin.techreborn.recipe.registries.MatterFabricatorRegistry;
import com.sabrepenguin.techreborn.tileentity.common.TileEntityIOManager;
import com.sabrepenguin.techreborn.util.InventoryUtils;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TileEntityMatterFabricator extends TileEntityIOManager implements IGuiHolder<PosGuiData> {

	private static final MatterFabricatorRegistry registry = RegistryHandler.instance().getMatterFabricatorRegistry();
	private boolean isActive = false;
	private int processed = 0;

	public TileEntityMatterFabricator() {
		super(6, 5, TechRebornConfig.machineConfig.matterFabricator.maxEnergy, TechRebornConfig.machineConfig.matterFabricator.maxInput, 0);
		energyStorage.setMaxExtract(Integer.MAX_VALUE);
	}
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		processed = compound.getInteger("Processed");
		isActive = compound.getBoolean("IsActive");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("Processed", processed);
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

	private int canProcess() {
		for (int i = 0; i < output.getSlots(); i++) {
			ItemStack out = output.getStackInSlot(i);
			if (out.isEmpty())
				return i;
			if (!out.isItemEqual(registry.getOutput()))
				continue;
			int result = out.getCount() + 1;
			if (result <= output.getSlotLimit(i) && result <= registry.getOutput().getMaxStackSize())
				return i;
		}
		return -1;
	}

	@Override
	public void update() {
		if (world.isRemote)
			return;
		super.update();
		boolean isDirty = false;
		boolean active = false;

		if (processed < TechRebornConfig.machineConfig.matterFabricator.fabricationRate) {
			for (int i = 0; i < inputs.getSlots(); i++) {
				ItemStack fakeItem = inputs.extractItem(i, 1, true);
				if (!fakeItem.isEmpty()) {
					final int amp = registry.getItemValue(fakeItem);
					if (amp > 0) {
						final int feNeed = amp * TechRebornConfig.machineConfig.matterFabricator.energyPerAmp;
						int usedEnergy = energyStorage.internalExtract(feNeed, true);
						if (usedEnergy >= feNeed) {
							inputs.extractItem(i, 1, false);
							processed += amp;
						}
					}
				}
			}
		}
		int slotToOutput = canProcess();
		if (processed >= TechRebornConfig.machineConfig.matterFabricator.fabricationRate && slotToOutput >= 0) {
			processed -= TechRebornConfig.machineConfig.matterFabricator.fabricationRate;
			_internalOutput.insertItem(slotToOutput, registry.getOutput(), false);
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

	@Override
	public ModularPanel buildUI(PosGuiData data, PanelSyncManager syncManager, UISettings settings) {
		String lang = "tile.techreborn.matter_fabricator.name";
		ModularPanel panel = ModularPanel.defaultPanel(IKey.lang(lang).get(), 176, 176);
		syncManager.registerSlotGroup(new SlotGroup("inputs", 3, 100, true))
				.registerSlotGroup(new SlotGroup("upgrades", 4, 50, true))
				.registerSlotGroup(new SlotGroup("power", 1, 50, true));

		Supplier<EnumFacing> getFacing = () -> getWorld().getBlockState(getPos()).getValue(BlockHorizontal.FACING);
		TRGuis.setupConfigPanel(panel, syncManager, getPos(), ioManager, getFacing,
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
		DoubleSyncValue progress = new DoubleSyncValue(() -> (double) processed / TechRebornConfig.machineConfig.matterFabricator.fabricationRate,
				value -> processed = (int) (value * TechRebornConfig.machineConfig.matterFabricator.fabricationRate));

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
						.value(progress))
				.child(PowerDisplayWidget.fromEnergyStorage(syncManager, energyStorage)
						.pos(9, 16))
				.child(new RectangleWidget(129, 129, 129).size(102, 22)
						.pos(38, 64));

		panel.child(new ItemSlot().pos(30, 20).slot(new ModularSlot(inputs, 0)
						.slotGroup("inputs").changeListener(this::onInputChange).filter(InventoryUtils.IS_UPGRADE.negate())))
				.child(new ItemSlot().pos(50, 20).slot(new ModularSlot(inputs, 1)
								.slotGroup("inputs").changeListener(this::onInputChange).filter(InventoryUtils.IS_UPGRADE.negate())))
				.child(new ItemSlot().pos(70, 20).slot(new ModularSlot(inputs, 2)
								.slotGroup("inputs").changeListener(this::onInputChange).filter(InventoryUtils.IS_UPGRADE.negate())))
				.child(new ItemSlot().pos(90, 20).slot(new ModularSlot(inputs, 3)
								.slotGroup("inputs").changeListener(this::onInputChange).filter(InventoryUtils.IS_UPGRADE.negate())))
				.child(new ItemSlot().pos(110, 20).slot(new ModularSlot(inputs, 4)
								.slotGroup("inputs").changeListener(this::onInputChange).filter(InventoryUtils.IS_UPGRADE.negate())))
				.child(new ItemSlot().pos(130, 20).slot(new ModularSlot(inputs, 5)
								.slotGroup("inputs").changeListener(this::onInputChange).filter(InventoryUtils.IS_UPGRADE.negate())))
				.child(new ItemSlot().pos(40, 66).slot(new ModularSlot(output, 0).accessibility(false, true)))
				.child(new ItemSlot().pos(60, 66).slot(new ModularSlot(output, 1).accessibility(false, true)))
				.child(new ItemSlot().pos(80, 66).slot(new ModularSlot(output, 2).accessibility(false, true)))
				.child(new ItemSlot().pos(100, 66).slot(new ModularSlot(output, 3).accessibility(false, true)))
				.child(new ItemSlot().pos(120, 66).slot(new ModularSlot(output, 4).accessibility(false, true)))
				.child(new ItemSlot().pos(7, 69)
						.slot(new ModularSlot(battery, 0)
								.slotGroup("power")
								.filter(InventoryUtils.IS_POWER_ITEM)));
		return panel;
	}
}
