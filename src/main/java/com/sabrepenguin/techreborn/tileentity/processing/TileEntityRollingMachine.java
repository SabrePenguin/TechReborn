package com.sabrepenguin.techreborn.tileentity.processing;

import com.cleanroommc.modularui.api.IGuiHolder;
import com.cleanroommc.modularui.api.drawable.IKey;
import com.cleanroommc.modularui.drawable.GuiTextures;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.utils.Alignment;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.widgets.ProgressWidget;
import com.cleanroommc.modularui.widgets.ToggleButton;
import com.cleanroommc.modularui.widgets.slot.ItemSlot;
import com.cleanroommc.modularui.widgets.slot.ModularSlot;
import com.cleanroommc.modularui.widgets.slot.SlotGroup;
import com.sabrepenguin.techreborn.capability.stackhandler.SlotAction;
import com.sabrepenguin.techreborn.config.TechRebornConfig;
import com.sabrepenguin.techreborn.gui.*;
import com.sabrepenguin.techreborn.recipe.RegistryHandler;
import com.sabrepenguin.techreborn.util.InventoryUtils;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.util.EnumFacing;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TileEntityRollingMachine extends TileEntityAutoBalance implements IGuiHolder<PosGuiData> {

	public TileEntityRollingMachine() {
		super(9, 1, TechRebornConfig.machineConfig.rollingMachine.maxEnergy,
				TechRebornConfig.machineConfig.rollingMachine.maxInput, RegistryHandler.instance().getRollingMachineRegistry());
	}

	@Override
	@SuppressWarnings("ConstantConditions")
	public ModularPanel buildUI(PosGuiData data, PanelSyncManager syncManager, UISettings settings) {
		String lang = "tile.techreborn.rolling_machine.name";
		ModularPanel panel = ModularPanel.defaultPanel(IKey.lang(lang).get());
		syncManager.registerSlotGroup(new SlotGroup("inputs", 9, 100, true))
				.registerSlotGroup(new SlotGroup("upgrades", 4, 50, true))
				.registerSlotGroup(new SlotGroup("power", 1, 50, true));
		Supplier<EnumFacing> getFacing = () -> getWorld().getBlockState(getPos()).getValue(BlockHorizontal.FACING);
		TRGuis.setupConfigPanel(panel, syncManager, getPos(), ioComponent.getManager(), getFacing,
				new SlotPosition(SlotAction.INPUT, 30, 22, 0, 0),
				new SlotPosition(SlotAction.INPUT, 48, 22, 0, 1),
				new SlotPosition(SlotAction.INPUT, 66, 22, 0, 2),
				new SlotPosition(SlotAction.INPUT, 30, 40, 0, 3),
				new SlotPosition(SlotAction.INPUT, 48, 40, 0, 4),
				new SlotPosition(SlotAction.INPUT, 66, 40, 0, 5),
				new SlotPosition(SlotAction.INPUT, 30, 58, 0, 6),
				new SlotPosition(SlotAction.INPUT, 48, 58, 0, 7),
				new SlotPosition(SlotAction.INPUT, 66, 58, 0, 8),
				SlotPosition.BigSlot(SlotAction.OUTPUT, 120, 39, 1, 0),
				new SlotPosition(SlotAction.BIDIRECTIONAL, 7, 59, 2, 0)
		);
		panel.bindPlayerInventory()
				.child(IKey.lang(lang)
						.asWidget()
						.align(Alignment.TopCenter)
						.top(7))
				.child(new ProgressWidget()
						.size(20)
						.pos(90, 39)
						.texture(GuiTextures.PROGRESS_ARROW, 20)
						.value(recipeComponent.getProgress()))
				.child(TRGuis.createJeiButton(lang)
						.pos(90, 41).size(20, 15))
				.child(PowerDisplayWidget.fromEnergyStorage(syncManager, energyComponent.getEnergyStorage())
						.pos(9, 6));

		panel.child(new ItemSlot().pos(30, 22)
						.slot(new ModularSlot(ioComponent.getInputs(), 0)
								.slotGroup("inputs").changeListener(this::onInputChange).filter(InventoryUtils.IS_UPGRADE.negate())))
				.child(new ItemSlot().pos(48, 22)
						.slot(new ModularSlot(ioComponent.getInputs(), 1)
								.slotGroup("inputs").changeListener(this::onInputChange).filter(InventoryUtils.IS_UPGRADE.negate())))
				.child(new ItemSlot().pos(66, 22)
						.slot(new ModularSlot(ioComponent.getInputs(), 2)
								.slotGroup("inputs").changeListener(this::onInputChange).filter(InventoryUtils.IS_UPGRADE.negate())))
				.child(new ItemSlot().pos(30, 40)
						.slot(new ModularSlot(ioComponent.getInputs(), 3)
								.slotGroup("inputs").changeListener(this::onInputChange).filter(InventoryUtils.IS_UPGRADE.negate())))
				.child(new ItemSlot().pos(48, 40)
						.slot(new ModularSlot(ioComponent.getInputs(), 4)
								.slotGroup("inputs").changeListener(this::onInputChange).filter(InventoryUtils.IS_UPGRADE.negate())))
				.child(new ItemSlot().pos(66, 40)
						.slot(new ModularSlot(ioComponent.getInputs(), 5)
								.slotGroup("inputs").changeListener(this::onInputChange).filter(InventoryUtils.IS_UPGRADE.negate())))
				.child(new ItemSlot().pos(30, 58)
						.slot(new ModularSlot(ioComponent.getInputs(), 6)
								.slotGroup("inputs").changeListener(this::onInputChange).filter(InventoryUtils.IS_UPGRADE.negate())))
				.child(new ItemSlot().pos(48, 58)
						.slot(new ModularSlot(ioComponent.getInputs(), 7)
								.slotGroup("inputs").changeListener(this::onInputChange).filter(InventoryUtils.IS_UPGRADE.negate())))
				.child(new ItemSlot().pos(66, 58)
						.slot(new ModularSlot(ioComponent.getInputs(), 8)
								.slotGroup("inputs").changeListener(this::onInputChange).filter(InventoryUtils.IS_UPGRADE.negate())))
				.child(new LargeItemSlot().pos(120, 39)
						.slot(new ModularSlot(ioComponent.getOutputs(), 0)
								.accessibility(false, true))
						.size(24))
				.child(new ItemSlot().pos(7, 59)
						.slot(new ModularSlot(ioComponent.getBatterySlot(), 0)
								.slotGroup("power")
								.filter(InventoryUtils.IS_POWER_ITEM)));

		panel.child(new ToggleButton()
				.right(5)
				.top(5)
				.size(16, 12)
				.stateOverlay(TRGuiTextures.LOCK)
				.value(recipeComponent.getLocked()));

		panel.child(TRGuis.createUpdateTab(upgradeComponent.getUpgrades(), "upgrades", upgradeComponent::onUpgradeChange).leftRelOffset(1f, 1));
		return panel;
	}
}
