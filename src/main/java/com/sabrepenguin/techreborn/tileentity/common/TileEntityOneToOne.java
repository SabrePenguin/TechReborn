package com.sabrepenguin.techreborn.tileentity.common;

import com.cleanroommc.modularui.api.IGuiHolder;
import com.cleanroommc.modularui.api.drawable.IKey;
import com.cleanroommc.modularui.drawable.GuiTextures;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.utils.Alignment;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.widgets.ProgressWidget;
import com.cleanroommc.modularui.widgets.slot.ItemSlot;
import com.cleanroommc.modularui.widgets.slot.ModularSlot;
import com.cleanroommc.modularui.widgets.slot.SlotGroup;
import com.sabrepenguin.techreborn.capability.stackhandler.SlotAction;
import com.sabrepenguin.techreborn.gui.*;
import com.sabrepenguin.techreborn.recipe.registries.ITRRegistry;
import com.sabrepenguin.techreborn.tileentity.processing.TileEntityGeneric;
import com.sabrepenguin.techreborn.util.InventoryUtils;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.util.EnumFacing;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TileEntityOneToOne extends TileEntityGeneric implements IGuiHolder<PosGuiData> {
	private final String lang;
	private final String jeiKey;

	public TileEntityOneToOne(String lang, int feCapacity, int maxInput, ITRRegistry registry, String jeiKey) {
		super(1, 1, feCapacity, maxInput, registry);
		this.lang = lang;
		this.jeiKey = jeiKey;
	}

	// IGuiHolder
	@Override
	@SuppressWarnings("ConstantConditions")
	public ModularPanel buildUI(PosGuiData data, PanelSyncManager syncManager, UISettings settings) {
		ModularPanel panel = ModularPanel.defaultPanel(IKey.lang(lang).get());
		syncManager.registerSlotGroup(new SlotGroup("inputs", 2, 100, true))
				.registerSlotGroup(new SlotGroup("upgrades", 4, 50, true))
				.registerSlotGroup(new SlotGroup("power", 1, 50, true));

		Supplier<EnumFacing> getFacing = () -> getWorld().getBlockState(getPos()).getValue(BlockHorizontal.FACING);
		TRGuis.setupConfigPanel(panel, syncManager, getPos(), ioComponent.getManager(), getFacing,
				new SlotPosition(SlotAction.INPUT, 55, 39, 0, 0),
				SlotPosition.BigSlot(SlotAction.OUTPUT, 101, 39, 1, 0),
				new SlotPosition(SlotAction.BIDIRECTIONAL, 7, 59, 2, 0)
		);

		panel.bindPlayerInventory()
				.child(IKey.lang(lang)
						.asWidget()
						.align(Alignment.TopCenter)
						.top(7))
				.child(new ProgressWidget()
						.size(20)
						.pos(75, 39)
						.texture(GuiTextures.PROGRESS_ARROW, 20)
						.value(recipeComponent.getProgress()))
				.child(TRGuis.createJeiButton(jeiKey)
						.pos(75, 41).size(20, 15))
				.child(PowerDisplayWidget.fromEnergyStorage(syncManager, energyComponent.getEnergyStorage())
						.pos(9, 6));

		panel.child(new ItemSlot().pos(55, 39)
						.slot(new ModularSlot(ioComponent.getInputs(), 0)
								.slotGroup("inputs")
								.changeListener(this::onInputChange)
								.filter(InventoryUtils.IS_UPGRADE.negate())))
				.child(new LargeItemSlot().pos(101, 39)
						.slot(new ModularSlot(ioComponent.getOutputs(), 0)
								.accessibility(false, true))
						.size(24))
				.child(new ItemSlot().pos(7, 59)
						.slot(new ModularSlot(ioComponent.getBatterySlot(), 0)
								.slotGroup("power")
								.filter(InventoryUtils.IS_POWER_ITEM)));

		panel.child(TRGuis.createUpdateTab(upgradeComponent.getUpgrades(), "upgrades", upgradeComponent::onUpgradeChange).leftRelOffset(1f, 1));
		return panel;
	}
}
