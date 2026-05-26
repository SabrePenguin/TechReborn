package com.sabrepenguin.techreborn.tileentity.processing;

import com.cleanroommc.modularui.api.IGuiHolder;
import com.cleanroommc.modularui.api.drawable.IKey;
import com.cleanroommc.modularui.drawable.GuiTextures;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.utils.Alignment;
import com.cleanroommc.modularui.value.sync.DoubleSyncValue;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.widgets.ProgressWidget;
import com.cleanroommc.modularui.widgets.slot.ItemSlot;
import com.cleanroommc.modularui.widgets.slot.ModularSlot;
import com.cleanroommc.modularui.widgets.slot.SlotGroup;
import com.sabrepenguin.techreborn.capability.stackhandler.SlotAction;
import com.sabrepenguin.techreborn.config.TechRebornConfig;
import com.sabrepenguin.techreborn.gui.*;
import com.sabrepenguin.techreborn.recipe.RegistryHandler;
import com.sabrepenguin.techreborn.util.InventoryUtils;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.util.EnumFacing;

import java.util.function.Supplier;

public class TileEntityRollingMachine extends TileEntityProcessing implements IGuiHolder<PosGuiData> {

	protected boolean locked = false;

	public TileEntityRollingMachine() {
		super(9, 1, TechRebornConfig.machineConfig.rollingMachine.maxEnergy,
				TechRebornConfig.machineConfig.rollingMachine.maxInput, RegistryHandler.instance().getRollingMachineRegistry());
	}

	@Override
	public ModularPanel buildUI(PosGuiData data, PanelSyncManager syncManager, UISettings settings) {
		String lang = "tile.techreborn.rolling_machine.name";
		ModularPanel panel = ModularPanel.defaultPanel(IKey.lang(lang).get());
		syncManager.registerSlotGroup(new SlotGroup("inputs", 9, 100, true))
				.registerSlotGroup(new SlotGroup("upgrades", 4, 50, true))
				.registerSlotGroup(new SlotGroup("power", 1, 50, true));
		Supplier<EnumFacing> getFacing = () -> getWorld().getBlockState(getPos()).getValue(BlockHorizontal.FACING);
		TRGuis.setupConfigPanel(panel, syncManager, getPos(), ioManager, getFacing,
				new SlotPosition(SlotAction.INPUT, 34, 39, 0, 0),
				new SlotPosition(SlotAction.INPUT, 126, 39, 0, 1),
				new SlotPosition(SlotAction.INPUT, 126, 39, 0, 2),
				new SlotPosition(SlotAction.INPUT, 126, 39, 0, 3),
				new SlotPosition(SlotAction.INPUT, 126, 39, 0, 4),
				new SlotPosition(SlotAction.INPUT, 126, 39, 0, 5),
				new SlotPosition(SlotAction.INPUT, 126, 39, 0, 6),
				new SlotPosition(SlotAction.INPUT, 126, 39, 0, 7),
				new SlotPosition(SlotAction.INPUT, 126, 39, 0, 8),
				SlotPosition.BigSlot(SlotAction.OUTPUT, 80, 39, 1, 0),
				new SlotPosition(SlotAction.BIDIRECTIONAL, 7, 59, 2, 0)
		);
		DoubleSyncValue progress = new DoubleSyncValue(() -> (double) processTime / cachedProcessTime,
				value -> processTime = (int) (value * cachedProcessTime));
		panel.bindPlayerInventory()
				.child(IKey.lang(lang)
						.asWidget()
						.align(Alignment.TopCenter)
						.top(7))
				.child(new ProgressWidget()
						.size(20)
						.pos(90, 39)
						.texture(GuiTextures.PROGRESS_ARROW, 20)
						.value(progress))
				.child(TRGuis.createJeiButton(lang)
						.pos(90, 41).size(20, 15))
				.child(PowerDisplayWidget.fromEnergyStorage(syncManager, energyStorage)
						.pos(9, 6));

		panel.child(new ItemSlot().pos(30, 22)
						.slot(new ModularSlot(inputs, 0)
								.slotGroup("inputs").changeListener(this::onInputChange).filter(InventoryUtils.IS_UPGRADE.negate())))
				.child(new ItemSlot().pos(48, 22)
						.slot(new ModularSlot(inputs, 1)
								.slotGroup("inputs").changeListener(this::onInputChange).filter(InventoryUtils.IS_UPGRADE.negate())))
				.child(new ItemSlot().pos(66, 22)
						.slot(new ModularSlot(inputs, 2)
								.slotGroup("inputs").changeListener(this::onInputChange).filter(InventoryUtils.IS_UPGRADE.negate())))
				.child(new ItemSlot().pos(30, 40)
						.slot(new ModularSlot(inputs, 3)
								.slotGroup("inputs").changeListener(this::onInputChange).filter(InventoryUtils.IS_UPGRADE.negate())))
				.child(new ItemSlot().pos(48, 40)
						.slot(new ModularSlot(inputs, 4)
								.slotGroup("inputs").changeListener(this::onInputChange).filter(InventoryUtils.IS_UPGRADE.negate())))
				.child(new ItemSlot().pos(66, 40)
						.slot(new ModularSlot(inputs, 5)
								.slotGroup("inputs").changeListener(this::onInputChange).filter(InventoryUtils.IS_UPGRADE.negate())))
				.child(new ItemSlot().pos(30, 58)
						.slot(new ModularSlot(inputs, 6)
								.slotGroup("inputs").changeListener(this::onInputChange).filter(InventoryUtils.IS_UPGRADE.negate())))
				.child(new ItemSlot().pos(48, 58)
						.slot(new ModularSlot(inputs, 7)
								.slotGroup("inputs").changeListener(this::onInputChange).filter(InventoryUtils.IS_UPGRADE.negate())))
				.child(new ItemSlot().pos(66, 58)
						.slot(new ModularSlot(inputs, 8)
								.slotGroup("inputs").changeListener(this::onInputChange).filter(InventoryUtils.IS_UPGRADE.negate())))
				.child(new LargeItemSlot().pos(120, 39)
						.slot(new ModularSlot(output, 0)
								.accessibility(false, true))
						.size(24))
				.child(new ItemSlot().pos(7, 59)
						.slot(new ModularSlot(battery, 0)
								.slotGroup("power")
								.filter(InventoryUtils.IS_POWER_ITEM)));

		panel.child(TRGuis.createUpdateTab(upgrades, "upgrades", this::onUpgradeChange).leftRelOffset(1f, 1));
		return panel;
	}
}
