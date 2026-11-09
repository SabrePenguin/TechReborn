package com.sabrepenguin.techreborn.tileentity.multis;

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
import com.sabrepenguin.techreborn.TechReborn;
import com.sabrepenguin.techreborn.capability.stackhandler.SlotAction;
import com.sabrepenguin.techreborn.config.TechRebornConfig;
import com.sabrepenguin.techreborn.gui.PowerDisplayWidget;
import com.sabrepenguin.techreborn.gui.RectangleWidget;
import com.sabrepenguin.techreborn.gui.SlotPosition;
import com.sabrepenguin.techreborn.gui.TRGuis;
import com.sabrepenguin.techreborn.jei.TRRecipePlugin;
import com.sabrepenguin.techreborn.recipe.RegistryHandler;
import com.sabrepenguin.techreborn.util.InventoryUtils;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.util.EnumFacing;

import java.util.function.Supplier;

public class TileEntityDistillationTower extends TileEntityGenericMulti implements IGuiHolder<PosGuiData> {

	public TileEntityDistillationTower() {
		super(2, 4, TechRebornConfig.machineConfig.distillationTower.maxEnergy,
				TechRebornConfig.machineConfig.distillationTower.maxInput, RegistryHandler.instance().getDistillationTowerRegistry(),
				MultiblockStructure.DISTILLATION_TOWER, TechRebornConfig.machineConfig.distillationTower.async);
	}

	@Override
	public ModularPanel buildUI(PosGuiData data, PanelSyncManager syncManager, UISettings settings) {
		String lang = "tile.techreborn.distillation_tower.name";
		ModularPanel panel = ModularPanel.defaultPanel(IKey.lang(lang).get());
		syncManager.syncValue("overlay", multiblockComponent.getValid());
		syncManager.registerSlotGroup(new SlotGroup("inputs", 2, 100, true))
				.registerSlotGroup(new SlotGroup("upgrades", 4, 50, true))
				.registerSlotGroup(new SlotGroup("power", 1, 50, true));

		Supplier<EnumFacing> getFacing = () -> getWorld().getBlockState(getPos()).getValue(BlockHorizontal.FACING);
		TRGuis.setupConfigPanel(panel, syncManager, getPos(), ioComponent.getManager(), getFacing,
				new SlotPosition(SlotAction.INPUT, 40, 30, 0, 0),
				new SlotPosition(SlotAction.INPUT, 40, 50, 0, 1),
				new SlotPosition(SlotAction.OUTPUT, 60, 39, 1, 2),
				new SlotPosition(SlotAction.OUTPUT, 80, 39, 1, 3),
				new SlotPosition(SlotAction.OUTPUT, 100, 39, 1, 4),
				new SlotPosition(SlotAction.OUTPUT, 120, 39, 1, 5),
				new SlotPosition(SlotAction.BIDIRECTIONAL, 7, 59, 2, 6)
		);

		panel.bindPlayerInventory()
				.child(IKey.lang(lang)
						.asWidget()
						.align(Alignment.TopCenter)
						.top(7))
				.child(new ProgressWidget()
						.size(20)
						.pos(63, 32)
						.texture(GuiTextures.PROGRESS_ARROW, 20)
						.value(recipeComponent.getProgress()))
				.child(TRGuis.createJeiButton(TRRecipePlugin.DISTILLATION_TOWER_UID)
						.pos(63, 34).size(20, 15))
				.child(PowerDisplayWidget.fromEnergyStorage(syncManager, energyComponent.getEnergyStorage())
						.pos(9, 6));

		if (ioComponent.getInputs() == null || ioComponent.getOutputs() == null || ioComponent.getBatterySlot() == null) {
			TechReborn.LOGGER.error("A portion of ioComponent was null when it should be impossible to do so.");
			return panel;
		}

		panel.child(new RectangleWidget(139, 139, 139)
				.size(81, 24)
				.pos(85, 29));
		panel.child(new ItemSlot().pos(40, 23)
						.slot(new ModularSlot(ioComponent.getInputs(), 0)
								.slotGroup("inputs")
								.changeListener(this::onInputChange)
								.filter(InventoryUtils.IS_UPGRADE.negate())))
				.child(new ItemSlot().pos(40, 43)
						.slot(new ModularSlot(ioComponent.getInputs(), 1)
								.slotGroup("inputs")
								.changeListener(this::onInputChange)
								.filter(InventoryUtils.IS_UPGRADE.negate())))
				.child(new ItemSlot().pos(88, 32)
						.slot(new ModularSlot(ioComponent.getOutputs(), 0)
								.accessibility(false, true)))
				.child(new ItemSlot().pos(107, 32)
						.slot(new ModularSlot(ioComponent.getOutputs(), 1)
								.accessibility(false, true)))
				.child(new ItemSlot().pos(126, 32)
						.slot(new ModularSlot(ioComponent.getOutputs(), 2)
								.accessibility(false, true)))
				.child(new ItemSlot().pos(145, 32)
						.slot(new ModularSlot(ioComponent.getOutputs(), 3)
								.accessibility(false, true)))
				.child(new ItemSlot().pos(7, 59)
						.slot(new ModularSlot(ioComponent.getBatterySlot(), 0)
								.slotGroup("power")
								.filter(InventoryUtils.IS_POWER_ITEM)));

		TRGuis.createMultiblockCover(panel, data, multiblockComponent::isValid, multiblockComponent.getCurrentStructure());
		panel.child(TRGuis.createUpdateTab(upgradeComponent.getUpgrades(), "upgrades", upgradeComponent::onUpgradeChange).leftRelOffset(1f, 1));
		return panel;
	}
}
