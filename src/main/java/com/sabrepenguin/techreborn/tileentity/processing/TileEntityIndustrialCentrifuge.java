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
import com.sabrepenguin.techreborn.gui.PowerDisplayWidget;
import com.sabrepenguin.techreborn.gui.SlotPosition;
import com.sabrepenguin.techreborn.gui.TRGuis;
import com.sabrepenguin.techreborn.recipe.RegistryHandler;
import com.sabrepenguin.techreborn.util.InventoryUtils;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.util.EnumFacing;

import java.util.function.Supplier;

public class TileEntityIndustrialCentrifuge extends TileEntityProcessing implements IGuiHolder<PosGuiData> {
	public TileEntityIndustrialCentrifuge() {
		super(2, 4, TechRebornConfig.machineConfig.industrialCentrifuge.maxEnergy,
				TechRebornConfig.machineConfig.industrialCentrifuge.maxInput, RegistryHandler.instance().getIndustrialCentrifugeRegistry());
	}

	@Override
	public ModularPanel buildUI(PosGuiData data, PanelSyncManager syncManager, UISettings settings) {
		String lang = "tile.techreborn.industrial_centrifuge.name";
		ModularPanel panel = ModularPanel.defaultPanel(IKey.lang(lang).get());
		syncManager.registerSlotGroup(new SlotGroup("inputs", 2, 100, true))
				.registerSlotGroup(new SlotGroup("upgrades", 4, 50, true))
				.registerSlotGroup(new SlotGroup("power", 1, 50, true));

		Supplier<EnumFacing> getFacing = () -> getWorld().getBlockState(getPos()).getValue(BlockHorizontal.FACING);
		TRGuis.setupConfigPanel(panel, syncManager, getPos(), ioManager, getFacing,
				new SlotPosition(SlotAction.INPUT, 55, 39, 0, 0),
				SlotPosition.BigSlot(SlotAction.OUTPUT, 101, 39, 1, 0),
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
						.pos(61, 39)
						.texture(GuiTextures.PROGRESS_ARROW, 20)
						.value(progress))
				.child(TRGuis.createJeiButton(lang)
						.pos(61, 41).size(20, 15))
				.child(PowerDisplayWidget.fromEnergyStorage(syncManager, energyStorage)
						.pos(9, 6));

		panel.child(new ItemSlot().pos(40, 30)
						.slot(new ModularSlot(inputs, 0)
								.slotGroup("inputs")
								.changeListener(this::onInputChange)
								.filter(InventoryUtils.IS_UPGRADE.negate())))
				.child(new ItemSlot().pos(40, 50)
						.slot(new ModularSlot(inputs, 1)
								.slotGroup("inputs")
								.changeListener(this::onInputChange)
								.filter(InventoryUtils.IS_UPGRADE.negate())))
				.child(new ItemSlot().pos(84, 40)
						.slot(new ModularSlot(output, 0)
								.accessibility(false, true)))
				.child(new ItemSlot().pos(103, 21)
						.slot(new ModularSlot(output, 1)
								.accessibility(false, true)))
				.child(new ItemSlot().pos(122, 40)
						.slot(new ModularSlot(output, 2)
								.accessibility(false, true)))
				.child(new ItemSlot().pos(103, 59)
						.slot(new ModularSlot(output, 3)
								.accessibility(false, true)))
				.child(new ItemSlot().pos(7, 59)
						.slot(new ModularSlot(battery, 0)
								.slotGroup("power")
								.filter(InventoryUtils.IS_POWER_ITEM)));

		panel.child(TRGuis.createUpdateTab(upgrades, "upgrades", this::onUpgradeChange).leftRelOffset(1f, 1));
		return panel;
	}
}
